---
layout: post
title: "Humble Chronicles: Layout"
category: blog
summary: "New Sublime Text plugin for working with Clojure"
draft: true
---

This is a second post documenting the process of developing [Humble UI](https://github.com/HumbleUI/HumbleUI/), a Clojure UI framework. In this post we discuss layout problems.

None of the decisions are final and might change at any time. The main purpose of these posts is to share ideas and get better understanding what can work and what can’t. Feedback is welcome!

# Pixels and pixels

Doing graphics on the screen used to be simple. 1 pixel was literally 1 set of diodes lighting up on your physical screen.

<figure>
  <img src="./px_physical.png">
  A physical pixel
</figure>

Now, with the introduction of HiDPI and retina screens, things became more confusing.

The way I like to think about it is this:

- 1 physical pixel is, as before, 1 set of diodes on your screen.
- 1 logical pixel is an abstract unit of measurement that interfaces could be described in.

Note that logical pixels are not even the same physical size, in mm, across devices. The only (softly held) invariant about logical pixels is that if, for example, 20 logical pixels is a reasonable size for a button on one device, it will still be a resonable size on another one, given the difference in pixel density, UI scaling, screen size and viewing distance.

In other words, logical pixels are units to define all your UI dimensions in. Physical pixels are what you should render your UI to.

<figure>
  <img src="./px_logical.png">
  19 logical pixels are still 19 logical pixels, retina or not
</figure>

Logical pixels could be converted to physical ones by multiplying them to UI scale. E.g. for Mac retina screens UI scale is 2.0. For Windows and Linux, this could be freely adjusted: 1, 1.25, 1.5, 1.75, 2.0, 2.5 etc are all reasonable UI scales.

Humble UI approach is simple:

- All render surfaces are in physical pixels.
- Scale is an arbitrary floating-point number set once per window.
- By default UI scale is identified from OS settings, but could be also arbitrary adjusted.
- All component sizes are defined in logical pixels.
- Logical pixels are floats, physical pixels are ints.
- All sizes (e.g. component width/height) are rounded to physical pixels.

So there could be no 20.5 physical px button, but 20.5 logical px on retina—why not? As long as physical result fits the physical pixel grid, we are happy:

<figure>
  <img src="./scaling.png">
</figure>

As a bonus, there’s no problem adjusting UI scaling to your heart’s desire, without relying on OS settings:

<figure>
  <video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="ui_scale.mp4" type="video/mp4"></video>
</figure>


# Where are we at?

Humble UI is in a very raw state right now, but enought to build a functioning cross-platform calculator:

<figure>
  <img src="./calculator.png">
</figure>

A couple of interesting things about the layout here. It’s adaptive (changes with window size):

<figure>
  <video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="stretch.mp4" type="video/mp4"></video>
</figure>

Font size is set dynamically (⅓ of button height).

No grid component yet, but C/0 button widths are calculated using formula:

<figure>
  <img src="./formula.png">
</figure>


- Measure/Draw model
- Stretch by default
- Stretch/hug units
- Dynamic formula
- No minimal size
- Arbitrary Align 
- Additive model
- Text box
- No margin

- REPL-friendly
