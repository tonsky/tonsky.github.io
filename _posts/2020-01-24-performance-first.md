---
layout: post
title: "Performance first"
category: blog
summary: "“Premature optimization being the root of all evil” is the root of all evil"
draft: true
---

How does one build fast software?

If you are a programmer, you’re probably familiar with this Knuth quote:

> Premature optimization is the root of all evil.

Many programmers seem to believe that’s an ok way to develop products:

![](kotlin.png)

![](jetpack.png)

First, it’s not an excuse for [not thinking about performance at all](https://medium.com/@okaleniuk/premature-optimization-is-the-root-of-all-evil-is-the-root-of-evil-a8ab8056c6b). There’s no proud in doing sloppy job. It’s an attitude like this that brought Software Disenchantment upon us.

But even if you don’t take Knuth’s quote as an excuse, I still see flaws in this logic. If your program is still a prototype and does, for example, 1% (20%, 50%, 90%) of what it’s supposed to do, _and it is already slow_, then it’ll only be slower after you finish it, no? You will make it do more, why would it become faster?

Some can say: 

“We are building programs correct first, performant later. We will optimize it after it’s feature complete.”

What that actually means is: performance would stay mostly the same, _unless_ those people find some low-hanging fruits that will allow them to make the program fast without changing too much of what they’ve already built.

And I have a problem with that. This more or less equals to leaving final performance in the hands of a blind chance. IF you manage to find some huge performance bottleneck and IF altering it would not affect architecture, you MIGHT get some speedups, yes. But nobody can _guarantee_ you that. It’s a bet. You either do or you don’t. Basically, you’re accepting whatever performance you’ll get with a slight chance for a slight improvement. Is that what you call good engineering?

My take is this: if you want to build a really fast program, pay attention to the performance from the start. It’s never too early to start measuring and working on performance. Your prototypes should be blazing fast, way faster than the final program. For one, if you start from the fast program, it’s much easier to keep performance from degrading than starting from a slow program and hoping you’ll find an easy way to speed it up.

Another point is that if performance is a priority, it might affect product decisions too. If you are planning a feature but figure out early it can’t be made fast, you still are in a good position to reconsider. Are there fast alternatives? Do you want that feature at all? Can it be replaced with another approach? You might end up with a different product — but it’ll stay fast. In contrast, if something is already part of the product and you can’t figure out a way to make it performant, you have much fewer options. Most of the time, you’ll be stuck with such slow features forever.

History is full of programs that were made faster after the release. These examples are just a few things that pop in my memory: Chrome is known for pioneering many JS speed improvements. Both Kotlin and Rust compilers have seen many speedups. VS Code / Atom eventually became faster versions of their original Electron prototypes. And I’m not saying it’s impossible to speed up programs after release. I’m saying these improvements are accidental. It’s sheer luck they happened. They could have not as easily as they did. You might consider 10x speed improvement that JS has seen to be impressive, but also remember this: even though thousands of people are working full-time on speeding it up, it still can’t be made as fast as C++ or even Java. You’ll have to change the language to do that, but it’s too late to do that.

Let me put it this way:

“Premature optimization being the root of all evil” is the root of all evil.