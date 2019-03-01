---
layout: post
title: "Better Clojure formatting"
category: blog
summary: "A Clojure formatting style good enough to be a default standard"
hackernews_id: 18620309
img_rel: true
---

<style>
blockquote > pre { margin: 15px 0; padding: 0; background: none; }
</style>

There has been [a discussion](https://clojureverse.org/t/clj-commons-building-a-formatter-like-gofmt-for-clojure/3240) recently whether Clojure should have its own version of gofmt, a default go code formatter. I think it’s a good opportunity to collect and formulate my thoughts on the subject.

If you have no idea why default formatter is such a good idea, [this fantastic slidedeck](https://talks.golang.org/2015/gofmt-en.slide#1) might shed a light.

## Clojure Style Guide won’t do

As I understand, current [community standard](https://github.com/bbatsov/clojure-style-guide) takes its roots in prior LISP conventions and Emacs formatter. Although many people use it, following it to the letter might not be the goal, mainly because it seems to be in conflict with other constraints (we’ll get to them in a bit).

“But! Most of Clojure code is _already_ written with it!”

Yes. And another reason why not following the current community standard might be ok is that when hypothetical `clojurefmt` arrives, nobody’s code will probably match it to the letter. So everybody will have to reformat, and if everybody is reformatting it’s doesn’t really matter how much. You either join `clojurefmt` and hand all your code to the machine or you don’t.

## One formatter to rule them all

Ok. Now to constraints and why none of the existing conventions nor tools solve the problem.

Design constraints for `clojurefmt` are kind of unique. Once a tool like this is set up, there’s no changing it. It’s set in stone. That’s by definition: we want one and only clojure formatter that everyone agrees on. Two versions are the same as having two formatters. That means chaos. Can’t have that.

So formatter can’t be extensible, can’t have patches and releases. Which means it has to be designed with future changes in mind. That includes clojure.core and language changes that didn’t happen yet!

This is why config/hard-coded rules/exception-based solutions don’t work here. When Clojure 1.6 added `when-some` and `if-some`, no formatters supported it and as a result, `when-let` and `when-some` was formatting differently. This is because `when-let` was is config but `when-some` wasn’t. This also means library macros never would be formatted correctly.

The important thing to understand here is that such changes DID happen in the past and WILL happen in the future. So we can’t rely on “let’s enumerate all the exceptions and live forever with them”.

## Invade everything!

`Clojurefmt` has to be everywhere. Any editor. Any language. Stand-alone tool. Browser. Libraries. If we want everyone to use it, we should give it to everyone. Nothing sucks more than “it works in Emacs but everybody else needs to figure out a way to run it”. Or “it’s written in Clojure so it takes 10 seconds to start up, practically unusable”.

This constraint requires a certain simplicity of the standard. Fewer rules, low language specificity, simpler conditions. So it could be reimplemented everywhere.

For example, I’d be happy if I could express the rules of `clojurefmt` in VS Code Auto Indent syntax, so that editor would support them natively and place my cursor where it’s supposed to be. This is much better UX than formatting afterward. Ideally, onsave/beforecommit formatting should be for correcting mistakes. Unless you fight it, an editor should produce correctly formatted code while you write it.

Simplicity also means there’s less chance for different implementations to diverge.

## What’s wrong with the status quo?

Clojure Style Guide has multiple rules that don’t play nice with those constraints.

<blockquote>
Use 2 spaces to indent the bodies of forms that have body parameters.
<pre>
(when something
  (something-else))
</pre>
</blockquote>

How would we know if the form has body params or not? Whitelisting doesn’t work. Parsing codebase to find macro source doesn’t either.

<blockquote>
Vertically align function (macro) arguments spanning multiple lines.
<pre>
(filter even?
        (range 1 10))
</pre>
</blockquote>

This rule would be easy to automate if it didn’t have a huge list of exceptions. `let, cond, if, defn` and many other forms simply don’t follow this rule.

<blockquote>
Use a single space indentation for function (macro) arguments when there are no arguments on the same line as the function name.
<pre>
(filter
 even?
 (range 1 10))
</pre>
</blockquote>

Again, exceptions! E.g. `try` doesn’t follow it. Also, having a mix of one- and two-space indents was driving me mad when I was trying to follow those rules manually.

> Where feasible, avoid making lines longer than 80 characters.

This is just a silly cargo-cult rule that people like to bring up for no reason. Punched cards used to have 80 columns and we follow this limit ever since. 80-column text on a 1920-px display is as stupid as 135-column text on a narrow mobile phone screen.

Bikeshedding, but here’s an ultimate argument: any editor can fit long text for a screen of any size. No editor can fit 80-column text on display that has three times as much space though.

> Avoid trailing whitespace.

This is one of the stupidest things to automate. Unlike indenting, removing trailing whitespace simply produces diffs *where it doesn’t matter*. And does nothing more.

## Ok, what do we do then?

I propose two simple unconditioned formatting rules:

- Multi-line lists that start with a symbol are always indented with two spaces,
- Other multi-line lists, vectors, maps and sets are aligned with the first element (1 or 2 spaces).

*Note: rules was updated to address an issue with Parinfer and multi-arity fns indentation, as many people has pointed out.*

Basically, these examples will “simply work”, with no form-specific rules or any exceptions:

```
(when something
  body)

(defn f [x]
  body)
  
(defn f
  [x]
  body)
  
(defn many-args [a b c
                 d e f]
  body)
  
(defn multi-arity
  ([x]
   body)
  ([x y]
   body))

(let [x 1
      y 2]
  body)
  
[1 2 3
 4 5 6]

{:key-1 v1
 :key-2 v2}
 
#{a b c
  d e f}
```

They will also work with any user-defined macros, any libraries and any possible extensions to clojure.core.

These will produce results different from what you’re used to (not a bad thing!):

```
; second cond is not aligned
(or (condition-a)
  (condition-b))
  
; or/and are the only forms where this looks not ideal
; other forms don’t win/lose much because of this change
(filter even?
  (range 1 10))
  
; my way is actually better if fn name is looooooooooong
(clojure.core/filter even?
  (range 1 10))

; 1 additional space, no big deal
(filter
  even?
  (range 1 10))
```

It might be not what you’re used to, but it still looks decent, doesn’t it?

In fact, differences are so marginal you might not even notice them unless you’ve spent a couple of days working on code formatting already.

What’s important is that those rules are much simpler to establish, implement and follow. They even fit in your head!

## Road to adoption

As Robert Griesemer put it in [his slides](https://talks.golang.org/2015/gofmt-en.slide#33), “gofmt’s style is nobody’s favorite, yet gofmt is everybody’s favorite”.

The point of such formatter is not to produce a style that everybody would love. This is probably impossible. The point is to enforce a style that will free people from arguing and making style decisions *at all*. To accomplish it, it doesn’t need to be smart, or pretty, or sophisticated. It has to be ubiquitous. It has to be everywhere.

Let Clojure community know if you think this formatting is better fit for one-and-only-clojure-default-formatter-that-everyone-has-to-use-no-knobs-no-settings-no-questions-asked. Vote [on Github](https://github.com/clj-commons/formatter/issues/9#issuecomment-445528667), comment on [Hacker News](https://news.ycombinator.com/item?id=18620309) or [on Twitter](https://twitter.com/nikitonsky/status/1070654163288276993).