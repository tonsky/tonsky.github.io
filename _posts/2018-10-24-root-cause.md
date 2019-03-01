---
layout: post
title: "Address the root cause"
category: blog
summary: "Do not just fix symptoms. Find out the root cause and address it instead"
img_rel: true
---

Today I was listening for [Apropos Clojure #20](https://www.youtube.com/watch?v=6ftW8UwwP_4) and looks like it was me who [triggered Stu](https://twitter.com/nikitonsky/statuses/1014596144347926529) to start looking into the problem. What surprised me was what he said next.

Many people complain that Clojure stacktraces are ugly and look like you broke your computer. They are not incorrect:

```
#error {
 :cause "Divide by zero"
 :via
 [{:type java.lang.ArithmeticException
   :message "Divide by zero"
   :at [clojure.lang.Numbers divide "Numbers.java" 188]}]
 :trace
 [[clojure.lang.Numbers divide "Numbers.java" 188]
  [clojure.lang.Numbers divide "Numbers.java" 3901]
  [user$eval1 invokeStatic nil 1]
  [user$eval1 invoke nil 1]
  [clojure.lang.Compiler eval "Compiler.java" 7172]
  [clojure.lang.Compiler eval "Compiler.java" 7135]
  [clojure.core$eval invokeStatic "core.clj" 3206]
  [clojure.core$eval invoke "core.clj" 3202]
  [clojure.main$repl$read_eval_print__8898$fn__8901 invoke "main.clj" 309]
  [clojure.main$repl$read_eval_print__8898 invoke "main.clj" 307]
  [clojure.main$repl$fn__8907 invoke "main.clj" 332]
  [clojure.main$repl invokeStatic "main.clj" 332]
  [clojure.main$repl_opt invokeStatic "main.clj" 396]
  [clojure.main$main invokeStatic "main.clj" 495]
  [clojure.main$main doInvoke "main.clj" 458]
  [clojure.lang.RestFn invoke "RestFn.java" 397]
  [clojure.lang.AFn applyToHelper "AFn.java" 152]
  [clojure.lang.RestFn applyTo "RestFn.java" 132]
  [clojure.lang.Var applyTo "Var.java" 705]
  [clojure.main main "main.java" 37]]}
```

Compare to Python stacktrace:

```
>>> 1 / 0
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
ZeroDivisionError: integer division or modulo by zero
```

So Clojure exceptions are ugly and something should be done about that. BUT! The solution IS NOT to just hide them. Exceptions are useful, and hiding them does not address the root problem: they are hard to use and contain lots of unnecessary info.

Look at Clojure stacktrace again. Everything starting from stack element 5 is unrelated to my program *at all*. `user$eval1` is what I’m interested in but it is cryptic and appears twice and has no clues about the place in the REPL where that error came from.

Compacted version (Clojure behaviour by default from 1.9) is no better:

```
user=> (/ 1 0)
Evaluation error (ArithmeticException) at clojure.lang.Numbers.divide (Numbers.java:188).
Divide by zero
```

The message is correct but file/line number lead back into Clojure internals. When I see that *I have to* expand stacktrace anyways and fight the complexity of it on my own. So everything this solution does is merely postponing the moment I frighten myself with the horror of the stack.

Another solution that was mentioned is [Clojure Error Message Catalog](https://github.com/yogthos/clojure-error-message-catalog). This should not be treated as a helpful resource (although it is, thx @yogthos for collecting those!) but as a bug list to fix in Clojure core. If something blows up in a confusing way, the solution is not to educate everyone about that specific “language peculiarity”. The solution is to go back to Clojure core and put an appropriate check in an appropriate place so that next time it blows up the message is clear and precise.

The same faulty approach was mentioned in [The REPL #7](https://www.therepl.net/episodes/7/) with Ben Brinckerhoff regarding Clojure.spec validation. Ben said that Spec simply does not provide enough data to transform Spec errors into nice-looking useful messages. Again, Clojure.core position on this has always been: we’ve made the Spec and other people should figure out how to live with it.

But here the one of the core components is missing, so it *is not* a viable solution. Instead of asking other people to invent ingenious ways to overcome this limitation, the solution should be addressed in Spec itself. Everything else would be a compromise and will only lead to a complicated, fragile ecosystem in the long run.

Yes, sometimes Clojure core can’t be changed enough to address the core issues because of the backward compatibility. Sometimes it might be harder than waiting for people to invent their workarounds, and Clojure.core resources are limited. But at least let’s talk about that openly? Let’s clearly call compromise a compromise when we make one?

And when something can be fixed—why not do it? Why not address the root reason instead of fixing the symptoms? Maybe it’s time we stop treating core as some sacred texts and start cleaning it up? At least could we start acknowledging some problems should be addressed there instead of at ecosystem level?

My greates sympathies to Clojure.core team and everything they’ve done so far. I’m using software you made every day and couldn’t be happier about it. I’m only writing this so we can see problems clearly and talk about them more openly.

UPD: A separate “dev” mode was also suggested in a podcast and [on Twitter](https://twitter.com/puredanger/status/1055109097362731010). This comes from a premise that novice developers and “pros” need different things. WRONG. We do not need different things. We both want the same thing: good error messages reporting the correct cause and a relevant stacktrace frames. We both DO NOT want cryptic messages reported at the wrong place.

Do you imagine it like novice gets “You need to pass a set to core.set/union” and line number where he passed the wrong thing but “pro” gets a “Count not supported on this type: Long” and three times longer stacktrace pointing at the wrong place? No. Any pro will prefer “novice” option any time of the day. Professional are as annoyed with this as any novice would be.

Just do the right thing and _everyone_ will be happy.