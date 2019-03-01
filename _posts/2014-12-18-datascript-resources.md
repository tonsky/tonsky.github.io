---
layout: post
title: "Couple of DataScript resources"
category: blog
summary: "Couple of new talks about DataScript"
img_rel: true
---

There’s couple of new resources available about DataScript.

On December 4th I gave a talk at Clojure eXchange conference about motivation behind DataScript, a little bit about internals, and then about how DataScript can be used for application development. Beyond traditional SPAs, there were couple of examples of new kind of architectures that are trivial to execute given that DataScript exists.

You can watch video of the talk [at SkillsMatter website](https://skillsmatter.com/skillscasts/6038-datascript-for-web-development) (free, registration required) and check out slides:

<p style="margin: 20px -38px; height: 538px"><script async class="speakerdeck-embed" data-id="5ba8bad06862013296c3468088921707" data-ratio="1.33333333333333" src="//speakerdeck.com/assets/embed.js"></script></p>

Later this month I talked at ClojureScript NYC user group. During the webinar we developed ToDo application from scratch and touched, at least quickly, almost every aspect of DataScript. Here’s the agenda:

- Create DB schema (multi-valued relations, references)
- Add ability to create tasks (basic `transact!`)
- Display list of tasks (basic query)
- Display tags on tasks (multi-valued attrs)
- Persist database to `localStorage` (serialization/deserialization)
- Make tasks completable (transact functions)
- Assign projects to tasks (entity navigation)
- Display task count for projects (aggregate queries)
- Display task count for inbox (“negate” query, query functions, query predicates)
- Display “by month” grouping (custom fn call in a query)
- Make left panel navigable (storing “view” app state in a db)
- Add filter (implicit OR via rules and collection bindings)

The recording:

<p style="margin: 20px -38px; height: 404px"><iframe src="//player.vimeo.com/video/114688970?byline=0&amp;portrait=0&amp;color=ff8c84" width="620" height="393" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe></p>

After the webinar I fixed couple of bugs in ToDo repo (and in DataScript as well), added comments here and there explaining what’s going on and implemented couple of new features:

- DB filtering
- Serialization via transit-cljs
- History tracking and undo/redo support

DataScript-ToDo should be a good resource for learning DataScript and its applications in the wild. Source code is [on github](https://github.com/tonsky/datascript-todo/tree/gh-pages/src), live version here:

<p class="fig" style="margin: 20px -38px;"><a href="http://tonsky.me/datascript-todo/"><img src="datascript-todo.jpg" style="width: 620px; height: 455px:" /></a></p>

Stay tuned!