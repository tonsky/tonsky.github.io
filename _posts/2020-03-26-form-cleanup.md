---
layout: post
title: "Cleaning up form UI"
category: blog
summary: "Simple tips on fixing small details in UI"
draft: true
---

<style>
    p > img { width: 736px; height: 472px; max-width: unset; margin: 0 -96px; }
</style>

This is the first dialog that you see when you launch IntelliJ Idea 2020.1 EAP. What do you think about it?

![](00.original.png)

At the first glance, it looks clean and fresh. On a closer examination, there are many small details that are wrong. In fact, almost everything in this form can be improved!

Today I’ll show you a few universal tricks that, applied consistenly, could significantly improve the look and feel of your UI. We will not look at the logic of this form, only polish the visuals.

# Define the grid

First step is simple. We decide on a grid and try to align all sizes to it. Grid step shouldn’t be too big, otherwise you won’t be able to fit fine details in it. It can’t be too small either, that would mean you can put anything anywhere and the benefit of having the grid would be lost.

In our case we decide on a 8-pixel grid, mainly because some elements already align to it: top and bottom paddings, button height, 16×16 icons (although it might be harder to see due to alignment).

![](00.grid.png)

# Normalize the spacing

Ultimately, to make the UI look good we have to apply some consistent rules to it. In our case, we want everything to be made of the same-height blocks to give the UI rhytm and consistency. This is what we are going for:

![](01.wireframe.png)

Because all paddings are either 8 or 16 px, and all elements are 24px tall, UI aligns itself with the grid automatically.

Surprisingly, there’re lots of inconsistencies in the original form. Top padding is smaller that the other three, (?) button and Project SDK dropdown are not aligned to the edge: 

![](01.outer_padding.png)

<center>↓ becomes ↓</center>

![](02.outer_padding.png)

Horizontal and vertical splitters are of different width. Project... and Additional... labels are not fully aligned to the left edge:

![](03.splits.png)

<center>↓ becomes ↓</center>

![](04.splits.png)

Horizontal paddings between elements are very inconsistent. I’m narrowing them down to only two options: small 8px padding between _related_ elements (e.g. laben and a dropdown, icon and text), and large 16px padding between separate elements that just happen to stand in the same row (e.g. Previous/Next buttons):

![](05.in_component_horizontal.png)

<center>↓ becomes ↓</center>

![](06.in_component_horizontal.png)

Given that most of our components are 24px tall (list row, buttons, dropdowns), it makes sense for text labels to occupy the same amount of space. We are also putting 16px large gap between Project SDK and Additional libraries (unrelated), but only 8px gap between Additional libraries and the list beneath it, since it is its label. Same goes for error message in the bottom panel: since it’s related to the dropdown, we only separate them with the small gap.

I’m also taking this opportunity to align Project SDK label and dropdown next to it along their baselines. That’s how labels should always be aligned:

![](07.in_component_vertical.png)

<center>↓ becomes ↓</center>

![](08.in_component_vertical.png)

![](09.inner_padding.png)

![](10.inner_padding.png)

![](11.inner_padding_narrow.png)

![](12.inner_padding_narrow.png)

![](13.rows.png)

![](14.rows.png)

![](15.icons.png)

![](16.icons.png)

![](17.button_padding.png)

![](18.button_padding.png)

![](19.equal_buttons.png)

![](20.equal_buttons.png)

![](21.question_mark.png)

![](22.question_mark.png)

![](23.disabled_text.png)

![](24.disabled_text.png)

![](25.icons_outline.png)

![](00.original.png)

![](26.final.png)

![](27.final.grid.png)