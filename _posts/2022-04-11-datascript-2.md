---
layout: post
title: "Ideas for DataScript 2"
category: blog
summary: "Things that came to mind while working on DataScript 1"
draft: true
---

No, I am not working on second version. This is just a list of ideas based on 8 years developing DataScript and 3 years building web-applications with it full-time.

Maybe you’ll find inspiration in these? Feel free to borrow anything you like!

## UUIDs for entity IDs

Makes it easier to generate new IDs in distributed environment without consulting central authority.

## Attribute IDs

Right now attributes are stored as keywords in DataScript. This is inefficient both for storage (CLJS creates different keywords each time, CLJ interns them) and for comparison.

Integer IDs are fast to compare and compact to store. Keyword ↔︎ id translation could be done transparently to user (Datomic fails to do that in some cases, need to check if could be avoided).

## Optimized B-Trees

Right now B-Trees store datoms (JS objects/POJOs), which leads to crazy amount of pointer chasing during binary searches.

E.g. to access attribute, you first go to datom, then to `a` field, which points to keyword. In CLJS, keyword is an object which points to two strings: namespace and name. Each string itself is probably a pointer. You get the idea.

It will probably be much more efficient if entity ID and attribute ID would be stored directly in B-Tree array, without any need to go anywhere. This will make EAVT and AEVT indexes way faster, and those are responsible for majority of use-cases.

## Transaction IDs

In Datomic, each datom is branded with transaction ID when it was created. They are used in full history DB view to filter out past from the future.

DataScript doesn’t have that, so TX serves no real purpose. Same for `added` flag: it’s barely needed.

## Entities without cache

Entities (object-like view into database) are caching attributes right now, copied from Datomic.

It feels like it’s unnecessary: you can just store a pointer into EAVT index and scan/binary search every time somebody asks for attribute. EAVT is a cache in some sense.

## No queries

I know this is controvertial. Query engine amounts for a lot of complexity in DataScript, and it’s very convenient to use. I have a feeling a lot of people are attracted to DataScript because they want to use queries.

Well, it surprised me as well that in 3 years of full-time web app development with DataScript I haven’t used a single query.

I guess if you build UI with a database, you don’t need queries that much. What you need is a graph database: get this object and follow this relation to another object/collection.

Another reason why I don’t like queries is performance. Index scans are the same, but converting form raw datoms into relations takes time, building set (removing duplicates) takes time, etc. I find it faster to use direct index access for simple stuff.

Performance for simple queries could be solved, I think. But the general viability of queries (despite their attractivenes) is under question.

## Recursive walking

What saddens me the most in DataScript/Datomic is that rules are often used to do simple recursive walking. Like, go all the way up or all the way down the tree through these relations until a condition is met. Some examples:

- Go all the way up through `:entity/parent` until `(= (:entity/type %) :document)`
- Do breadth-first search recusrive through `:entity/children` and collect `:entity/text`

You don’t really need a Datalog semantic there: building sets, joining and all that. But people still use it because it’s the only thing they got.

So maybe a collection of convenient recursive walk functions could do instead?

## Ordering

We need a good built-in way to order stuff. Not sure how API would look like, but we need that. Order in UI is very important.

## Reactive updates

Re-rendering your whole application is great (and it worked very well for us). But I always want to be more efficient. Since we won’t have queries, how about subscribing to individual entity updates?

I have a feeling this could be done even today, without modifying DataScript even, but designing it from the beginning might work out even better.

The simplest API I’m thinking is like

```
(d/subscribe conn e a v callback)
```

where any of `e`, `a`, `v` could be `nil`. E.g.

```
(d/subscribe conn 100 nil nil callback)
```

means you want any changes in any attributes for entity 100.

This API is simple, could be implemented efficiently, could get you a long way (hopefully).

## Persistence

As experience shows, even in browser DataScript databases could grow quite large. Which means it’ll be great to have:

- Lazy loading segments from a persistent storage/network
- Append to transaction log
- Rebuild indexes once in a while

Would be cool to have file system / SQLite / IndexedDB storages for starters.

## Async API

The reason DataScript doesn’t have persistence yet is because all DataScript APIs are synchronous, and IndexedDB APIs are asynchronous.

For persistence to work, you need to make all APIs to be async, too (not on a server, of course, but in JS, which makes me sad).

## Replication

Client-to-server, server-to-server, client-to-firebase, client-to-Datomic, client-to-client.

The idea is simple: if you run DataScript on both client and server, it would be cool if they could talk to each other directly and you don’t have to implement sync layer yourself.

You don’t want to do it yourself: there’s a lot of edge cases and failure modes that are tricky to get right.

The main obstacle here is API: how would subscriptions look like? How to start/cancel them? How to track which datom came from which subscription? How to clean up unneded data?

## Conclusion

This is a dump of raw ideas I had collected over the years in case I ever decide to start DataScript over.

If you have more—don’t hesitate to reach out! I’d be glad to hear them out.