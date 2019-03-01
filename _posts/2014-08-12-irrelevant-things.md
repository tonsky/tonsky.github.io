---
layout: post
title: Irrelevant Things
category: blog
hackernews_id: 8167186
summary: "As a programmer on a way to technical excellency, you should teach yourself to constantly spot and reduce waste."
img_rel: true
---

I had a conversation once, whether trailing spaces should be removed from code or not. Real conversation, with an actual person, with “arguments”, “pros and cons”. Even appeals to taste. Half an hour lost on something that isn’t a problem at all. Trailing spaces are okay, not having trailing spaces is okay too, I guess; I’m only concerned when somebody cares about such irrelevant thing.

A lot of people care, to be frank. Enough to have plugins written for all major editors to check, highlight, eliminate trailing spaces. To make invisible, not-a-problem thing bold, visible disaster. But I’m more or less fine with this waste, I can accept it being small compared to the fact there’s a whole bunch of _companies_ building their _businesses_ around to-do applications. Even guy who wrote JavaDoc generator for getters in Eclipse seems to be making Earth a better place compared to that.

<p class="fig">
<img src="todos.png" style="width: 320px; height: 480px;">
There’s always a room for innovation
</p>

It’s easy to spot [muda](http://en.wikipedia.org/wiki/Muda_(Japanese_term)) in well-known holywars. But a lot of everyday programmer’s activities fall into the same category. Dependency management, for example. If you spent a day setting up compilation workflow and getting dependencies right, it’s not a day of good work. It’s a day lost. You haven’t created new value, you haven’t enabled a single person to do anything that wasn’t possible before. You were [satisfying other programs’ demands](http://www.lighttable.com/2014/05/16/pain-we-forgot/). Even the fact that this activity has its own name indicates there’s something wrong with it. I hope there isn’t an actual job title like “Dependency management engineer”, is there? I probably don’t want to know.

As a programmer on a way to technical excellency, you should teach yourself to constantly spot and reduce waste. At their very essence, programs are about converting information: data in, data out. When you understand that, you can tell, for every piece of code in your system, if it’s essential for _the_ task, or not. Kind of universal criteria. If it converts X to Y, it’s _probably_ doing something valuable. If it’s just passing data around, it’s _probably_ a waste. Every case is different, but overall these are good markers to trigger alarm.

There’s some point in having full-blown class hierarchies, or paranoid incapsulation, or split interface and implementation for every other bit of your program. But, with high degree of probability, you can get away without that perfectly fine. You can build modern, competitive [10K LOC systems](https://github.com/LightTable/LightTable) without a single class. You can build small, ridiculously sophisticated things [in a very short time](http://thenewstack.io/the-new-stack-makers-adrian-cockcroft-on-sun-netflix-clojure-go-docker-and-more/). Here’s a whole [operating system under 20K LOC](https://news.ycombinator.com/item?id=1114410). And we’re not talking about compromising quality or feature set. We’re talking about taking away non-essential parts.

There are practices to keep code ready for changes, but those are only for changes you can foresee. By leaving stuff out and keeping codebase small, you’re making it ready for _any change_ because there’s so little code to change in the first place. Small codebase is a valuable asset per se.

<p class="fig">
  <img src="getters.png" style="width: 320px; height: 480px;">
  How much value was added on this screen?
</p>

So the idea is to go with as little things as possible. Then look at yourself. Is your life getting easier? It probably won’t right away, as you’ll be kicked off your comfort zone and will have to fight habits. Habits are not good indicators of anything, they’re accidental, so let some time pass. Does it become harder to get tasks done? Are you slowing down or speeding up? Have you completed more? Has quality of your work increased?

Give it a try, it’s not lethal. Do a project without ORM, use SQL instead. Do not create a constant, use a string. Do not use inheritance. Put everything in a single file. Start writing code in a text editor. Interesting things may happen. Without help of an IDE, for example, your code may become better. There’s [a flaw in human nature](http://usabilitypost.com/2011/01/10/dark-side-of-usability/) saying that the more help you get on a task, the less deep you’re actually involved.

I’m not saying all of these things are good. They may be situational, they may not worth it at all. But real reasons, real value of things is often not on a surface, it’s so far away from conventional wisdom you need to do something radical to reveal it. You need to clear your vision. That’s the point. Even if you return, you’ll know exactly why you’re returning. What you cannot live without, and what you can dispense with. I haven’t touched IDEs for three years, and now I’m returning, but not for the usual stuff (highlighting, refactorings, automation — effect from them is negligible). I’m returning for interactive development. Turns out it really changes the game.

By going ascetic, you’ll develop new meanings for good and bad. You’ll learn that good tool is not the one with billion modules already written. Good one makes writing your own module a no-brainer. You’ll learn that good things value your time. Wasting time is a shame, and you should constantly look for a ways to reduce waste. You’ll have a small amount of tools in your hand, and each one of them has to be deep. Some widely adopted engineering practices will fade away, some will stick. The point is, again, to know exactly why you’re doing what. It’s a question of life and death now, not just fashion or habits.

The rest is for you to figure out. Different experience may lead to different results, but overall it’s very beneficial to constantly reflect and question if you can live without things. The less you need, the more powerful you are.


