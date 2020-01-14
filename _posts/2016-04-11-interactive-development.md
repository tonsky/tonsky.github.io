---
layout: post
title: "The Blessing of Interactive Development"
category: blog
summary: "In this post I describe a couple of practice that makes the process of writing code faster, more predictable and straightforward"
img_rel: true
starred: true
---

## How do you write code?

We have plenty of literature on _what_ code to write, but rarely you see a piece about the process of writing itself. Some might think it’s programmers’ own business and everybody is free to use whatever tools they like. I believe that writing process—what I use for writing, where do I start, which parts do I build in what order, all the little motions—in other words, the “getting there”—is as important as the end result. Writing habits have a direct impact on my performance and the code I produce. This is an area of software development that should be discussed, studied and trained as an important part of coding craftsmanship.

I like to reflect on how my own writing process has evolved over the years. It had periods of gradual improvement and moments of radical changes. From what you’re about to read, it’s not even obvious whether it improved at all—some moments might look like a step back. I can assure you that in practice I do feel a strong cumulative positive effect: after all, I work on much more complex things now compared to ten years ago, and I’m for sure doing more. Otherwise, judge for yourself.

## Price of help

I started my career with Java and Java IDEs it was. The benefits IDEs advertise are of that kind where it’s hard to say no: they offer to spare you some pain (or, depending on a language, _a lot_ of pain). And they deliver, no doubt about that. What they don’t tell you, and what is not as widely discussed, is _a catch_ I started to notice only after five plus years of happy IDEing: there’s the dark side. As human mind works, if somebody or something helps you with a problem, you start to not see it as a problem. But the problem does not go away. You just lose the ability to see it.

When IDE mitigates the problem, you forget about it and that’s the moment when you lose the motivation to fight it. Huge codebase. Unnecessary code. Bad structure. Generated code you have no idea about. These problems are pretty serious, but they do not affect your nearest goals, we all have priorities, so you let them build up for a while. The problems are there, but you’ve lost the urge to fix them. Until, of course, it’s too late. Given the reality of software projects, good intentions and “knowing about it” do not help. What can help, what can generate enough motivation to keep your project in a good shape is the itch. You must feel the itch all the time. Paradoxically, harder working conditions push you towards better code.

Realizing all that, I concluded that IDE is a great tool to work with already rotten legacy projects, but a terrible aid to write new code.

## Going plain text 

And I wanted to write new code. To clean up my mind, I took a break from IDE-driven development and moved to barehanded text editors: Vim, Textmate, Sublime Text. It’s hard to remember exact motivation for the change (lack of IDE for Erlang? Multiple languages in a project? Hardware too slow for IDEA?), but the value of it I see clearly in hindsight.

Working with program’s text directly, without an autopilot, lets you really feel what your program is made of. It makes you _aware_. A software project is not a big shapeless thing that let you accidentally type stuff inside anymore. You value each line—you’ve typed each line with your own hands. You know exactly what, where, when and why happens. You own every piece, without “I write this part, hope everything else will work OK too” attitude. You become less tolerant for unnecessary formalities, bloated abstractions, excess future proofing—all types of noise. Imagine turning on a bright light in a poorly lit room: what was hidden in the shadows stands out immediately. The first step to improvement is to see.

## Interactive development

Now I’m in the era of interactive development. It builds on the fact that code we commit was not born in its final form—it was built, with trial and errors, via many iterations before it hit source control. Sound simple, but notice how little tools support code in transitional states, in spite of the fact that for developer code exists in a transitional state most of the time. The idea of interactive development is to enhance individual iterations so the resulting code will be born faster and with better quality. Following are the principles I’m using.

*Build in small chunks.* You can’t solve anything but smallest bugs in a single try. If you write more than a couple of lines before actually running it, chances are they won’t work and you wouldn’t know why. Say hello to long debugging sessions, running around checking all sort of crazy hypotheses and trying to isolate parts to check them separately. This is backward. The straightforward way is to move in small steps: build a helper function, test it, see it works, then move on. It resembles test-driven development, but on a finer, often sub-function level: write a function declaration, check arguments are passed in right, write a loop, check it iterates, add regexp match, check it really matches. It doesn’t always mean bottom up development—you might start by adding stub interface and see if a system will adequately react to its presence. Just do a single small change, see if it works, then move on. Always check by actually running the code. Don’t try two changes at once.

*Quick turn-around*. Testing each smallest change won’t be possible if it’d take more than a couple of seconds to run a piece of code and see results. There’re a couple of psychological limits at play: everything faster than ~100 ms human will _perceive_ as immediate feedback, ~1–2 seconds is quick feedback, they feel latency but keep the attention, everything beyond that breaks attention and human will need to spend huge amount of time and effort to get back to the context they were in. For 10+ second delays, human will switch to another task altogether. Best tools will not let human lose their attention, otherwise, _the flow_ gets broken and development time grows exponentially. There’s qualitative difference of development speed between systems with sub-second iteration time and systems with 2+ seconds iteration time. Funny enough, there’s not much difference between systems with 2 sec delay and 9 sec delay, or between 15 seconds and 3 minutes. It’s either really quick or “my code is compiling” and nothing gets done.

*Keep the state*. Sounds a little bit utopian so far? Even if you can compile and run your whole project in a sub-second time, you’ll still need time to get the context right. You don’t run code in isolation, it needs particular system parts be hot and running, particular arguments prepared, stubs/fixtures (if you use them) initialized. This is where tests help—they let you _automate context preparation_. On the smaller sub-function scale, writing tests would be a waste of time, though. Tests are also no option when you work on something visual, like UI. This is the moment where you want to work in a live system. Some languages allow you to connect to working process and evaluate code there as you go (REPL). Nothing gets restarted, just create context once and work inside it as long as you need.

*Don’t leave the editor*. This is the same “keep the context” argument, but applied to the developer. You write code in the editor, right? Switching to another window to test and see would be too expensive from attention preservation standpoint. It’s worse if you have to synchronize the content of the editor and the content of the REPL for example. Total waste of time. Instead, evaluate code and see results in the same place where you write it—in the editor. You’ll need a proper editor, of course.

*Open the hood*. It would be simpler if everything consisted of small tightly isolated bite-size chunks. But it isn’t, and one day you’d need to work on a heavily integrated part of the system. Take some time and rework parts that will help you create context, write new code in small chunks and test it as you go. Just prepare the system to be worked from inside. If you can leave it in that state, the better, but sometimes you just do a couple of dirty hacks and revert them after you’re done with your code.

Here’s a quick video to give you an idea how interactive development might actually looks like, given the right tools and setup:


<figure>
<iframe width="600" height="338" src="https://www.youtube.com/embed/XEMI5-MBgaM" frameborder="0" allowfullscreen></iframe>
(You can grab my LightTable skin <a href="https://github.com/tonsky/alabaster-lighttable-skin" target="_blank">here</a>)
</figure>

What now? Even if you’re completely sold on the ideas, chances are you won’t be able to apply most of them immediately. My intention is to show you where to aim at and let you find your opportunities for yourself. Even if you can’t do everything, try to get as close as you can (e.g. unit tests + quick turnaround is pretty good, devtools console is a REPL, many compilers have fast compile times, etc). This is not an easy path, not a convenient path, not the most popular one, but I can assure you it’s worth it.
