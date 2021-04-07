---
layout: post
title: "Performance first"
category: blog
summary: "“Premature optimization being the root of all evil” is the root of all evil"
---

_Translations: [Russian](https://habr.com/ru/company/itelma/blog/550432/)_

How does one build fast software?

# The wrong way

If you are a programmer, you’re probably familiar with this Knuth quote:

> Premature optimization is the root of all evil.

Many programmers seem to believe that’s an ok way to develop products:

![](kotlin.png)

Some also think that performance is just another feature that can be added later:

![](jetpack.png)

I think this logic is flawed. If your program is still a prototype and does, for example, 1% (20%, 50%, 90%) of what it’s supposed to do, _and it is already slow_, then it’ll only be slower after you finish it, no? You will make it do more, why would it become faster?

If someone says: 

> We are building programs correct first, performant later. We will optimize it after it’s feature complete.

It actually means: performance would stay mostly the same, _unless_ those people find some low-hanging fruits that will allow them to make the program fast without changing too much of what they’ve already built.

And I have a problem with that. This more or less equals to leaving final performance in the hands of a blind chance. IF you manage to find some huge performance bottleneck and IF altering it would not affect architecture, you MIGHT get some speedups, yes. But nobody can _guarantee_ you that. It’s a bet. You either do or you don’t. Basically, you’re accepting whatever performance you’ll get with a slight chance for a slight improvement. Do you call that good engineering?

It might appear that history is full of programs that were made faster after the release. These examples are just a few things that pop in my memory: Chrome is known for pioneering many JS speed improvements. Both Kotlin and Rust compilers have seen many speedups. VS Code / Atom eventually became faster versions of their original Electron prototypes. And I’m not saying it’s impossible to speed up programs after release. I’m saying these improvements are accidental. It’s sheer luck they happened. They might’ve never happen just as easily as they did.

# The right way

My take is this: if you want to build a really fast program, pay attention to the performance from the start. It’s never too early to start measuring and working on performance. Your prototypes should be blazing fast, way faster than the final program.

For one, if you start from the fast program, it’s much easier to keep performance from degrading than starting from a slow program and hoping you’ll find an easy way to speed it up.

Then, if you are really serious about your final program’s performance, every decision must be made with performance in mind. Platform, language, architecture, framework, user interface, business tasks, business model. Can this be made fast? Can we use language X or framework Y, could they be made fast? Can we make this feature fast? If not, what can we replace it with? How to make UI fast? How to make it appear fast? These sort of decisions are easy to make early on, but impossible to change later. For example, JavaScript has seen impressive speed improvements over the years, but it still can’t be made as fast as C++ or even Java. If you goal was to build a fast language, you’d end up with a different language!

Let me put it this way:

> “Premature optimization being the root of all evil” is the root of all evil.