---
layout: post
title: "Ideas for Clojure Network Eval API"
category: blog
summary: "How nREPL could be improved"
---

Jack Rusher [is asking](https://twitter.com/jackrusher/status/1525092640286560258) for nREPL feedback from Clojure tooling authors:

> After implementing an nREPL server for a new Clojure runtime, I'm begging the other tools maintainers to work with me to define an actual standard that works.🥹

Since I am also a [tool maintainer](https://tonsky.me/blog/sublime-clojure/) AND my tool works with nREPL, I thought I share my ideas here.

# What is REPL?

Rich Hickey [famously said](https://groups.google.com/g/clojure-dev/c/Dl3Stw5iRVA/m/IHoVWiJz5UIJ) that nREPL is not a REPL:

> REPL stands for something - Read, Eval, Print, Loop. It does not stand for - Eval RPC Server/Window. It is not something I "feel" about. But I do think we should be precise about what we call REPLs.  
[...]  
'read' is a protocol. It works on character streams, one of the most widely available transports. It uses delimiters (spaces, \[], (), {} etc) to separate forms/messages. Readers implement this protocol and return data upon encountering complete forms.

I am happy to accept these definitions and have no desire to force REPL to mean what it’s not. But then, in my opinion, Clojure does not need a REPL, it needs an Eval RPC protocol. To turn Rich’s methods against him, current REPL _complects_ command-line interactions with code evaluation.

It’s trivial to build human-friendly CLI on top of machine-friendly RPC, but much harder to build machine-friendly RPC on top of a human-oriented command line.

Sure, `ls -lah` output looks nice in my terminal, but imagine parsing this for machine consumption?

<figure>
  <img src="./ls.png">
</figure>

So I insist that Clojure needs a “Remote Eval API” — message-oriented machine-friendly network API that’s easy to build on.

One feature that is crucial for me that nREPL gets right and all of the Clojure REPLs get wrong is evaling incomplete forms. If I send `(+ 1 2`, I don’t want to see my REPL stuck in the intermediate state. I want to see an error.

# JSON serialization

nREPL uses bencode by default, which was a brilliant decision. Bencode is super-simple and trivial to implement, meaning nREPL clients could exist in any environment: Python for Sublime, Java for IDEA, Elisp for Emacs, JS for VS Code, VimScript for Vim.

This day I would go with JSON, though. I know nREPL has JSON backend, but I mean by default.

Using EDN would be a terrible mistake, though. EDN is much less popular, and REPL clients exist in all sorts of environments, rarely in Clojure.

# Automatic session management

nREPL has a concept of sessions. I think this exists mainly for capturing dynamic bindings like `*warn-on-reflection*` or `*print-namespace-maps*`.

What troubles me is that sessions require manual management: you clone them manually and have to close them manually. If you forgot to close them, they will exist indefinitely, leaking resources.

I would replace this with automatic management, a session per connection. Connection dropped? Kill the session.

# Session-scoped middlewares

Another thing sessions could be useful for are middlewares. Currently, they are installed globally, but that seems like an oversight? If I need middlewares X, Y, and Z for my editor to work, why should anyone else see them too?

In true network API, what I do should not affect what user in a parallel session is doing.

This all applies, of course, only if we allow middlewares to exist.

# No middlewares

If I understand Jack right, his main concern is that middlewares are not portable. If you wrote an nREPL client that installs middlewares for Clojure, it won’t work with ClojureScript.

I faced the same problem, and that’s the main reason why Clojure Sublimed doesn’t talk to CLJS yet.

Sure, middlewares are a nice escape hatch. But maybe the goal should be to get the basic protocol enough so that nobody will have to write middlewares?

# No “upgrade”

In my head, Network EVAL API should work like any other API: you connect to the server you want, send predefined commands and get results back. Want to eval Clojure? Connect to Clojure server. ClojureScript? Connect to CLJS.

Instead, the route Clojure REPLs choose is “REPL upgrade”. You connect to Clojure server always, then eval some magic forms and commands start to behave differently, e.g. being compiled to CLJS and sent to the browser.

I think even nREPL does that because Piggieback is a middleware installed globally that shares server with Clojure instead of starting its own.

This makes API non-uniform, complects CLJS (or any other) REPL with Clojure REPL, and just feels weird to use. Evaluating Clojure code should not assume JVM Clojure environment!

# Parallel eval

For some reason, nREPL allows a single pending eval per session. This seems like an arbitrary limitation. I think evaluating one form should not prevent you from evaluating another, given that threads are easy to create in JVM.

CLJS might be in a different situation, but that shouldn’t mean Clojure users should suffer.

# Single connection

Not sure if worth mentioning since nREPL got this right, but some REPLs suggest you open second connection to control the session in the first one.

This is a terrible idea. Multiple connections (even two) are order of magnitude harder to manage than a single one.

Don’t block the line, allow parallel eval and you won’t need a second connection.

# Stacktraces

I don’t think nREPL sends stack trace if an exception happened? That is a crucial information, seems strange to omit it.

P.S. Stacktraces as data would be nice.

P.P.S. Stacktraces that don’t show nREPL internal code would be double nice.

P.P.P.S. Unmunged Clojure names would be triple nice.

P.P.P.P.S. Correct error position would be quadruple nice, but this is on Clojure more than on REPL.

# Execution time

Stupidly simple, but quite an obvious feature: how long did that form take to eval?

Can’t measure that on the client due to network overhead.

# Extract info from Clojure file

Not 100% sure this belongs in the Network API server, but then on the other hand repeating this in every client feels excessive too?

Imagine you go to a file and eval the last form here:

```
(ns my-ns
  (:require [clojure.string :as str]))

(defn reverse [s]
  (str/reverse s))

(reverse "abc")
```

Which namespace should it be evaled in? `my-ns`, the namespace of the file, right?

But the network server doesn’t know that! All it sees is something like:

```
{"op":   "eval",
 "code": "(reverse \"abc\")"}
```

This won’t work because `reverse` is not defined. You can specify namespace though:

```
{"op":   "eval",
 "code": "(reverse \"abc\")",
 "ns":   "my-ns"}
```

This improves the experience a bit, but how do you get this `my-ns` string? Well, to do that, you have to parse Clojure file, find the closest `ns` form and take the first symbol. Which in turn could be preceded by arbitrary-complex metadata form, like this:

```
(ns my-ns
  (:require [clojure.string :as str]))

(defn reverse [s]
  (str/reverse s))

(ns ^{:doc "Hello"} another-ns
  (:require [my-ns :refer [reverse]]))

(reverse "abc")
```

Not exactly the simplest task, is it? Well, it’s only hard if you are in Python, or JS, or VimScript.

But if you are in Clojure, it’s trivial! Clojure already ships with Clojure parser, so figuring out namespace is a matter of a few calls into stdlib.

Another common problem arises when you want to eval “the outermost form”. Like, you stand somewhere inside a function and ask the network REPL to eval that function. Finding start and end of the form to send is again, a hard task unless you have access to Clojure.

Finally, requesting info on symbol (lookup) requires you to identify where that symbol starts/ends. And yes, would be way easier in Clojure.

I’m not sure what the solution here is. Send a file to the network server and ask for parse information back when needed? Send the entire file on each eval?

I guess the reason nREPL has no solution here is because all possible solutions here are cumbersome? Bad? But it doesn’t mean the problem doesn’t exist.

# Auto-require

On the other hand, if I try to eval `(+ 1 2)` in `my-ns` file before loading `my-ns` first, I’ll get an error: namespace is not loaded.

Clients could work around that, sure, but it still feels like an unnecessary dance. If ns could be ignored (e.g. evaluated code does not depend on it at all), eval it as is, otherwise load namespace first? Or always load namespace when client provides it? Or have an option?

# Capturing \*out*

It became a norm to display part of the stdout output in the REPL panel:

<figure>
  <img src="./calva.png">
</figure>

But if you don’t have REPL panel, where did that output go?

<figure>
  <img src="./sublime.png">
</figure>

In that model, it goes to the same place where any other output goes: to the console that started the server.

In other words, I would like an option to _disable_ output capturing and just let Clojure do what it would do by default.

# What to keep

Good parts of nREPL that I definitely would want to keep:

- interrupt
- print/buffer-size
- lookup

I don’t have an opinion on code analysis/suggestions/etc since I don’t use those (well, I do use suggestions from Sublime but those don’t require Clojure server).

# In conclusion

During my development of Clojure Sublimed, I was deciding between using nREPL or writing my own server. I decided on nREPL due to it being a standard but ended up modifying a significant part of it and losing CLJS support in the process.

Because of that, I would love to see a Clojure Network Eval API that could be used to build editor integrations with minimal modifications and interoperable with all existing Clojure servers: JVM, CLJS, Babashka, nbbjs etc.

My thoughts here are provided just as a starting point/idea dump, I would be happy to move the discussion/join it elsewhere!