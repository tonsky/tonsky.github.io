---
layout: post
title: "Adventures in REPL implementation"
category: blog
summary: "Writing Clojure REPL plugin for Sublime Text"
draft: true
---

It’s weird to announce, but I wrote Clojure plugin for Sublime Text. Again.

I mean, previous version worked fine, but it had a few flaws:

- REPL depended on syntax highlighting (yikes!)
- Everything was in a single file
- Hard to add REPLs

So, let’s do it again, almost from scratch, and right this time!

# What is REPL?

In a nutshell, REPL consists of two parts: client and server. Here’s an architectural diagram for you:

<figure><img src="./architecture.png"></figure>

# REPL Client

As you can see from the diagram, REPL clients live in a variety of environment, dictated by their host: Java for Idea, Python for Sublime Text, JS for VS Code etc. In my case, it was Sublime Text, so the environment happened to be Python 3.8.

Of course, writing client-server apps is not hard in any language. Unfortunately for us, REPL client needs to be able to read, speak and actually understand Clojure for a few features:

## Problem 1: Automatic namespace switching

When you go to a file and eval something there, you want it to be run in a context of that file’s namespace. But how to figure out which namespace it is, without parsing Clojure source file?

The level of understanding is non-trivial: there could be multiple namespace declarations, not necessarily at the top of the file:

<figure><img src="./ns_switching.png"></figure>

The declaration itself could be complex, too, containg comments and/or meta tokens before the actual name:

<figure><img src="./complex_ns.png"></figure>

## Problem 2: Form boundaries

I want shortcut that evals “topmost form” around my cursor. To do so, I need to know where those boundaries are. 

Notice how I don’t explicitly “select” what I want to evaluate, instead, REPL client finds form boundary for me:

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./form_boundaries.mp4" type="video/mp4"></video></figure>

This is tricky, too. For the very least, you can count parens, but even then you’d have to aware of strings. To make things harder, Clojure also has reader tags `#inst "2023-02-24"`, metadata `^bytes b` and different weird symbols like `@` or `#'` that are not wrapped in parens but are still considered a single form.

Bonus points for treating technically second-level forms inside `(comment)` as top-level.

All of this requires really deep understanding of Clojure syntax.

## Problem 3: Indentation and pretty-printing

Clojure Sublimed originally started when I wasn’t happy what happens when I press “Enter” in Clojure file. Cursor would go to the wrong place, and I (subjectively) was spending too much time correcting it, so I decided to fix that once and for all.

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./indent.mp4" type="video/mp4"></video></figure>

Indentation is not really a REPL concern, but it’s another part of Clojure Sublimed that requires model of Clojure code.

Indentation logic re-applied to the whole file is formatting, so I get that one basically for free (both follow [Better Clojure formatting](https://tonsky.me/blog/clojurefmt/) rules).

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./format.mp4" type="video/mp4"></video></figure>

Finally, there’s a question of pretty-printing, which is basically indentation + deciding where to put line breaks. Normally this could be done Clojure-side, but doing it on a client have clear advantages:

- you have to send less data when transferring evaluation results (no need to sends spaces at the beginning of the line)
- it works for every Clojure REPL the same
- it can adjust for your current editor configuration instead of some arbitrary server-side number like 80 characters

<figure>
    <img src="./pretty_print.png">
    Wrapping on current window width
</figure>

Another upside is that I can adjust pretty-printing rules to my liking, of course.

# Clojure parser in Python

So, client lives inside your code editor, and needs to understand Clojure _before_ it starts communicating with it. Meaning, without Clojure runtime. Meaning, we had to parse Clojure in Python!

Things get harder because support for libraries, especially native ones, is not great in Sublime Text. Meaning, pure python implementation!

I was put off by that task for a long time because it felt enormous. In the first Clojure Sublimed version, I deduced this information from syntax highlighting (Sublime Text already parses your source code for highlighting, and you can kind of access results of that). But this time I wanted to make things right.

And, in fact, it turned out not to be all that bad! Clojure, as any Lisp, _is_ relatively easy to parse. This is the whole grammar, more or less:

<figure><img src="./grammar.png"></figure>

Full source at [GitHub](https://github.com/tonsky/Clojure-Sublimed/blob/master/cs_parser.py).

Some notable details below.

## Parser: Cutting Corners

I cut some corners to write less code and get max performance by e.g. not distinguishing between numbers, keywords, symbols — they are all just tokens. It’s probably not hard to add them, but I don’t really need them for what I do, so they are not there.

## Parser: Performance

I haven’t spent any serious time trying to optimize the parser. In the current state, it can go through clojure.core (enormous 8000 loc file) in 170ms on my M1 Mac.

Clojure itself does the same in roughly 30-50 ms, though, so there’s definitely potential for improvement.

## Parser: Incrementality

Should be possible one day :) So far performance is good enough to re-parse on each “enter” keypress.

## Parser: Testing

Parse trees could be quite hard to navigate, and twice as hard to compare.

Parsing also requires _a lot_ of tests to get everything right and to avoid regressions. So having a nice and ergonomic way to write and inspect tests was super important.

I ended up copying test syntax from tree-sitter.

<figure><img src="./test.png"></figure>

Test runner just compares actual result with expected one string-wise and if they are different, repots an error in a nice to comprehend table:

<figure><img src="./failed_test.png"></figure>

Having this early on saved me a ton of time and I am 100% happy I made that investement.

## Parser: Error recovery

Parsing valid Clojure code is quite easy. But code in the process of edit is not always correct, even syntactically. That means that our parser has to work around errors somehow!

This surprised me, though:

> In the yacc and bison parser generators, the parser has an ad hoc mechanism to abandon the current statement, discard some parsed phrases and lookahead tokens surrounding the error, and resynchronize the parse at some reliable statement-level delimiter like semicolons or braces.

For our purposes, though, it was quite simple: see something that you don’t understand? That must be an error. Most stuff gets consumed as a symbol or a number, though, so these were rare.

We did accept some invalid programs as valid, but that’s okay for our use-case: find expression boundaries, throw it over the fence and let Clojure work out rest of the details.

## Parser: Accidentally quadratic

One funny thing happened during testing: I noticed that sometimes parser was becoming ultra-slow on moderately-sized files. That means I had quadratic behavior somewhere.

And that was indeed the case. First version of the parser, roughly, was parsing parens/brackets/braces like this:

```
Seq(Char("["),
    Repeat(Choice('_gap', '_form')),
    Char("]"))
```

Which basically means: if you see opening bracket, consume forms and whitespace inside as long as you can, and in the end there must be a closing bracket.

Well, what if there’s none? That means it wasn’t a `'brackets'` form in the first place! Which is technically correct, but also means that we have to mark opening bracket as error and then _re-parse everything inside again_. That’s your quadratic behavior right here!

A simple change got rid of this problem:

```
Seq(Char("["),
    Repeat(Choice('_gap', '_form')),
    Optional(Char("]")))
```

Technically, this accepts incorrect programs. In practice, though, it works exactly as we need for indentation: we only care about opening parens up to the point of cursor and don’t really care what happens after it.

## Parser: Conclusion

Writing parser was very fun! So many little details to figure out and get right, but in the end, when everything clicks into its place, it’s so satisfying!

I also now understand why Lisps were so popular back in the day: they are really made for ease of implementation. Hacking together the whole parser from scratch in a weekend — can you imagine it for something like C++ or Python?

Anyways, if you need Clojure parser in Python, take a peek at [my implementation](https://github.com/tonsky/Clojure-Sublimed/blob/master/cs_parser.py) — maybe it’ll help you out!

# Protocol

# Server

## Socket Server

# You were going to ask anyway

Curious what color scheme do I use? Check out my new repo, [tonsky/sublime-color-schemes](https://github.com/tonsky/sublime-color-schemes/).

I know, it looks like pain for the eyes. I made it as a joke, but suddenly started to like it a lot and have been using it for the past two months.

The font is [Berkeley Mono](https://berkeleygraphics.com/typefaces/berkeley-mono/).