---
layout: post
title: "Solve the problem at hand"
category: blog
summary: "Always prefer concrete code to abstract one. Don’t try to solve problems you don’t have."
img_rel: true
---

Imagine being at the interview where the interviewer asks you to convert a binary string into a number.

“Please write such `f()` so that e.g. `f("101") === 5`.”

I would start writing something like this:

```
function char_to_int(char) {
  switch (char) {
    case "0": return 0;
    case "1": return 1;
  }
}
```

“Wait-wait-wait,” he would stop me. “What if string is not binary?”

“Oh, so you want me to convert decimal string? Is it not binary?”

“No, it still is. But, you know, in the future…”

So he wants me to write generic code that still solves the specific problem. It might be ok for an interview, but in real engineering work, I would always prefer concrete code to generalized one.

For one, it’s faster to write. If you only solve a problem for specific cases you usually have a very limited amount of code paths to think of. It’s simpler to write.

It’s easier to get right too. Because you already know the problem. And you know it because you have it at hand, and you can dig as deep as you need, learn all the details you need and _make the right call_. Whether with a generalized version you’ll be _speculating_ about what problems you _might_ have in the future. You might have them. You might not. You might have a different problem, or you might misjudge the details.

Concrete is also easier to read. The code is filled with clues, concrete details that tell you the specifics of the problem that is being solved here. You see helpful constants (like `0` and `1` in the example above). You see branch conditions that make sense. You see a complete list of possible states instead of abstract opaque “collections” that could contain everything. A generalized code is usually one step above that, so you’ll have to _imagine_ concrete values before you could go through the code in your “mental debugger” (that’s how I read the code anyway).

But what if your prediction _was_ right and you actually _will_ need generalized version soon? Well, you could write it then. It’s the requirements that make writing good code hard, not the act of coding itself. And I doubt you’re good at predicting requirements. Nobody is. So you’ll just spend more time and create more obstacles for yourself in the future.

That’s what the Clojure ecosystem has taught me. Everything is as concrete as possible. Clojure just has no facilities to overgeneralize. It’s either a concrete thing or nothing. Get to the meat as fast as possible. Cut to the chase. If you need to print, just print, don’t invent printing facilities. If you have a record, put in fields you know about and don’t worry about the rest. Add them when you need them.

The advice is simple: don’t solve a problem you don’t have. Be rather afraid to create obstacles for the future you. You can’t see the future, but you can make the present as simple as it could be.