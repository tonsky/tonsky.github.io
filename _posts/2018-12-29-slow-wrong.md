---
layout: post
title: "It is fast or it is wrong"
category: blog
summary: "Fast programs are always fast, slow programs are slow even on a most powerful computers. Knowing that fast solution exists makes slow one plain wrong."
hackernews_id: 18783704
img_rel: true
---

I’ve been doing some [Advent of Code](https://adventofcode.com/2018) programming recently (you can [watch the recordings](https://www.youtube.com/playlist?list=PLdSfLyn35ej-UL9AuxUvoFXerHac4RYnH)). They publish a small programming problem every day during December and you are supposed to write a program that solves it for you. It usually takes anything from a couple of minutes to a couple of hours and is pretty fun, I recommend that you try it too. Once the task is up, it’s always accessible, not only during December.

<figure><img src="advent.png"></figure>

One thing it taught me is that there are two types of solutions you end up with: ones that can calculate the answer in a couple of milliseconds and the ones that would take years to finish. If you end up with the second type, you’re doing it wrong. There’s no point in waiting, although _technically_ it might do the right thing too.

Another interesting observation is that it makes no difference what hardware you use to run it. If the solution is fast, it’ll be fast on a notebook as well as on a beefed-up workstation-class desktop. Sure, it might be twice or thrice slower, but it’ll be a difference between 10ms and 30ms. You still get your answer, so it doesn’t really matter.

If it’s slow, on the other hand, then you can throw at it any amount of computing power and it still won’t be enough. It might reduce the time from three years on a notebook to one year on a most powerful computer I can possibly build. So what? It’s still too long.

Now let’s get to software. It’s easy to call solutions of Advent Of Code wrong when they are slow since we know the fast solution has to exist. With real-world problems, nobody guarantees that.

Except sometimes.

Actually, quite often.

I’d say, almost always, in fact.

Let’s see. I have a library called [Datascript](https://github.com/tonsky/datascript). It’s a persistent data structure slash embedded database that, so it happens, is implemented for two platforms: JVM and JS. More so, it is actually written in Clojure and most parts of its codebase are shared between both platforms. It means that we know both versions always do the same motions. There’s a small layer that covers up platform-specific details like datatypes and standard library, but rest is shared. It’s not like one version is original and the other one is an inefficient port. They are both playing the same game.

You might think that if they do the same, they must perform the same, right? That would be logical to think so.

Let’s look at actual times it takes to compile the codebase and run full integration tests suite. We are talking about a codebase that is just a little over 9000 LOC, of which tests are 4000 LOC:

    Clojure 1.10 on JVM:
      REPL boot time: 1.5 sec
      Compile time:   6.5 sec
      Tests time:     0.45 sec
      
    ClojureScript 1.10.439 with advanced compilation:
      Compile time:   78 sec
      Tests time:     1 sec
      
    ClojureScript 1.10.439 without Google Closure compilation:
      Compile time:   24 sec
      Tests time:     1.3 sec

So what do these numbers tell us? Basically, to process exactly the same code, you can either spend ~8 seconds, 24 seconds or 78 seconds. Your choice. Also, by running the same program, you can get your result either in half a second, full second or close to one and a half second.

“_But wait, Tonsky, you can’t compare those! Those are apples and oranges! They are built to do completely different things! One is running in the browser!_”

Of course you can. Let me remind you: we compile exactly the same code, built to do exactly the same thing, using the same algorithms and running on the same hardware. The end result is the same in both cases: you either get your Datalog query answered in short time or in a long time. You either spend half of your workday waiting for a compiler or you spend it playing in the REPL, building stuff.

What do ClojureScript/Google Closure compilers do for so long? They are wasting your time, that’s what. Of course it’s nobody’s fault, but in the end, this whole solution is simply _wrong_. We can do the same thing much faster, we have proof of that, we have the means to do it, it just happens that we are not. But we could. If we wanted to. That huge overhead you’re paying, you’re paying it for nothing. You don’t get anything from being on JS, except a 2× performance hit and astronomical build times.

The same applies to all languages with terribly long build times. It’s not that they couldn’t build faster. They just choose not to. C++ or Rust program compiles for too long? Well, OCaml could probably compile the equivalent program in under a second. And it’ll still be machine-level fast.

“_Wow, wow, slow down! This is even more unfair! Now it’s not just apples and oranges, now it’s toothbrushes and spaceships. You completely ignore what each language brings to the table. There’s a reason they spend so much time compiling, you know?_”

I know. But still, I think you kind of can compare them. They are all general-purpose languages, after all, and in the end what matters is if you have a working program on your hand and if it can produce the answer in a reasonable time. It doesn’t really matter how the developer arrived there. You might get some comfort in thinking that it does, but nobody really cares.

Imagine this: there’s a plane from Moscow to Novosibirsk, the heart of Siberia, that takes 4 hours to fly 2800 kilometers. And there’s also a train that takes three days to cover the same distance. The train has no shower, bad food, beds you can’t sleep in. And the plane is a comfortable modern airplane. Which one would you choose? The price is the same. The only difference is your comfort and your time.

<figure><img src="train.jpg"></figure>

If we take this as a metaphor for software development, you’d be surprised that programmers are happily choosing train. They would even argue you to death there are indisputable reasons to choose the train. No, they don’t mind the compiler taking its time to “do the work”. Even though faster ways to get to the same destination exist. It’s easy to get lost arguing the details, but remember: _we all end up in the same place_, no matter what language we use.

Browsers? Same story. HTML is a pretty inefficient way to put pixels on a screen. A computer that might render millions of polygons a frame could easily struggle to scroll a web page. Same as with Advent of code solutions, it doesn’t really depend on how powerful your computer is. And even a highly optimized web code based on Canvas and WebAssembly ([Figma](https://figma.com)) makes my Macbook fans spin while running native Sketch in complete silence.

<figure><img src="crysis.jpg"></figure>

There’re just limits on how far this wrong solution can go. Electron text editors can’t resize their own window in real-time and drop frames while you just move your cursor around. Slack would be as slow and memory-hungry on iMac Pro as it would be on a 12" Macbook.

The whole solution, the “web stack”, _is wrong_. The same thing could be done faster and more efficient _easily_—there is just so much wasted potential. Time to admit that and start over. There are fast text editors and chat programs around, very well within capabilities of even the least powerful netbooks. 

I can go on and on. The thing to remember is: think what are you getting out of it. Are the problem and the resources wasted on it even comparable? It’s easy to find excuses why things are the way they are. They are all probably valid, but they are _excuses_. We know way faster programs _are possible_, and that makes everything else just plain wrong.