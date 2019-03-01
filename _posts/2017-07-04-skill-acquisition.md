---
layout: post
title: "Skill acquisition"
category: blog
summary: "Dreyfus model of skill acquisition explains what happens in IT industry"
draft: true
img_rel: true
---

There’s a great talk by Dan North about “Patterns of Effective Teams”:

<iframe class="figure" width="600" height="338" src="https://www.youtube.com/embed/lvs7VEsQzKY?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>

The most interesting part for me was the [Dreyfus model of skill acquisition](http://www.dtic.mil/cgi-bin/GetTRDoc?AD=ADA084551&Location=U2&doc=GetTRDoc.pdf). It proposes that a person, while aquiring new skill, passes through five distinct stages:

1. _Novice_ student just learns the rules and tries to apply them without any situational awareness.
2. _Advanced beginner_ sees how rules are related to the more broader context, but still treats each aspect of work separately and with equal importance.
3. _Competent_ student sees the work as a means to achieve the goal and can move there deliberately. Aspects now appear to be more or less important depending upon their relevance to this goal. Relies on conscious planning and routinized procedures. This is where most people usually end up with.
4. _Proficient_ sees situations holistically rather than in terms of individual, separated aspects. Proficient student observes situations “as a whole”. She also has a sense of what’s “normal” and sees deviations from it.
5. _Expert_ student reacts to situations intuitively and falls back to analytican thinking only in rare cases when something completely novel occurs.

_N.B. those are skill-based, non people-based. You can be an expert in Haskell but novice in distributed systems, for example._

This model explains so much!

## Rules and guidelines

People at different stages of study needs different forms of advice:

-  _Novice_ needs strict rules: do exactly this, don’t do exactly that, proceed while you can. Anything more vague or nuanced would just confuse her.
- _Advanced beginner_ needs guidelines—more detailed rules that reference real-world examples and patterns.
- _Competent_ student needs procedures and plans to move towards the goal.
- _Proficient_ student operates on maxims: broader and situational set of principles that change their meaning depending on the current situation.
- Finally, _expert_ transcends any type of rules, guidelines and maxims and operates mostly from intuition.

You don’t just consume “more material” as you progress, you need fundamentally different form of guidance at each level.

What happens if you see material not suited for your level? Well. Short answer is: misunderstanding.

Guidelines—by definition—reference real-world situations and patterns, which novice students—again, by definition—haven’t experienced enough of yet. Thus they simple can’t make proper use of guidelines. You might’ve seen this happening with GoF patterns: novice programmers tend to apply as much patterns as possible, failing to see if situation calls for it or not.

Maxims rely on your ability to prioritize rules and guidelines in a context of “whole situations”. Beginners would misunderstand maxims as literal advice and use them out of proportion. You’ve probably seen how people push for [100% unit test coverage](http://labs.ig.com/code-coverage-100-percent-tragedy) or treat TDD/BDD/any other DD as religion.

All levels above novice would dismiss strict rules as unnecessary strict, maximalist or not entirely correct. You can’t get any real work done if you follow rules written for beginners—you’ll be breaking them all the time. But it doesn’t mean they are not useful for somebody who’s just starting.

So, I guess, no advice is for everybody. Be careful with what you follow, it might be not for you yet (or not anymore).

## Procrastination

Competent is the first level which can get work done. Advanced beginners can produce a lot of code but not move anywhere, not really. We’ve all been there—writing new abstarction layer, new UI widget system, new parser etc—anything that substitutes important part of the work with anything that is fun/easy/interesting.

This is an inability to _evaluate_ and _align_ guidelines in relation to goals. If you see all aspects of your work as equally important, it’s too easy to do the wrong thing.

## Asking for advice

You might’ve seen how people, being asked for advice, talk abstract trivia for some time and ultimately end up with “think for youself”. This is a failure of proficient person to give an advice without putting the whole situation into her head first. She can’t analyze situation from separate pieces.

## It’s hard to explain

If you ask an expert to explain a particular decision in her code, you’ll get an unstructured hours-long mix of battle stories, curious edge cases, half-formed ideas, excuses and awkward logical transitions. It’s literally her whole professional life behind each letter—hard to narrow that down to couple of all-around rules.

## Moving through stages

Say you want to get better. Good news is that it will happen more or less automatically if you just keep pushing:

- You move from _novice_ to _advanced beginner_ by accumulating real-life experience and seeing how individual rules and features are represented in the real world.
- To get to the _competent_ stage, you need to get sense of goals and their relation to guidelines, which is done by participating in projects.
- You become _proficient_ when you’re exposed to enough real-world “whole situations”. In IT it means you need to control a project (maybe a couple) and see all aspects of its evolution.
- You become an _expert_ by experiencing massive amount of situations to the point where you remember and internalized response to all of them. This is just a result of being there long enough and seeing a lot.

Bad news is that you can’t force this process to happen any faster. Neither can you skip stages. There’s only one path to the top of the mountain.

## Pairing

Some skill levels pair badly but some pair really well.

If you pair _novice_ with _an expert_ they will struggle to communicate. Novice needs clear rules and instructions to proceed, to start off and understand _at least something_. Expert, on the other hand, no longer thinks in rules. It’s not her choice, she just can’t even if she wanted to. Rules got supplanted by real-world situations and complex multidimentional analysis. There’s literally nothing simple about it that can be explained to novice (or anybody else) in a form of clear rules. If you’re an expert don’t get angry explaining something to beginners! It’s not their problem, it’s yours too.

_Advanced beginners_ are at the point where they test the guidelines to their limits to see if they need them at all. If you pair two advanced beginners they will get into the reinforcement loop of breaking rules and inventing everything from scratch.

If you pair _competent_ student with _novice_, it works really well. Novice needs rules and competent knows everything about them.

Same for _proficient_ students and _experts_ — experts has exactly the details proficient students are interested in.

So, if you need to teach, plan accordingly. Also: expert are not necessarily the best people to teach, at least not at all levels.

## Documentation

Great book (guide, documentation, blog post, podcast, any other piece of information) reconizes the distinction between stages and is focused at one specific stage at the time. It understands how reader at that stage thinks, what she needs and how to best deliver information to her. If you miss that it’s easy to write a document that isn’t for anybody.

## Programming languages

If you analyze programming languages using this model, you’ll see they were not created equal.

Languages define rules. Language with lots of rules pave a clear path for beginners.

Let’s see what Java offers: classes, interfaces, hierarchies, inheritance rules, visibility rules, inner classes, exceptions, static methods and more opinions how you should structure your code. You’re not yet sure _what_ should you write, and Java already offers you couple of sane and safe options.

Unfortunatelly, as you move forward, rules start to have priorities and exceptions. You don’t need an instruction manual anymore. You need a tool. As a proficient user, you know _what_ to write but you also need to fit it into the default language options. 

In contrast, Clojure has much less rules and safety measures. There’s just one, really basic way to organize code. There’s no visibility rules—everything is open by default. Everything can be overridden, including language syntax. There’re no static types, no “idiomatic ways” to write code, no patterns.

As a result, it’s easy to make a mess. _Really easy_. You can’t learn programming _through_ Clojure. Not alone. But Clojure really shines in the hands of an expert. If you know exactly what you want and how, nothing compares to it in terms of expressivity, precision and development speed. It’s razor sharp, but you need to know how to swing it.

As you learn more and more about programming, it’s natural to change your language of choice as you go.

## What an expert looks like

Vladimir Krichevsky

<iframe class="figure" width="600" height="338" src="https://www.youtube.com/embed/g-q3YHUnSe0?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>

He can’t explain his own decision, only describe them. He describes how the material “asked” him to be put this way. He contradict himself, of course, but that makes sense. If you decide to make rule out of it and remove contradiction, it’ll be worse.

Usually, when designing a book, you invent the rules and then play by them. Here, he literally invented everything in the process, intuitively. He invented the rules on the go (if you can call it rules, as they only exists for a single page each)

## Conclusion

It’s ok for different levels to coexist. There’ll always be novice students, beginners, competent and proficient professionals and experts. What’s not normal is that we don’t always recognize that. We don’t see that people at different stages need different lessions, materials, they think differently! We often fail to communicate because we don’t take that into account, and worst part of it is that we don’t even notice.

As we move through stages, we forget too easily how lost we once felt, how little we understood and how often we were wrong without realizing it. 

What to do:

- Read this post (check!)
- Learn to recognize stages and adapt to them. Try to imagine how _they_ feel and what _they_ need at this point.
- If you’re already in proficient/expert zone try to learn something new. It’ll help you remember how being a beginner feels. Pay attention to how you learn, what resources you use, which concepts you find useful, what help you move forward.



https://twitter.com/MicheleBertoli/status/889764689617530880
https://github.com/basecamp/handbook/blob/master/titles-for-programmers.md
https://medium.com/@cindysridharan/small-functions-considered-harmful-91035d316c29

https://en.wikipedia.org/wiki/Dreyfus_model_of_skill_acquisition
file:///Users/prokopov/Downloads/ADA084551.pdf
https://books.google.ru/books?id=xwyqLR-mG4cC&pg=PA162&dq=%27%27%27Dreyfus+Model+of+Skill+Acquisition%27%27%27&redir_esc=y#v=onepage&q='''Dreyfus%20Model%20of%20Skill%20Acquisition'''&f=false