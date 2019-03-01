---
layout: post
title: Chatting cats use DataScript for fun
category: blog
summary: "How to write chat application with ClojureScript, core.async, React and DataScript"
img_rel: true
---

What DataScript-driven application looks like? Does DataScript really makes the difference? I tried to answer both questions by writing small single-page application. Not to bore you (and myself; mostly myself) with another TodoMVC show-off, I made a simple chat. Meet CatChat:

<figure><a style="border: none; " href="http://tonsky.me/datascript-chat/"><img src="./datascript_chat.png" style="width: 620px; height: 420px; margin: 20px 0;" /></a></figure>

Check out [source code](https://github.com/tonsky/datascript-chat) and [live version](http://tonsky.me/datascript-chat/).

CatChat is organized by principles of Flux architecture. DataScript is a storage, clojure/core.async is an event bus, DOM is rendered by React. Some [GSS](http://gridstylesheets.org) used out of curiosity. Not to complicate things, server-side calls are emulated.

<figure><img src="./overview.png" /></figure>

## Starting

At the beginning we just create a DB:

    (def conn (d/create-conn {}))

Initial data is loaded and pushed directly to DB in `onReady` callbacks. We assume server sends data in a format that matches client-side DB:

    (server/call server/get-rooms []
      (fn [rooms]
        (d/transact! conn rooms)))

    (server/call server/whoami []
      (fn [user]
        (d/transact! conn [(assoc user
                             :user/me    true
                             :user/state :loaded)])))

Notice how user object gets _augmented_. Persistent attributes like `:user/name` or `:room/title` come directly from server DB. But some stuff only makes sense on a client: who current user is, which room is selected — session-dependent stuff. We store these transient attributes in the same DataScript database, exactly at the same entities. They will come in handy when queries kick in.

Good thing about code above is that it doesn’t know or care about any other part of the system: how data is rendered, who else listen or cares about data — it doesn’t matter at this point. Initial database population is a small, self-contained piece of logic. Our app will be built from pieces like that: independent, focused and composable. They do not communicate with each other, only thing they share is the database.

## Dispatching

Data flow incorporates event bus to make events observable and allow reactions to be added. Event bus opens a lot of possibilities, including events mocking, simulation, logging. Having simple way to get insight into causality graph is _crucial_ for effective understanding of complex systems. Debug, refactoring and optimization benefits follow.

Most importantly, event bus helps with decoupling: nobody knows what parties are interested in particular message; different pieces of functionality can be developed, tested and run independently from each other.

We use core.async pub/sub channel as event bus. Incoming chat messages are delivered via server push and then `put!` to the channel:

    (def event-bus (async/chan))
    (def event-bus-pub (async/pub event-bus first))

    (server/subscribe
      (fn [message]
        (async/put! event-bus [:recv-msg message])))

Our first consumer just saves messages to the database:

    (let [ch (async/chan)]
      (async/sub event-bus-pub :recv-msg ch)
      (go-loop []
        (let [[_ msg] (async/<! ch)]
          (d/transact! conn [msg])))
        (recur))

I put a little twist to data model here. With each message, server sends user id, but whole user entity (name, avatar) is needed for rendering. Thus, we must issue another async request to fetch user data. It’s done in another listener to event stream:

    (let [ch (async/chan)]
      (async/sub event-bus-pub :recv-msg ch)
      (go-loop [seen-uids #{}]
        (let [[_ msg] (<! ch)
              uid     (:message/author msg)]
          (if (contains? seen-uids uid)
            (recur seen-uids)
            (do
              (d/transact! conn [(user-stub uid)])
              (load-user uid)
              (recur (conj seen-uids uid)))))))

    (defn user-stub [uid]
      { :db/id       uid
        :user/name   "Loading..."
        :user/avatar "avatars/loading.jpg"
        :user/state  :loading })

    (defn load-user [uid]
      (server/call server/get-user [uid]
        (fn [user]
          (d/transact! conn [(assoc user
                               :user/state :loaded)]))))

For every incoming message we check if we’ve seen author id already, and if not, then we send request to the server, put temporary user stub to the database (to display placeholders instead of avatar and name), and recur. Once server responds, callback gets called and we store actual user data with the same entity id, overwriting stub attributes.

Note that code above contains an infinite loop that tracks state (seen user ids) naturally — a thing you can’t afford with callbacks. Go blocks are sequential co-programs which give an illusion of parallel execution a-la green threads. Their parking and resuming happens at point where data arrives to or leaves channels. Core.async can do much more beyond simple pub-sub, (think complex topologies of channels, modifiable at runtime), but I couldn’t find a good occasion for that in CatChat.

## Rendering

`Render` is literally a function of two arguments: DB value for building DOM and event bus for communicating events back. Root React component receives a value of a DB as one and only source of data. Having all state as a single, immutable value brings many benefits:

- Rendering is always consistent. No matter how state mutation and rendering loops work, you take immutable DB snapshot once and render everything from it. User never sees a screen in transient state.
- Previous states can be stored and reverted to. This makes undo/redo, replays and time traveling trivial.
- Rendering code does not care how data gets there. You can easily render mock states and do what-if speculations without touching rendering at all.
- Application state can be remembered and restored trivially (e.g. from `localStorage` between page reloads).

It’s trivial to know when re-rendering is needed. We just establish a DB listener and trigger re-rendering after each transaction:

    (d/listen! conn
      (fn [tx-report]
        (ui/request-rerender (:db-after tx-report) event-bus)))


Independent widget development is also a breeze. All widgets are derived from the same database, but other than that, they do not communicate neither depend on each other. It removes large piece of logic responsible for two-way data flow between UI components: user clicked here, let’s tell everyone what and how they should update. We all love shortcuts, but even in small applications this approach is not sustainable. What UI needs to communicate back to DB goes through the same event bus everybody else in a system uses. After all, rendering is not _that_ special. 

## Queries

Let’s now dive into the deeps of DataScript usage. Rendering is the main reader of a database, utilizing all sorts of queries.

Simplest possible query selects for each room id its title:

    (d/q '[:find ?r ?t
           :where [?r :room/title ?t]]
      db)

Results are always a set of tuples, each tuple consisting of values in `:find` clause order. In our case it’ll look like this:

    #{[12 "Room1"]
      [42 "Room2"]
      ...}

Here we select all unread messages in a specific room:

    (d/q '[:find ?m
           :in $ ?r
           :where [?m :message/unread true]
                  [?m :message/room ?r]]
      db room-id)

That query does implicit join (all unread messages are inner joined with all messages of a specific room) and has a query parameter (`room-id`).

Notice that `db` is also just a parameter for a query. DataScript allows for several databases in a single query (and/or collections, they work the same) and can do effective cross-db joins between them.

This function uses previous query to construct a list of datoms for a transaction that will mark all messages in a room as read:

    (defn mark-read [db room-id]
      (let [unread (d/q '[:find ?m
                          :in $ ?r
                          :where [?m :message/unread true]
                                 [?m :message/room ?r]]
                        db room-id)]
        (map (fn [[mid]]
               [:db/retract mid :message/unread true])
             unread)))

Aggregates are another handy feature. This query takes, for each room, all messages that satisfy `:where` clause and _then_ applies `count` on them, grouping by room: 

    (d/q '[:find ?r (count ?m)
           :where [?m :message/unread]
                  [?m :message/room ?r]]
      db)

Result will look like this:

    #{[1 18]
      [2  0]
      [3  2]}

## Entities

Take a look at how messages are retrieved by room id:

    (let [msgs (->> (d/q '[:find ?m
                           :in $ ?r
                           :where [?m :message/room ?r]]
                   db room-id)
                 (map first)
                 (map #(d/entity db %))
                 (sort-by :message/timestamp))])

Query first selects messages for specific room, then results are unpacked (so we have `(1 2 3)` instead of `#{[1] [2] [3]}`), then every id gets converted to an entity, and finally all entities are sorted by `:message/timestamp` attribute.

Sometimes entities are very handy, and you’ll probably use them a lot. Entities are map-like interface to accessing DB: given entity id and DB value, all attributes of that entity id will be in a map (well, <nobr>sort of —</nobr> you cannot `assoc` them, and `get` is lazy). For example, you have room with id 17:

    (def room (d/entity db 17))

Use it to get attribute values as if it was a regular map:

    (:room/title room)    => "Room 17"
    (:room/selected room) => true
    (:db/id room)         => 17

As you access attributes, they get lazily retrieved and cached:

    room => { :db/id 17,
              :room/title "Room 17",
              :room/selected true }

Entities are intentionally dumb and simple. They’re just a view at a specific part of specific database version. They do not auto-update when database is changed. They cannot communicate changes back to the database. Entities are not ORM. In essence, entities are just handy way to write `[:find ?v :in $ ?e ?a :where [?e ?a ?v]]` queries.

Entities also make it easy to walk references. If a value of an attribute is a reference to another entity, it’ll be represented as entity object itself:

    (d/entity db msg-id)
    =>  {:db/id 10001
         :message/text   "..."
         :message/room   { :db/id 2
                           :room/title "Room2" }
         :message/author { :db/id 17
                           :user/name "Ilya"   }}

For this to work, specify attribute type in schema during initial database creation:

    (def conn (d/create-conn {
      :message/room   {:db/valueType :db.type/ref}
      :message/author {:db/valueType :db.type/ref}
    }))

## Multi-valued relations

DataScript is especially good at multi-valued relations. One-to-many and many-to-many relations are first class. If a group has a list of students, DataScript can support that. If an actor plays in a movie, and movie has a list of an actors, you can model that without intermediate table nonsense.

Relations are two-way. It doesn’t really matter if room contains list of messages or message has a reference to a room. You can query it both ways:

    (def conn (d/create-conn {
      :message/room {:db/valueType :db.type/ref}
    }))

    (d/q '[:find ?m
           :in $ ?r
           :where [?m :message/room ?r]
      db room-id)

    (d/q '[:find ?r
           :in $ ?m
           :where [?m :message/room ?r]
      db message-id)

Even if we reverse relation in schema, it wouldn’t really matter:

    (def conn (d/create-conn {
      :room/messages {:db/valueType   :db.type/ref
                      :db/cardinality :db.cardinality/many }
    }))

    (d/q '[:find ?m
           :in $ ?r
           :where [?r :room/messages ?m]
      db room-id)

    (d/q '[:find ?r
           :in $ ?m
           :where [?r :room/messages ?m]
      db message-id)

Entities have a nice way to handle references in both directions. In CatChat we use `:message/room` relation. To access it in forward direction (from message to room):

    (get message-entity :message/room) => <room-ent>

_Exactly the same_ relation can be accessed backwards (from room to messages):

    (get room-entity :message/_room)   => #{<message-ent>, ...}

Backward-accessed relations always return sets of entities. Forward access returns single entity or a set depending on relation’s arity. All this makes DataScript natural to express and easy to navigate entities graphs.

## Resume

Let’s recap:

1. Event bus is implemented as core.async channel with listeners implemented as independent `go` loops.
2. Listeners issue DB transactions to “alter” DB value.
3. React render is a function of immutable DB value and is triggered after each transaction. Current value of DB is passed as the only property.
4. Any action in UI, if it want to change something, sends an event to event bus. Loop closes.

For me, that was the best way to write UI application I ever experienced.

Turned out adopting a database is a really good idea for client-side app. Programming languages make it easy to model state as nested dictionaries and arrays, but most data access patterns are more complicated. “I know, I’ll put messages inside rooms! Oh, now I need to count unread messages across all rooms... Oh, now I need to group messages by user id. Ok, I’m screwed”. This is where DataScript shines: you store datom once and look at it from different angles: messages by room, room by message, messages by user, user by having unread messages, messages by unread status, and so on. One-to-many collections, many-to-many relations, reference graphs — it all fits naturally to DataScript. It frees a lot of cognitive resources: you don’t have to invent optimal storage strategy for every next property, messing with all these nested hash map structures, clever rolling caches, consistency issues. In DataScript all data is in one place, it’s normalized, it’s handled uniformly, it’s already optimized — much better than you usually do by hand. And you can query it any way you need.

Project trackers, email clients, calendars, online banks, professional to-do lists are all kinds of client-side apps that are highly structured and can benefit from adopting DataScript. Think Trello or GMail: in any sufficiently complex client-side app there’s a lot of structured data to take care of. I personally sometimes fantasize about rewriting GitHub issues page:

<figure><img src="./github_issues.png"></figure>

Just imagine how we can store all these tiny issues and all their little properties in DataScript, and then implement all these tabs, buttons and filters _on a client_, without even touching a server.

This should bring sanity to web app development. Finally, server API is dumb and inflexible, returning, within a single call, all essential data in a bulk dump format. Server is freed from any presentation-level hacks like `?sort_by=name`, `?unread=unread` or <nobr><code>?flash_message=Saved</code></nobr>. That’s all part of presentation logic and must reside on a client.

## Where to get more info?

1. [Flux architecture overview](http://facebook.github.io/react/docs/flux-overview.html)

2. Rich Hickey speaks core.async and why channels are fundamentally better than callbacks: [infoq.com/presentations/clojure-core-async](http://www.infoq.com/presentations/clojure-core-async) (esp. from 32:00)

3. Stuart Halloway [introduces Datalog for Datomic](http://www.youtube.com/watch?v=bAilFQdaiHk) (DataScript syntax is heavily based on Datomic’s one)

4. While DataScript doesn’t have its own documentation, take a look at Datomic’s docs on [queries](http://docs.datomic.com/query.html),  [transactions](http://docs.datomic.com/transactions.html), [entities](http://docs.datomic.com/entities.html) and [indexes](http://docs.datomic.com/indexes.html). They are pretty close, with some minor differences

5. [DataScript tests suite](https://github.com/tonsky/datascript/tree/master/test/datascript/test) can give you a good overview of what’s possible with DataScript

6. And, of course, don’t forget [CatChat codebase](https://github.com/tonsky/datascript-chat)