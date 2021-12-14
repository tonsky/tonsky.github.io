---
layout: post
title: "Sublime ❤︎ Clojure"
category: blog
summary: "New Sublime Text plugin for working with Clojure"
hackernews_id: 29549392
---

REPL is Clojure’s superpower. For a long time, I’ve been enjoying Sublime Text but was unable to match it with Clojure. This ends today: I’m happy to present my new Sublime Text plugin, [Sublime Clojure](https://github.com/tonsky/sublime-clojure).

## Road there

The work started in 2018 when I got too annoyed with built-in Sublime Clojure grammar. It was there and highlighted _some_ things while reporting errors or misunderstanding others perfectly valid things:

<figure>
  <img src="./grammar.png">
</figure>

On top of that, Sublime just introduced nested contexts in their grammars, so I was able to express the true tree-like nature of S-expressions. This allowed me to highlight unbalanced brackets and even build rainbow parens directly in the color scheme.

<figure>
  <img src="./nested_contexts.png" width="418" heigh="206">
</figure>

I didn’t know that yet, but this fact will later allow me to utilize highlighting info to efficiently detect form boundaries. There’s a lesson there, I think: embrace the true nature in the API and it will pay off sooner or later.

I never published this grammar as a separate package but have been using it since 2018 and it has been rock solid. It now comes as a part of the Sublime Clojure plugin, so feel free to install it. Even if you don’t need a REPL, it’s very useful.

To be honest, in the last three years the Clojure grammar that is built-in in Sublime Text was improved, too, from what I can see.

## Why not Sublime REPL?

Sublime REPL is a universal REPL that tries to cover as many languages as possible. What it does, basically, is that it opens a tab with a terminal for you. It never has been or will be Clojure-specific. In fact, the last time I tried, I wasn’t even able to make it connect.

## Why not Tutkain?

[Tutkain](https://tutkain.flowthing.me/) is a great piece of software with which I, unfortunately, could not make peace with.

First, it packs too much in one package (in my opinion): it does paredit, it ships with its own syntax (yes, I am guilty of that too), it indents code, it runs tests. It is great if you need all of it, but what if I don’t need paredit, for example? It’s a shame I can’t use the rest because of this.

Second, and more importantly, it follows this idiom of “dedicate half of your screen to REPL” which I never understood:

<figure>
  <a href="./tutkain.png"><img src="./tutkain.png"></a>
</figure>

Forms on the right more or less repeat forms on the left, namespace indicator is repeated a million times, matching what was evaluated on the left and what appeared on the right is non-trivial, and many results are probably not necessary anymore, yet they take up precious space.

Imagine, like, your bash history always present and taking up so much space? Or a browser that only uses half of the screen. Crazy? Yet this is more or less the default in Clojure tools, as far as I understand.

So I was left with the only option: to try and build my own REPL client.

## REPL, reimagined
### Inline evaluation

The first and most important feature for me is inline evaluation. Display results directly in the file they come from, _in the context of the code_.

<figure>
    <img src="./inline.png">
</figure>

Need to compare multiple results? They could all be visible on the screen at once. Changed code? Result disappears. Need to look something up in a different file? The result will stick with the code. 

Later I found that Tutkain can do this, too. Only their results disappear once you move your cursor, which is not exactly what I want.

### No “REPL” panel

The second feature that was important to me: get rid of the extra “REPL” panel for good. Why do you need it if you can see your results inline?

<figure>
    <img src="./no_panel.png">
</figure>

Well, stdout. Printing is unbounded and unpredictable, so most of the time you don’t really want to see it inline.

The way most REPLs deal with it is simple: they already have an extra panel, so they capture the output that was produced during your evaluation and print it on the panel.

But this also means there are now two places to check, depending on how your `println` was executed. Directly in the function you just called? Look in the REPL panel. In another thread (e.g. in a web server or inside a `core.async` loop)? Check your primary stdout.

And it drove me crazy before: I was banging my head way too often trying to understand why my `println` prints nothing, only to realize I was looking in the wrong place.

So Sublime Clojure just gets rid of this complexity. Any stdout, either from evaluation or from any other thread goes straight to `System.out`. If you want something else, just wrap your code with `(with-out-str ...) ` or `(binding [*out* ...] ...`).

As an added bonus, there’s no extra panel to manage. There might be one if you choose to start your Clojure program directly from Sublime, but it’s completely up to you.

### Parallel evaluation

The idea of REPL in the command line is that it’s an interactive protocol. You ask something from your program, the program responds back. 1 request, 1 response. It would be too hard to manage otherwise from the CLI.

But in the editor, we are not really constrained by the amount of code we can operate on. So why not operate everything at the same time? Why not make a blocking call that starts a web server and go do something else?

Honestly, I don’t see why not.

<figure>
  <video style="border-radius: 11px" autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./parallel.mp4" type="video/mp4"></video>
</figure>

### Exceptions

Clojure exceptions are... hard to make sense of.

<figure>
    <img src="./clojure_trace.png">
</figure>

What is `:via`? Why does it repeat `:message` and `:data`? Why is message called `:cause` in one place but `:message` in the other? Why Clojure function names have so many $$$ in them?

Compare:

<figure>
    <img src="./trace.png">
</figure>

Sublime Clojure makes an effort to clean it up, present Clojure functions with their original names, and remove the parts that happen inside middleware, nREPL, Clojure runtime, or in a Clojure compiler. Most of the time you want to debug your own app, not the REPL or clojure.core.

It’s been two weeks since I started using Sublime Clojure REPL myself. And you know what? I started to really enjoy Clojure exceptions! Short, concise, pointing directly to the error, formatted in Clojure naming convention, not in Java munged style which you need to decypher back to Clojure. It’s a fantastic experience, even if on paper it doesn’t sound as much.

### Little conveniences

Sublime Clojure will display the time it took to run your command if it took more than 100 ms (configurable).

<figure>
    <img src="./elapsed.png">
</figure>

Let’s just say it’s my way to motivate developers to write faster software. Being aware of the problem is the first step.

### Animations

During long evaluation, there’s an animation playing. Seems minor and stupidly simple to implement, but so much fun looking at it!

<figure>
  <video style="border-radius: 11px" autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./progress_clock.mp4" type="video/mp4"></video>
</figure>

The best part? It’s trivially customizable. For example, by setting

```
"progress_phases": ["⠏", "⠛", "⠹", "⢸", "⣰", "⣤", "⣆", "⡇"]
```

I get this:

<figure>
  <video style="border-radius: 11px" autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./progress_braile.mp4" type="video/mp4"></video>
</figure>

By applying a little more imagination, it’s not hard getting this:

<figure>
  <video style="border-radius: 11px" autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./progress_wave.mp4" type="video/mp4"></video>
</figure>

Finally, utilizing Fira Code 6 progress bar glyphs, you can get this:

<figure>
  <video style="border-radius: 11px" autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./progress_bounce.mp4" type="video/mp4"></video>
</figure>

Your imagination is the limit! Send me crazy progress bars you come up with!

### Overloaded shortcuts

Human memory is limited, so some commands in Sublime Clojure do different things depending on the context.

`Ctrl` + `Enter` evals topmost form if there’s no selection, but current selection if there is one.

<figure>
    <img src="./ctrl_enter.png">
</figure>

`Ctrl` + `I` expands stacktrace if you stand on an error, but shows symbol info if standing in plain text:

<figure>
    <img src="./ctrl_i.png">
</figure>

In the future, I plan to overload it further to show pretty-printed values of evaluation.

The idea is that you have one single shortcut to remember and muscle memory will be built much faster.

### Binding keys to eval code

Every project is different, and sometimes it’s convenient to run a piece of code so often you’d want it on a shortcut. It might be namespace reload, test execution, linter, database reconnect — possibilities are endless.

Trick is, it’s hard for plugin developer to know what you might need in advance. That’s why best way to address this need is to give users the ultimate tool: ability to run code.

To support such use cases, Sublime Clojure allows you to bind arbitrary piece of code to a keyboard shortcut. E.g. by adding something like this:

```
{"keys": ["ctrl+t"],
 "command": "sublime_clojure_eval_code",
 "args": {"code": "(clojure.test/run-all-tests)"}}
```

and then pressing `Ctrl` + `T` I get myself a little ad-hoc test runner without any need to address this in the plugin itself!

<figure>
    <img src="./eval_code.png" alt="Eval Code">
</figure>

The result is conveniently displayed in the status bar.

### nREPL

Sublime Clojure chooses to communicate with nREPL instead of Socket REPL/pREPL/unREPL. Why?

Because (surprisingly) all other REPLs are built for interactive use. Using web analogy: they are all human-readable HTML pages when nREPL is the JSON API: it’s machine-oriented. It would be strange to build tools on top of human-readable web page, right?

Simple test. Send this to evaluate:

```
(+ 1 2
```

All REPLs with the exception of nREPL will get stuck: they will stop accepting any input and will wait for you to close the missing paren. This is ok when you work from the console but completely unacceptable when trying to integrate with another program.

Exceptions are part of the process of developing software. They are called exceptions, but that doesn’t mean they are exceptional or rare. Exceptions should be _expected_, especially during development, and dealt with grace. They certainly shouldn’t punish you by getting the system in a weird stuck state. Simple “Sorry, Dave, I don’t understand you” is a million times better than “I will not tell you what my problem is, you should’ve guessed”.

<figure>
    <img src="./malformed_input.png">
</figure>

There’re other benefits to nREPL, too. First, it powers CIDER, which Emacs folks use, and Emacs still dominates Clojure ecosystem. So strategically it’s the right place to be — if there’re problems, they will be addressed by a huge community way faster than by me alone.

It also (potentially) gives you access to ClojureScript, at least I hope so (I have not tried yet).

And it comes with batteries: e.g. interrupt, background evaluation, a safeguard against infinite sequences, and extension points.

Bencode is also a much simpler beast to deal with from non-Clojure environments than EDN is. Ideally, it would’ve been JSON, but we are not living in a perfect world.

In other words, I don’t really see an alternative. And I’m also surprised how many things nREPL got right and everyone else got wrong since.

### How to get

The initial version is out now on GitHub (Package Control [will come later](https://github.com/wbond/package_control_channel/pull/8394)). All the instructions can be found in the repo:

<figure>
    <a href="https://github.com/tonsky/sublime-clojure"><img src="./banner.png"></a>
</figure>

Feel free to try it out and leave feedback [in the issues](https://github.com/tonsky/sublime-clojure/issues).

For future plans, check out the issues in the repo, too. There’s a few already, perfect if you want to help. Come help me improve this!

## Conclusion

It’s magical how good the ability to evaluate code directly in editor feels. The very first day when I got basic evaluation working I felt the power of it on my fingertips immediately. It’s a superpower.

Now it’s much more polished and ready for the world. The future of Clojure in Sublime looks very bright right now.