---
layout: post
title: "Library focus"
category: blog
summary: "Why you shouldn’t write libraries as a part of a bigger software project"
img_rel: true
---

Software projects rely on libraries heavily. Usually it’s pretty simple: you have a need, you pick a library, you integrate it and get results. Most of the time it’s advantegeous—economically—because it’s faster than rolling your own implementation.

Clojure, though, faces an unique challenge here: many problems that have been addressed by libraries can be trivially solved in your own code too, and it can be done in a time that is comparable to the time that’ll take you to figure out a library. I’m not exaggerating—it’s really _that simple_.

Sure, there are libraries that do heavy lifting—stuff like DataScript or Instaparse, those will take you more than a day to write. But a good half of all libraries, or even more, _can_ be reproduced in day’s time. Maybe not for all use cases, but for all _your cases_ at least. If you think of it, all clojure.test does is just asserting for you, Component runs functions that you give it to run, compojure/bidi/other routers do pretty basic regexp matching on strings, etc.
  
So, if you _can_ solve your problems by writing your own code, and there’re no economic reasons not to, why shouldn’t you? You’ll get more freedom and better fit for your unique problems, right?

Well, you shouldn’t do it because you’re badly positioned to do so. Libraries and products need different focus. If you’re writing a production project, _there’re no powers in play that’ll stop you from compromising greater but further good to the immediate needs._ You’ll inevitably end up with incoherent, incomplete home-grown “library” that’s pain to use (as the rest of your code is) and is tightly coupled to your project.

Code is probably the simplest to isolate, but the assumptions, the features you decided to focus on will create the unfortunate coupling and compromised vision. Sometimes you’ll skip on crucial parts because there’s no time, sometimes you’ll focus on parts that shouldn’t be solved in there (but were very convenient—and quick—to solve there nevertheless). And of course you’ll never add stuff in advance, so it’ll never be finished, not really, and will change all the time.

So no, you can’t build a project and publish parts of it open-source while at it, not in a valuable way. It’s either one or the other.

I’m only writing about this because the difference’s so subtle it’s almost magical: the very same people, perfectly capable software engineers, when put in a bigger project context, almost physically can’t produce anything comparable to a library code in terms of clarity, isolation and reusability.

So yeah, choosing a library over rolling your own implementation has a benefit: a benefit of author’s focus that’s different from your immediate needs.

And no, don’t plan a project’s architecture on a library you plan to write first that’ll make rest of the development easier. That’ll never fly.