---
layout: post
title: "Macs and 4K 120Hz displays compatibility list"
category: blog
summary: "Macs/4K 120Hz displays compatibility list"
---

<style>
  h1 + h2 { margin: 2.5em 0 0.5em; }
  h2, .row { display: flex; align-items: center; }
  h2 > img, .row > img { margin: 0 0.5em 0 0; }
  figure > img { border-radius: 10px; }
</style>

Following is the compatibilty list between various Macs and various 4k 120+ Hz displays.

> Why?

Because Apple is not eager to share this kind of info themselves.

> Will this list be updated?

Yes! I am starting this list with what I personally encountered, but hope for your contributions to expand it.

> Why isn’t my Mac and/or display on the list?

Send a PR! Read the contribution guidelines at the end.

> Why is my display listed red, even though I can use it just fine?

This list is about supporting full 4k resolution (3840×2160) at 120Hz only! <span style="padding: 2px 6px; border-radius: 4px; background-color: #4dcf01;">Green</span> icon means this Mac can work with that display at 4k _and_ 120Hz. <span style="padding: 2px 6px; border-radius: 4px; background-color: #ff5835">Red</span> icon means it works, but in compromised settings (60 Hz, 1440p resolution).

# The list

## <img src="mini_2018.png" height=32> <span>Mac mini 2018</span>

<div class="row"><img src="doesnt_work.png" height=64> <span>Acer XV273KPbmiipphzx</span></div>

## <img src="air_2018.png" height=32> <span>MacBook Air (Retina, 13", 2018)</span>

<div class="row"><img src="doesnt_work.png" height=64> <span>Acer XV273KPbmiipphzx</span></div>

## <img src="mbp_15_2019.png" height=32> <span>MacBook Pro (15-inch, 2019) w/ Radeon Pro 560X</span>

<div class="row"><img src="works.png" height=64> <span>Acer XV273KPbmiipphzx</span></div>

<div class="row"><img src="doesnt_work.png" height=64> <span>ASUS XG27UQ</span></div>

## <img src="mbp_16_2020.png" height=32> <span>Macbook Pro (16-inch, 2019) w/ Radeon 5500M</span>

<div class="row"><img src="works.png" height=64> <span>ASUS XG27UQ</span></div>

<div class="row"><img src="works.png" height=64> <span>Acer Predator XB3 (XB273K GP)</span></div>

## <img src="mini_2018.png" height=32> <span>Mac mini (M1, 2020)</span>

<div class="row"><img src="doesnt_work.png" height=64> <span>Acer XV273KPbmiipphzx</span></div>

<div class="row"><img src="doesnt_work.png" height=64> <span>Acer Predator XB3 (XB273K GP)</span></div>

<div class="row"><img src="works.png" height=64> <span>LG UltraGear 27GN950-B</span></div>

## <img src="air_2020.png" height=32> <span>MacBook Air (M1, 2020)</span>

<div class="row"><img src="works.png" height=64> <span>ASUS PG27UQ</span></div>

# How to contribute?

If you have a combination of 4k monitor with 120+ Hz refresh rate and a Mac that’s not listed here, please [open a PR](https://github.com/tonsky/tonsky.github.io) (don’t worry about graphics/icons, I can add those myself).

Some guidelines. Please DO report:

- Both positive (it works) and negative (it doesn’t work) results.
- Positive results for pre-2018 Macs.

Please DON’T report:

- Any results about lower resolutions (1440p @ 120Hz or 4k @ 60Hz). Most Macs have no trouble powering those, so there’s no point in building a list.
- Anything already officially reported by Apple (yes, 5k/6k work just fine at boring 60 Hz).
- Results you didn’t experience yourself (e.g. read on a forum, heard from a friend). I want this list to stay as reliable as possible.
- Negative results for pre-2018 Macs. Thunderbolt version enabling 4k @ 120 Hz only came in 2018, so it’s unlikely Macs before that would have the support.

When opening a PR, please attach screenshots like these two. First one shows the exact model of your Mac and its GPU. Second one shows your display model and resolution it is working in.

<figure><img src="./about_this_mac.jpg"></figure>

<figure><img src="./system_report.jpg"></figure>

I hope this effort ends up useful.

Also see [Time to upgrade your monitor](https://tonsky.me/blog/monitors/).
