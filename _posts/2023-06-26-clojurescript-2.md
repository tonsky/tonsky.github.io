---
layout: post
title: "A case for ClojureScript 2.0"
category: blog
summary: "Innocent early design decision that led to a disaster years later"
reddit_url: "https://www.reddit.com/r/Clojure/comments/14jfs48/comment/jplxjm3/"
---

I was [complaining the other day](https://twitter.com/nikitonsky/status/1671532290172649479) about the ergonomics of ClojureScript and realized an interesting thing.

Quick context:

- Clojure is a modern Lisp.
- ClojureScript is a Clojure dialect that compiles to JS.
- From its very beginning, it relied on the Google Closure compiler in an elaborate plan to confuse people with Clojure/Closure naming (joking).
- It has two important compilation modes: `:none` and `:advanced`.
- `:none` is what you are supposed to develop with.
- `:advanced` is what you ship: smaller bundle size, stripped of unused code, better performance, worse stacktraces.

So, I was complaining about compilation times and ergonomics of using `:advanced` mode and other devs could not understand me. Apparently, they all work in `:none` and their experience is much better.

This is where an interesting chain of cause and effect starts that leads (in my opinion) to what ultimately should become ClojureScript 2.0.

You see, the very existence of `:advanced` mode means you can’t really develop in `:none`.

I know, sounds like clickbait. Let’s unpack.

# Advanced makes your code faster

First, `:advanced` does not _just_ packages your code and trims the bundle size. It also improves its performance _and_ can change its behavior.

Better performance is fine, of course. Who doesn’t love a little bit of extra speed that computer gives you for free, with no work from your side?

(Rich Hickey, for one. He once famously [made a case](https://groups.google.com/g/clojure/c/apkNXk08Xes/m/CGCQqLMhlHwJ) why `last` should be slow where it could’ve been faster.)

Unless you are doing benchmarking (as I was), so you have to rely on `:advanced` and have to suffer worse experiences with everything else because of that.

# Advanced may break your code

You see, most bundlers (to my knowledge, I might be ignorant here) try to do the best they can while _not_ changing the behavior of your code. For example, if they can prove some code is unused, only then will they remove it. If they can’t, the code stays. Better safe than sorry.

Google Closure is different. Google Closure actively tries _to destroy_ your code. You have to _work_ against it to prove that your code is, in fact, used. Or that it shouldn’t be changed. Or that if you access, for example, `className` property on a JS object, it should stay named `className` and not be renamed to some `fy` or worse. The presumption of innocence does not apply here.

From ShadowCLJS README:

> Ideally we want to use `:closure` as our primary JS Provider since that will run the entire application through `:advanced` giving us the most optimized output. In practice however lots of code available via npm is not compatible with the aggressive optimizations that `:advanced` compilation does. They either fail to compile at all or expose subtle bugs at runtime that are very hard to identify.

# You can’t auto-generate externs. Nobody can

DataScript, unfortunately, is one of those libraries that need externs. Not because we do some weird shit there, but because of the nature of the problem.

Basically, queries are data, and data doesn’t get munged by Google Closure. But datoms are classes so their fields _are_ getting munged by default. That’s why we have to write `externs` to work around that.

It’s not always bad, though. I’ve heard stories that people can develop whole applications without ever experiencing this problem.

I’ve also heard that there’s some “AI magic” (meaning: highly indeterministic heuristics) that is supposed to “automagically” detect cases like that and just “do the right thing™”.

Which is supposed to be good, right?

Well, not exactly. The fact that auto-deduced externs exist means people might forget that externs are sometimes _essential_ for ClojureScript code to work.

For example, ShadowCLJS, one of the most popular ClojureScript dev tools today, ignores hand-written `externs` _by default_. Because they are supposed to be “automatically deduced”. And it works. Until it doesn’t. As you can guess, their users then come to me claiming that “DataScript is broken”.

Well, it is. But I didn’t break it. It’s the way we do things is broken.

BTW, did I tell you that upgrading your ClojureScript version might break things in new and exciting ways? Because it updates Google Closure, too, and the extern-deducing algorithm might change unpredictably between versions. And then it’s your problem, because, well, we didn’t really promise you anything, did we?

The ultimate promise of Google Closure compilation is: your code might work. It might not. It also might change between versions. Good luck.

# Why is advanced mode needed?

When ClojureScript started, the main premise was that people will build websites with it.

After 10 years, I’d say that ClojureScript is best suited for web apps, not pages. The minimal bundle size, the performance—you won’t really put stuff like that on your landing page.

But a productivity app? Custom editor? Some complex UI? Sure! People don’t really care about bundle size in that case. They are already committed to using it, they have a JS bundle probably cached (unless you release 10 times a day), so it’s much less of a problem.

What I’m saying is: since we are not getting into really super-small, super high-perf, low overhead JS territory, maybe we can relax our constraints a little and choose a less aggressive bundler? The one that maybe produces slightly less optimal code, but code that doesn’t subtly and unexpectedly break?

Is there really a difference between, say, a 500k bundle and a 1M bundle? A practical one? One that users will definitely notice in a meaningful way?

# Why not just always use `:none` mode?

It might seem that having more options is always better. Hey, do you want small bundle sizes and good perf? We got you covered. Great dev experience? We’ve got you too!

And that is partially true. For app developers, at least. I think some people just ship `:none` mode and it works for them. Why wouldn’t it?

For library authors, it’s worse. Because `:advanced` mode exists, just the fact of its existence, means we have to take it into account. We don’t really get to choose. People use it → we have to support it. In some sense having more options made life harder for us.

You can always look at a choice like that two ways. Tesla can charge your car for free or replace your battery with no waiting time. Free or fast, says Elon Musk. But it’s also a choice between slow or paid. PS5 games have performance mode or quality mode. A good picture or fast gameplay. Or: big latency or worse picture? You don’t just choose good parts here. You also choose bad ones.

# How is JVM Clojure doing?

It’s very interesting to look at what JVM Clojure is doing differently. This is how my rant on Twitter started, actually: I was wondering why on JVM, which is designed for statically-typed languages, the Clojure experience is much more dynamic than on JS, where it’s almost comparable with C++ development (long building times, lots of options, bad stacktraces, etc)?

Well, because ClojureScript accidentally complected two things: performance optimizations and minification. I know Clojure devs are trained to be scared of the word “complected”, and its use here is intentional: I am trying to scare you.

Look at Clojure experience. I develop without any notion of jars, classes, paths, etc. There are no compilation options either. It Just Works™. When the time comes, I can compile my Clojure classes (or not) and package everything into a jar, which I then ship.

So there _are_ two modes on JVM Clojure as well: dev mode and prod mode. Yet the dev code behaves _exactly_ how it will in production. There’s no compromise. No choice to make. I can safely work in dev mode until the time comes to ship my code. And I know I don’t even need to check it a second time—it’ll just work. It’s _guaranteed_ to work, even though the storage format (jar) is different.

Why can’t it be that way in ClojureScript? Because it uses Google Closure for both _bundle size_ and _performance_ optimizations.

You see, the ClojureScript compiler outputs less performant code and relies on Google Closure to improve its performance. So even if you personally are ok with larger bundles, it’s still really hard to leave free performance on the table.

# What if advanced mode didn’t exist?

So what am I proposing? Basically,

1. Ditch Google Closure.
2. Move whatever performance optimizations it does into the ClojureScript compiler. Or accept that it’ll be slightly slower.
3. Use whatever bundler JS people use. Even if it outputs larger bundles. It’s okay, ClojureScript is already pretty thick anyways. The important part is that it should only make safe transformations and not try to destroy your code.

JS is an ecosystem. A strange one, but a huge one, too. So it was a very strange choice to ignore it completely or make it really hard to use. One of the selling points of JVM Clojure always was: to use whatever Java libraries you need. Using Java from Clojure is easier than from Java (not kidding).

Whereas in ClojureScript it’s more like: don’t use JS libraries. It’s very hard. There are a million “buts”. Are you in node or a browser?

That’s not the spirit, I would say.

And no matter what Rich Hickey's reasoning was, Google Closure is _not_ part of the JS ecosystem. Nobody uses it, except, maybe, for Google.

Getting rid of Google Closure will make interop with JS much simpler (as far as I understand). So we will only potentially lose a little bit in bundle sizes and (maybe) performance? But there are so many low-hanging performance fruits in ClojureScript anyways maybe nobody will notice.

What’s important is what we’ll gain:

<center>The Simplicity.<br><br>

Ease of Mind.<br><br>

Hapiness.</center>

I fell in love with Clojure because of how simple everything was. To this day I’m still reflecting on how the same things are unnecessarily complicated in other languages.

And I wish the same for ClojureScript users, I want them to feel the same transformative experience.

# Where are your patches?

I know getting rid of Google Closure is a huge step. I’m not even sure if that’s possible in the current implementation or the current ecosystem. 

That’s why I called this post ClojureScript 2.0. It’s a huge change. Lots of work. But I believe it’s the right path.

I also believe Michiel Borkent is working in the right direction with [Cherry 🍒](https://github.com/squint-cljs/cherry). I don’t know all the details but it looks like how I imagined the Clojure compiler for JavaScript _should_ look like. So maybe help him out?

All in all, the goal of this post was not to diss on ClojureScript. It’s absolutely great that it exists, and its existence has been paying my bills for the last seven(-ish?) years at least. I just was excited that I finally saw how a very early decision (use Google Closure) eventually led to “Clojure feels like the future, ClojureScript feels like developing C++” in some cases. I hope I described that path clearly enough and it’s of interest to you too.

Again, I’m not saying it’s wrong, bad, or anything, or that anybody should’ve predicted it. It took me 10 years to realize what was going on. I only hope it will help someone in the future if any new initiatives get developed.

Peace.