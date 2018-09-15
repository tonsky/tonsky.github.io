---
layout: post
title: "Software disenchantment"
category: blog
summary: "Everything is going to hell and nobody seems to care"
draft: true
---

I’ve been programming for 15 years now. Recently our industry’s lack of care for efficiency, simplicity and excellence started really getting to me, to the point of me getting depressed by my own career and the IT in general.

Modern cars work, let’s say for the sake of argument, at 98% of what’s physically possible with the current engine design. Modern buildings use just enough material to fulfil their function and stay safe in the the given conditions. All planes converged to the optimal size/form/load and basically look the same.

Only in software it’s fine if program runs at 1% or even 0.01% of the possible performance. Everybody just seems to be ok with it. People are often even proud about how much inefficient it is, as in “why should we worry, computers are fast enough”:

> [@tveastman](https://twitter.com/tveastman/status/1039002300600147968): I have a Python program I run every day, it takes 1.5 seconds. I spent six hours re-writing it in rust, now it takes 0.06 seconds. That efficiency improvement means I'll make my time back in 41 years, 24 days :-)

You’ve probably heard this mantra: “programmer’s time is more expensive that computer’s time”. What it means basically is that we’re wasting computers at unprecedented scale. Would you buy a car if it eats 100 liters per 100 kilometers? How about 1000 liters? With computers, we do that all the time.
  
<figure><a href="https://xkcd.com/2021/"><img src="software_development_2x.png" height="450"></a></figure>

## Everything is unbearably slow

Look around: our portable computers are thousand of times more powerful than the ones that brought man to the moon. Yet half of webpages struggle to maintain smooth 60fps scroll on the latest top-of-the-line MacBook Pro. I can comfotably play games, watch 4K videos but not scroll web pages? How is it ok?

Google Inbox, a web app written by Google, running in Chrome browser also by Google, [takes 13 seconds to open moderately-sized emails](https://twitter.com/nikitonsky/statuses/968882438024941568):

<figure><iframe src="https://twitter.com/i/videos/tweet/968882438024941568?embed_source=clientlib&player_id=0&rpc_init=1&autoplay=1&language_code=en&use_syndication_guest_id=true" style="width: 650px; height: 406px" allowfullscreen allow="autoplay; fullscreen" frameBorder="0"></iframe></figure>

It also animates empty white boxes instead of showing their content because it’s the only way anything can be animated on a webpage with decent performance. No, decent doesn’t mean 60fps, it’s rather “as fast as this web page could possibly go”. I’m dying to see web community answer when 120Hz displays become mainstream. Shit barely hits 60Hz already.

Windows 10 [takes 30 minutes to update](https://grumpy.website/post/0PeXr1S7N). What could it possibly be doing for that long? That much time is enough to fully format my SSD drive, download fresh build and install it like 5 times in a row.

<figure><img src="windows_update.png" ></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/): Typing in editor is a relatively simple process, so even 286 PCs were able to provide a rather fluid typing experience.

Modern text editors have higher latency than 42-year-old Emacs. Text editors! What can be simpler? On each keystroke all you have to do is update tiny rectangular region and modern text editors can’t do that in 16ms. Which is a lot of time. A LOT. A 3D game can fill whole screen with hundreds of thousands (!!!) of polygons in the same 16ms and also process input, recalculate the world and dynamically load/unload resources. How come?

## Everything is HUUUUGE

And then there’s bloat. Web apps could open up to 10× faster if you just simply block all ads. Google begs everyone to stop shooting themselves in their feet with AMP initiative—a technology solution to a problem that doesn’t need any technology, just a little bit of common sense. If you remove bloat, web becomes crazy fast. How smart do you have to be to understand that?

Android system with no apps [takes almost 6 Gb](https://grumpy.website/post/0Oz1lDOq5). Just think for a second how obscenely HUGE that number is. What’s in there, HD movies? I guess it’s basically code: kernel, drivers. Some string and resources too, sure, but those can’t be big. So, how many drivers do you need for a phone?

<figure><img src="android_storage.jpg"></figure>
  
Windows 95 was 30Mb. Today we have web pages heavier than that! Windows 10 is 4Gb, which is 133 times as big. But is it 133 times as superior? I mean, functionally they are basically the same. Yes, we have Cortana, but I doubt it takes 3970 Mb. But whatever Windows 10 is, is Android really 150% of that?

Google keyboard app routinely eats 150 Mb. Is an app that draws 30 keys on a screen really five times more complex than the whole Windows 95? Google app, which is basically just a package for Google Web Search, is 350 Mb! Google Play Services, which I do not use (I don’t buy books, music or videos there)—300 Mb that just sit there and which I’m unable to delete.

<figure><img src="apps_storage.png" height="500"></figure>

All that leaves me around 1 Gb for my photos after I install all the essential (social, chats, maps, taxi, banks etc) apps. And that’s with no games and no music at all! Remember times when an OS, apps and all your data fit on a floppy?

Your desktop todo app is probably written in Electron and thus [has userland driver for xbox 360 controller in it](https://josephg.com/blog/electron-is-flash-for-the-desktop/), can render 3d graphics and play audio and take photos with your webcamera.

<figure><img src="slack_memory.png"></figure>

A simple text chat is notorious for its load speed and memory consumption. Yes, you really have to count Slack in as a resource-heavy application. I mean, chatroom and barebones text editor, those are supposed to be two of the less demanding apps in the whole world. Welcome to 2018.

## Everything rots

16Gb Android phone was perfectly fine 3 years ago. Today with Android 8.1 it’s barely usable because each app has become at least twice as big _for no apparent reason_. There are no additional functions. They are not faster or more optimized. They don’t look different. They just...grow?

iPhone 4s was released with iOS 5, but can barely run iOS 9. And it’s not because iOS 9 is that much superior—it’s basically the same. But their new hardware is faster, so they made software slower. Don’t worry—you got exciting new capabilities like...running the same apps with the same speed! I dunno.

iOS 11 dropped support for 32 bit apps. That means if developer isn’t around at the time of iOS 11 release or isn’t willing to go back and update once-perfectly-fine app, chances are you won’t be seeing their app ever again.

> @[jckarter](https://twitter.com/jckarter/statuses/1017071794245623808): A DOS program can be made to run unmodified on pretty much any computer made since the 80s. A JavaScript app might break with tomorrow’s Chrome update

Webpages working today [would not be compatible with any browser in 10 years time](http://tonsky.me/blog/chrome-intervention/) (probably sooner).

“It takes all the running you can do, to keep in the same place”. But what’s the point? I might enjoy occasionally buying new phone and new macbook as much as the next guy, but to do so just to be able to run all the same apps which just became slower?

## Worse is better

Nobody understands anything at this point. Neither they want to. We just throw barely baked shit out there, hope for the best and call it “startup wisdom”.

Web pages ask you to refresh if anything goes wrong. Who has time to figure out what happened?

<figure><img src="reload.png"></figure>

Any webapp produces constant stream of “random” JS errors in the wild, even on compatible browsers.

The whole webpage/SQL database architecture is built on a premise (hope, even) that nobody will touch your data while you look at rendered webpage.

Most collaborative implementations are “best effort” and has many common-life scenarios in which they lose data. Ever seen this dialogue “which version to keep?” I mean, bar today is so low that your users would be happy to at least have a window like that.

<figure><img src="icloud_conflict.png"></figure>

And no, in my world app that says “I’m gonna destroy some of your work, but you get to choose which one” is not okay.

Linux kills random processes _by design_. And yet it’s the most popular server-side OS.

Every device I own fails regularly one way or another. My Dell monitor needs hard reboot from time to time because there’s software in it. Airdrop? You’re lucky if it’ll detect your device, otherwise, what do I do? Bluetooth? Spec is so complex that devices [won’t talk to each other](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/) and [periodic resets are best way to go](http://time.com/4358533/bluetooth-fix-how/).

<figure><img src="plz_connect.jpg"></figure>

And I’m not even touching [Internet of Things](https://twitter.com/internetofshit). It’s so far beyond the laughing point I’m not even sure what to add.

## Programming is the same mess

It just seems that nobody is interested in building quality, fast, efficient, lasting, foundational stuff anymore. Even when efficient solutions has been known for ages, we still struggle with the same problems: package management, build systems, compilers, language design, IDEs. 

Build systems are inherently unreliable and periodically require full clean, even though all info for invalidation is there. Nothing stops us from making build process reliable, predictable and 100% reproducible. Just nobody thinks its important. NPM has stayed in “sometimes works” state for years.

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145): It seems to me that `rm -rf node_modules` is indispensable part of workflow when developing Node.js/JavaScript projects.

And build times? Nobody thinks compiler that works minutes or even hours is a problem. What happened with “programmer’s time is more important”? Almost all compilers, pre- and post-processors add significant, sometimes disasterous time tax to your build without providing proportionally substantional benefints.

<figure><a href="https://xkcd.com/303/"><img src="compiling.png"></a></figure>

You would expect programmers to make mostly rational decisions, yet sometimes they do the exact opposite of that. E.g. choosing Hadoop [even when it’s slower than running the same task on a single desktop](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html).

Machine learning and “AI” moved software to guessing in the times when most computers are not even reliable enough in the first place.

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136): When an app or a service is described as “AI-powered” or “ML-based”, I read it as “unreliable, unpredictable, and impossible to reason about behavior”. I try to avoid “AI” because I want computers to be the opposite: reliable, predictable, reasonable. 

We put virtual machines inside Linux, and then we put Docker inside virtual machines, simply because nobody was able to clean up the mess that most programs, languages and their environment produce. We cover shit with blankets just not to deal with it. “Single binary” is still a HUGE selling point for Go, for example. No mess == success.

<figure><a href="https://xkcd.com/1987/" target="_blank"><img src="python_environment_2x.png"></a></figure>

And dependencies? People easily add overengineered “full package solutions” to solve simplest problems without considering their costs. And those dependencies bring other dependencies. You end up with a tree that is something in between of horror story (OMG so big and full of conflicts) and comedy (there’s no reason we include these, [yet here they are](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)):

<figure><img src="dependencies.png"></figure>

Programs can’t work for years without reboots anymore. Sometimes [even days are too much to ask](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer). Random stuff happens and nobody knows why.

What’s worse, nobody has time to stop and figure out what happened. Why bother if you can always buy your way out of it. Spin another AWS instance. Restart process. Drop and restore whole database. Write a watchdog that will restart your broken app every 20 minutes. Move fast, don’t fix.

## We’re stuck with it

So everything is just a pile of barely working code added on top of previously written barely working code. It keeps growing in size and complexity, diminishing any chance for a change.

To have a healthy ecosystem you _need_ to go back and revisit. You _need_ to occasionally throw stuff away and replace it with better stuff.

<figure><img src="design_process.jpg"></figure>

But who has time for that? We haven’t seen new OS kernels in what, 25 years? It’s just too complex to simply rewrite by now. Browsers are so full of edge cases and historical precedents by now that nobody dares to write layout engine from scratch.

Today’s definition of progress is either throw more fuel into the fire:

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712): 2014 - We must adopt #microservices to solve all problems with monoliths.<br />2016 - We must adopt #docker to solve all problems with microservices.<br />2018 - We must adopt #kubernetes to solve all problems with docker

or reinventing the wheel:

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112): 2000: Write 100s of lines of XML to "declaratively" configure your servlets and EJBs.<br />2018: Write 100s of lines of YAML to "declaratively" configure your microservices.<br />At least XML had schemas...

We’re stuck with what we have, and nobody will ever save us.

## It’s not all bad

There are some bright spots indicating that improving over state-of-the-art is not impossible.

Work [Marthin Tompson](https://twitter.com/mjpt777) has being doing ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) is impressive, refreshingly simple and efficient.

[Xi editor](https://github.com/google/xi-editor) by Raph Levien seems to be built with right principles in mind.

[Jonathan Blow](https://www.youtube.com/user/jblow888) has a language he alone develops for his game that can compile 500k lines per second on his laptop. That’s cold compile, no intermediate caching, no incremental builds.

You don’t have to be a genius to write fast programs. There’s no magic trick. The only thing required is not building on top of huge pile of crap that modern toolchain is.

## Better world manifesto

I want to see progress. I want change. I want to see an end goal, I want us to move towards it. I want something to believe in, to see future better than what we have today, and I want a community of engineers who share that vision.

What we have today is not progress. We’re stuck in local optima and nobody wants to move out. It’s not even a good place, it’s bloated and inefficient. We just somehow got used to it.

So I want to call it out: where we are today is bullshit. As engineers, we can, and should, and will do better. We can have better tools, we can build better apps, faster, more predictable, more reliable, using less resources (orders of magnitude less!). We can—and should–take pride in our work. Not just “given what we had...”—no buts!

I hope I’m not alone at this. I hope there are people out there who want to do the same. I’d appreciate if we at least start talking how absurdly bad our current situation in software industry is. And then we maybe figure out how to get out.