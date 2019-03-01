---
layout: post
title: Datomic as a Protocol
category: blog
summary: "Datomic introduced a data structure model: entity-attribute-value store, transaction format, Datalog query language dialect. As you get familiar with it, you notice that this model has a lot of nice properties and is, in fact, more thought-out than it may appear at the first glance."
img_rel: true
---

Datomic introduced a data structure model\: entity-attribute-value store, transaction format, Datalog query language dialect. As you get familiar with it, you notice that this model has a lot of nice properties and is, in fact, more thought-out than it may appear at the first glance.

Data format is agile and multi-purpose. It works perfectly well with non-regular data, wide columns, a lot of rows, sparse data, graph data. It all can be efficiently stored and accessed.

Since it’s a commercial database, it covers all sorts of use-cases real people face in real world while working with data. You can actually see how the Datomic team started small and added features to the data model as they felt need for them. In other words, the Datomic model is feature-full.

Transactions are just data. In Clojure we’re [used to data-oriented approach](http://www.infoq.com/presentations/Thinking-in-Data) and are well aware of its benefits. But there we have data representation not only for database itself, but also for transitions. Simple data structures work well at small scales and within the bounds of a single machine. If you have an atom, it’s easy to store and transfer its value. But when you do `(swap! atom inc)`, you cannot transfer that anywhere. You can transfer new value (and it’s totally ok while you can keep it small), but not the change. For dataset sizes up from few hundred kilobytes you start to feel need to transfer deltas instead of entire value. Datomic’s transaction report is a format that allows you to express that delta as a data structure. Benefits follow.

Transaction format happens to match database format. Same exact queries can be run on both. Given a stream of all transactions going through your system, you can easily know when data you’re interested in changes. For example, you have a query that returns a list of items. To monitor for query result updates, you run same query over transaction’s data instead of entire DB and, when you get non-empty results, then some item has been changed, or new item has been added, or some item has been deleted. Note that we monitor not only for changes inside returned items, but for any changes *in a collection* of items that match or will match, once added, our query. And query doesn’t have to be simple `select * from table` query. It can include joins or filtering, and this property will still hold true. The ability to monitor query results’ relevance without querying entire database at each change is indispensable for reactive applications.

Transaction format is reversible. You can easily build a transaction that reverts another transaction. It sure is a nice property to have. I was completely unaware of that before seeing [Dave Dixon’s gist](https://gist.github.com/allgress/11348685).

Datomic model is compact and can be recreated from scratch. It opens the possibility for other languages to utilize same conventions and roll out alternate implementations. I love Rich’s work because he always spends extra time thinking how to reduce required development effort. If he happened, for example, to choose SQL for Datomic, we wouldn’t have any DataScript neither now nor in any foreseeable future. (and probably no Datomic either)

I can easily imagine a distributed system where parts talk to each other via tx-reports and peers run Datalog queries over data and subscribe to database changes. The only big obstacle on our way there is, of course, lack of open-source implementation of Datomic’s basic parts like in-memory index and Datalog query engine. While Cognitect still hasn’t open-sourced these, I started [DataScript](https://github.com/tonsky/datascript) to cover that breach for some time. I also see how open-source, lightweight library like DataScript may add value at server-side too, so there’s probably a Clojure version coming.

Given all that, I believe that Datomic was more important as a format, protocol, standard, than as a particular implementation. It sure has a lot of potential, and, what’s more intriguing, not all of its potential is completely understood today. Maybe it’ll change Clojure development landscape to the better — once again.