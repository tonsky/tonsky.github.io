---
layout: post
title: "Good times create weak men"
category: blog
summary: "Software abstraction ladder becomes too tall and starts to fall"
hackernews_id: 21932049
related_url: "/blog/disenchantment/"
related_title: "Software disenchantment"
---

_Translations: [Russian](https://habr.com/ru/company/itelma/blog/550620/)_

In [Software Disenchantment](https://tonsky.me/blog/disenchantment/) we’ve seen how software has degraded in the past two decades. Recently Jonathan Blow gave a talk where he explained why it might be happening:

<figure>
<iframe width="600" height="338" src="https://www.youtube.com/embed/pW-SOdj4Kkk" frameborder="0" allow="autoplay; encrypted-media; picture-in-picture" allowfullscreen></iframe>
</figure>

The talk is very, very good, but since I already know you won’t find time to watch it here’s my interpretation.

The software crisis is systemic and generational. Say, the first generation works on thing X. After X is done and becomes popular, time passes and the next generation of programmers comes and works on Y, based on X. They do not need to know, exactly, how X is built, why it was built that way, or how to write an alternative X from scratch. They are not lesser people or lazier, they just have no real need to write X<sub>2</sub> since X already exists and allows them to solve more pressing tasks.

The biggest a-ha moment of the talk was that if you are working on Y and Y is based on X, that does not imply automatically that you would know X also. Even if the people who build X are still around, knowledge does not spread automatically and, without actual necessity, it will go away with the people who originally possessed it.

This is counter-intuitive: most people would think that if we’ve built, for example, a space ship or a complex airplane in the past, we could build it again at any time. But no, if we weren’t building a particular plane uninterruptedly, then after just 50 years it is already easier to develop a new one from scratch rather than trying to revive old processes and documentation. *Knowledge does not automatically transfer to the next generation.*

In programming, we are developing abstractions at an alarming rate. When enough of those are stacked, it becomes impossible to figure out or control what’s going on down the stack. This is where my contribution begins: I believe I have found some pretty vivid examples of how the ladder of abstractions has started to fall and nobody can do anything about it now because we all are used to work only at the very tip of it.

Enter macOS Catalina. Every year Apple releases a new operating system and every year it needs a flagship feature to promote it. This year it was a long-overdue standalone Music app. Well, what could be simpler, right? List of files, categories, filters, smart lists. All that has been around in iTunes at least since 2001. But even if it wasn’t, how hard is it to build a decent music player? Many companies order of magnitude smaller than Apple have done it successfully in the past.

And yet, it didn’t go smoothly. Guys at [Annoying.Technology](https://annoying.technology/) have some great examples.

Clicking one thing [shows another](https://annoying.technology/posts/04769fafdac16826/):

<figure>
<video controls="" autoplay="" loop="" muted="" preload="auto" playsinline=""><source src="smartitunesstore.mp4" type="video/mp4"></video>
</figure>

The Music.app header [is rendering wrong](https://annoying.technology/posts/802dfae3517d4419/):

<figure>
  <img src="musicappcatalina.png">
</figure>

The problem is not limited to just Music. In Podcasts, some items are highlighted on selection, while others [are not](https://annoying.technology/posts/da8e5b3f56d9f767/):

<figure>
<video controls="" autoplay="" loop="" muted="" preload="auto" playsinline=""><source src="sidebarclickable.mp4" type="video/mp4"></video>
</figure>

As Philipp correctly mentioned,

> It’s not some odd, third-party utility that somehow looks a bit funky on an obscure version of macOS. It’s the flagship rewrite of the new Music.app shipping with Catalina.

I think this is a great example because all the possible explanations you might have for this if it was some other app, any other app, do not apply here.

Lack of attention? Well, they CHOSE to make Music app. They CHOSE to make it a flagship product of Catalina. So there must’ve been thousands of eyes looking and checking. Didn’t help.

Lack of resources? This is Apple, a company that could’ve hired anyone in the world. There are probably more people working on Music player than on the entire Spotify business. Didn’t help.

They simply do not care? Well, again, Apple has been known for its attention to detail in the past and it has served them well. I doubt they intentionally completely changed company priorities just in this case. It’s only logical to assume they did care, but it didn’t help.

This is just too far-fetching for a single bug? Well, the bugs are multiple and they contaminate all Catalina apps.

A bug is too obscure? Well, What could be more evident than clicking on one thing and not have it selected?

Lack of expertise? They’ve been building music players for 18 years now!

Is the problem too complex? Is the scope too big? Actually, no. Music app is less powerful and more limiting than iTunes. That’s expected, of course, since iTunes had its time to develop, but still. This is a freaking music player. It’s not rocket science!

Yes, these particular bugs are pretty minor and probably do not affect business in the short run, only Apple’s reputation. Still, *it is a big deal.* Imagine how tall, opaque and unstable that ladder of abstractions is that it’s even possible to fail such a simple thing as selecting an item in a list??? It is a freaking list and if you click it, it should select a thing that you just clicked. How hard of a task do you think that is? Why it has worked flawlessly since the first iPod with a monochrome screen and quarter of computing power of modern watch, but can’t be done in a flagship product of the most advanced operating system in the world?

Because advanced means complex. So complex that no one could reasonably understand it or have control over it, even if they wanted. Apple DID want it. But even they couldn’t. Even with all the resources in the world.

At this point, you might think I’m just picking on Apple or Catalina. God knows what went wrong there. Maybe they did change priorities and re-hired all the programmers. But no. This problem is universal.

Amazon can’t [make a screen with two checkboxes](https://twitter.com/nikitonsky/status/1206562961688596483):

<figure>
<video controls="" autoplay="" loop="" muted="" preload="auto" playsinline="" style="height: 600px"><source src="amazon.mp4" type="video/mp4"></video>
</figure>

I mean, yeah, Amazon was never known for producing quality software. Yet, I insist, the task is SO trivial, it’s so impossible to fail that it only demonstrates how bad people understand or control their tool.

Twitter newly rebuilt UI [takes 7× longer to load first tweet](https://www.youtube.com/watch?v=ZkxBzJ8lYAY), giving you essentially the same stuff but much later and with much more effort:

<figure>
<video controls="" autoplay="" loop="" muted="" preload="auto" playsinline=""><source src="twitter.mp4" type="video/mp4"></video>
</figure>

I don’t have numbers, but I’ve heard Gmail rewrite also made it much slower with no apparent new functions. It’s still pretty drastic if you put GMail next to Fastmail, or Twitter next to Tweetdeck, both of which didn’t get any full rewrites in the last decade, so you can see how fast even Web UI could be if we weren’t constantly climbing up the abstraction ladder.

Docker and Electron are the most hyped new technologies of the last five years. Both are not about improving things, figuring out complexity or reducing it. Both are just compromised attempts to hide accumulated complexity from developers because it became impossible to deal with.

And that’s sad.