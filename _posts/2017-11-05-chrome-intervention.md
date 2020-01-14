---
layout: post
title: "My web app died from performance bankruptcy"
category: blog
summary: "Chrome team breaks existing web to make Chrome perform better"
hackernews_id: 15634609
reddit_url: "https://www.reddit.com/r/programming/comments/7b5hjg/my_web_app_died_from_performance_bankruptcy_tldr/"
img_rel: true
starred: true
---

*TL;DR Chrome team breaks web to make Chrome perform better.*

There’s a widely-used piece of DOM API called `addEventListener`. Almost every web site or web app that does anything dynamic with JS probably depends on this method in some way.

Up until 2016 the convention was that you just pass an event type, a callback and an optional “useCapture” boolean flag:

```
target.addEventListener(type, listener[, useCapture]);
```

Then Google [came along](https://github.com/whatwg/dom/pull/82) and decided that this API is not extensible enough (which is true). What if one wanted more options? Surely, there must be a map of options, not just a single positional boolean argument. To which, again, I can’t agree more. So they added a second form: 

```
addEventListener(type, listener[, useCapture]);
addEventListener(type, listener[, options]);
```

Which means you can’t practically use the new form without feature detection. At all. Never ever. Old browsers can’t be made to understand `options` form. Period.

But that’s fine. That’s all right. That’s why we have feature detection.

## DOM APIs aren’t meant to be used

Ok, so there must be some sort of feature detection API accompanying this change, right? Well, if you thought so, you clearly have never worked with web APIs. Even though web developers are _supposed_ to _always_ use feature detection, they’re also supposed to rely on a complex, brittle and accidental effects to check for it.

This is the code [you’re supposed to be using](https://github.com/WICG/EventListenerOptions/issues/16):

```
var supportsPassive = false;
try {
  var opts = Object.defineProperty({}, 'passive', {
    get: function() {
      supportsPassive = true;
    }
  });
  window.addEventListener("test", null, opts);
} catch (e) {}
``` 

Basically, you’re constructing a special object with a side-effect-producing getter and hope for the browser to access it when you install a fake event listener. Surely, what could go wrong?

To be fair, there’s [an open discussion](https://github.com/whatwg/dom/issues/491) for adding better feature detection around this. But the timing is as messy as the API itself. If feature detection will ever be implemented, we’d have three browser classes:

- the ones that don’t support `options` at all,
- the ones that do support it but don’t support feature detection for it (so you’ll have to resort to the getter+fake event hack anyway),
- and the ones that support both feature detection and the API.

Think about it: a feature detection API that itself needs to be detected <nobr>¯\_(ツ)_/¯</nobr>.

## Making Chrome fast

But that’s not the end of the story. Chrome team proposed the API change to add `passive` option because it allowed them to speed up scrolling on mobile websites.

The gist of it: if you mark `onscroll`/`ontouch` event listener as `passive`, Mobile Google can scroll your page faster (let’s not go into details, but that’s how things _are_). Old websites continue to work (slow, as before), and new websites have _an option_ to be made faster at the cost of an additional feature check and one more option. It’s a win-win, right?

Turned out, Google wasn’t concerned about your websites at all. It was more concerned about its own product performance, Google Chrome Mobile. That’s why on February 1, 2017, they made all top-level event listeners passive by default. They call it “[an intervention](https://developers.google.com/web/updates/2017/01/scrolling-intervention)”.

Now, this is a terrible thing to do. It’s very, very, very bad. Basically, Chrome broke half of user websites, the ones that were relying on touch/scroll events being cancellable, at the benefit of winning some performance for websites that were not yet aware of this optional optimization.

This was _not_ backward compatible change by any means. All websites and web apps that did any sort of draggable UI (sliders, maps, reorderable lists, even slide-in panels) were _affected_ and essentially _broken_ by this change.

Yet, if things become faster, they can always praise Mobile Chrome for the improvement. And if something breaks, people would probably blame website anyways. RByers (a Google Team engineer who advocated for the intervention) [commented on Jun 16](https://github.com/WICG/interventions/issues/18#issuecomment-309058348):

> Our data suggests we made the right trade-off for the web platform as a whole and for Chrome as a product. I understand that your perspective is the opposite and I'm sorry about that - I really wish there was a way to make everyone happy, that's just not reality.

Also, notice how harsh timeline on this update was. The `passive` option was released on June 1, 2016 (Chrome 51). Passive made default was out on February 1, 2017 (Chrome 56). That’s just 8 months! They couldn’t even agree on feature detection API in that time! Before June 2016 you didn’t even have an API for marking listeners passive! And just 8 months later your app is already silently broken and punished for not using new API that others browsers barely started to roll out!

## Excerpts from intervention discussion

RByers [commented on Sep 27, 2016](https://github.com/WICG/interventions/issues/18#issuecomment-249916777):

> Of course we'd need some transition path over many years to avoid breaking the web too badly in order to get there.

RByers [commented on Feb 9](https://bugs.chromium.org/p/chromium/issues/detail?id=639227#c27):

> I'm deeply sorry for the frustration this has caused you. We've long tried the "opt-in" approach but have learned that on average developers don't make the effort to opt-in to better performance.

RByers [commented on Sep 29, 2016](https://github.com/WICG/interventions/issues/18#issuecomment-250315841):

> Given the huge positive response [that video](https://www.youtube.com/watch?v=NPM6172J22g) has gotten from users, we're willing to accept a little bit of hacks / compat pain here.

mixonic [commented on Feb 12](https://github.com/WICG/interventions/issues/18#issuecomment-279194353):

> The video in question as 14k views and 36 "thumbs up". I really, really hope this isn't being used to make a decision about whether this intervention is appropriate.

RByers [commented on Feb 17](https://github.com/WICG/interventions/issues/18#issuecomment-280532958):

> We really don't have more than anecdote (and our metrics) on the "support" side, and no precise way to quantify the breakage. I'd love to have a more quantifiable way to make these sorts of trade offs.

RByers [commented on Feb 11](https://github.com/WICG/interventions/issues/18#issuecomment-279163417):

> So far we've gotten a ton of feedback that users care about this.

RByers linked to [this tweet](https://twitter.com/RickByers/status/719736672523407360) with exactly 6 responses:

> [adslaton](https://twitter.com/adslaton/status/726094587056541699): thank you for the demo against our site. We will take a look at what you have documented and apply updates.

> [adamdbradley](https://twitter.com/adamdbradley/status/719739381704040448): how did you do that test to see the differences?

> [jordwalke](https://twitter.com/jordwalke/status/720073223430217729): I strongly, but super-respectfully suggest against this. It masks serious issues. I do not believe this is how the web wins.

> [rickbiastwit](https://twitter.com/rickbiastwit/status/832399971026505729): and it's not cool to break tons of drag/drop, scroll/zoom browser default behaviour prevention only to achieve nearly nothing

> [rickbiastwit](https://twitter.com/rickbiastwit/status/832399551847804928): chrome scroll intervention demo is definitely misleading. I tried on top end devices, no perceivable difference, enabled or not.

If you call it “a ton of positive feedback”, well, khm, you definitely see a reality in a different light.

## A moral

- Web APIs aren’t pretty.
- There’s no “clean” way of using DOM APIs. Even freshly designed, freshly released features _require_ you to rely on hacks for feature detection.
- Libraries wouldn’t simply “abstract away” all the unpleasant details of the underlying experience. E.g. React still [has no way](https://github.com/facebook/react/issues/6436) to force event listeners not being passive.
- Making code work in older browsers is an easy task because older browsers do not change. Once hacks are implemented for them, they’ll continue to work forever.
- Code that works for you right now might stop working in _future_ browsers. Constant effort is required maintaining your code against changes browser vendors drop on you.
- Breaking changes might happen pretty fast. Current version of Chrome is 62. Your code might being broken by the time Chrome 67 arrives. It almost certainly wouldn’t work in Chrome 100.
- Browser vendors have their own agenda. It mostly includes making their browsers look fast, sometimes at the cost of your websites become broken.

RByers [commented on Feb 9](https://github.com/WICG/interventions/issues/18#issuecomment-278658295):

> But in Chrome we're fundamentally unwilling to allow the mobile web to continue to die from performance bankruptcy. Other browsers are less aggressive, and people who prefer to be more conservative (preferring maximal compatibility over being part of moving the web forward aggressively) should prefer to use a more conservative browser.

As a user, I certainly do not care about “being part of moving the web forward aggressively”. Why should I? I like my stuff working, not broken. Nobody ever wants it the other way around.