---
layout: post
title: "Cleaning up form UI"
category: blog
summary: "Simple tips on fixing small details in UI"
---

<style>
    @media (min-width: 736px) {
      p > img { width: 736px; height: 472px; max-width: unset; margin: 0 -96px; }
    }
</style>

_Translations: [Japanese](https://coliss.com/articles/build-websites/operation/work/cleaning-up-form-ui.html)_

This is one of the first dialogs you see after launching IntelliJ Idea 2020.1 EAP. What do you think about it?

![](00.original.png)

At first glance, it looks clean and fresh. On a closer examination, though, many small details here turn out to be wrong. In fact, almost everything in this form can be improved!

Today I’ll show you a few universal tricks that, applied consistently, could significantly improve the look and feel of your UI. We will not look at the logic of this form, only polish the visuals.

# Define the grid

The first step is simple. We decide on a grid and try to align everything on it. Grid step shouldn’t be too big, or you won’t be able to fit fine details in it. It can’t be too small either, that would mean you can put anything anywhere and the benefit of having the grid would be lost.

In our case, we decide on an 8-pixel grid, mainly because some elements already align to it: top and bottom paddings, button height, 16×16 icons (might be harder to see due to the current alignment).

![](00.grid.png)

# Normalize the spacing

Ultimately, to make the UI look good we have to apply some consistent rules to it. In our case, we want everything to be made out of the same-height blocks to give the UI rhythm and consistency. This is what we are going for:

![](01.wireframe.png)

Because all paddings are either 8 or 16 px, and all elements are 24px tall, everything aligns itself with the grid automatically.

Surprisingly, there’re lots of inconsistencies in the original form. Top padding is smaller than the other three, (?) button and Project SDK dropdown are not aligned to the edge: 

![](01.outer_padding.png)

<center>↓ becomes ↓</center>

![](02.outer_padding.png)

Horizontal and vertical splitters are of different width. “Project SDK” and “Additional Libaries” labels are not fully aligned to the left edge:

![](03.splits.png)

<center>↓ becomes ↓</center>

![](04.splits.png)

Horizontal paddings between elements are very inconsistent. I’m narrowing them down to only two options: small 8px padding between _related_ elements (e.g. label and a dropdown, icon and text), and large 16px padding between separate elements that just happen to stand in the same row (e.g. Previous/Next buttons):

![](05.in_component_horizontal.png)

<center>↓ becomes ↓</center>

![](06.in_component_horizontal.png)

Given that most of our components are 24px tall (list row, buttons, dropdowns), it makes sense for text labels to occupy the same amount of space. We are also putting a 16px large gap between “Project SDK” and “Additional libraries” (unrelated), but only an 8px gap between “Additional libraries” and the list under it since they are in a label-content relation. The same goes for the error message in the bottom panel: since it’s related to the dropdown, we only separate them with the small gap.

I’m also taking this opportunity to align Project SDK label and dropdown next to it along their baselines. That’s how all labels should be aligned, always:

![](07.in_component_vertical.png)

<center>↓ becomes ↓</center>

![](08.in_component_vertical.png)

Now let’s address padding inside panels. Lists only have left/right paddings (top/bottom are handled by the padding inside elements themselves). The bottom panel has all four paddings.

Horizontal and vertical paddings don’t necessarily have to be equal (see [bureau.ru/bb/soviet/20140908/](https://bureau.ru/bb/soviet/20140908/) if you can read Russian), but for simplicity let’s assume they are.

First, we try generous 16px padding:

![](09.inner_padding.png)

<center>↓ becomes ↓</center>

![](10.inner_padding.png)

Nothing wrong with this version, but it does feel a little too spacious. Let’s try 8px paddings:

![](11.inner_padding_narrow.png)

<center>↓ becomes ↓</center>

![](12.inner_padding_narrow.png)

That feels about right!

# Pixel hunting

I’m pretty annoyed by this 1px gap between the first element and the container.

![](13.rows.png)

 Gaps like this are usually a mistake: if you _do_ want a gap, make it look like it is intentional, not as a minor glitch:

![](14.large_gap.png)

Russian readers might read more about it at [ilyabirman.ru/meanwhile/all/almost/](https://ilyabirman.ru/meanwhile/all/almost/).

I am also removing delimiters since they serve no purpose here. List it too small for delimiters to make a difference. Even worse: three out of five groups here have just one element. If you have single-element groups, it means you are doing grouping wrong.

Result:

![](14.rows.png)

Not all icons are perfectly located too. To notice that, we draw a 16×16 square and align it to the text, then align icons inside that box:

![](15.icons.png)

<center>↓ becomes ↓</center>

![](16.icons.png)

# Buttons

Time to normalize some buttons. Strangely enough, original form had weird inconsistent paddings for all controls. The fix is very simple again: 8px paddings inside dropdowns, 16px inside buttons. 

![](17.button_padding.png)

<center>↓ becomes ↓</center>

![](18.button_padding.png)

For controls inside the form, all looks fine now. We can further improve the footer, though, by making all its buttons equal width. It will make them look even more consistent.

Here’s how to do it: I’m taking the largest button (Previous), add 16px side paddings, then align resulting width to be multiply of 8 to fit in the grid. Then I stretch every other button to that width. Good luck coding this algorithm in CSS :)

![](19.equal_buttons.png)

<center>↓ becomes ↓</center>

![](20.equal_buttons.png)

Let’s now bring our attention to the question mark button in the lower-left corner. Currently it is 22×22px, which is 2px smaller than button height (24px).

![](21.question_mark.png)

The problem is, visually round shapes look smaller than square ones of the same size. That’s why we have to make it slightly larger, not smaller than the button. I’m making it 26×26px instead:

![](22.question_mark.png)

By the way, for the same reason triangle arrow inside the dropdown is only 7px from the border, not 8 as everything else—compensating for the visual weight.

# Color contrast

Probably by mistake, text inside the disabled dropdown is painted black (should be gray). The error message under it is correctly gray, but the shade is different. There’s no reason to use two different grays where one would do.

![](23.disabled_text.png)

<center>↓ becomes ↓</center>

![](24.disabled_text.png)

Finally, look at the icons in the selected rows. They are hard to read, aren’t they?

![](25.icons_outline.png)

For some reason, they were made half-transparent so that selection color bleeds through them, making them even harder to read. Of course, a good solution would be to draw alternative versions for the dark background:

![](26.icons_alternate.png)

If your app supports dark/light scheme you might already have two sets of icons.

If you don’t want to draw each icon twice, there’s a simple trick that can save you: add 1px white outline. It would be invisible on a white background but will make icons easy to read on a dark one:

![](26.icons_outline.png)

# Typography

User interface typography, especially in non-entertainment, professional UIs, is simple: stick to the single font/size unless you have a really, really good reason not to. In our case, the error message is smaller than any other text on the form:

![](27.font_size.png)

Let’s fix this:

![](28.font_size.png)

The next thing that worries me about error message is that its location doesn’t help you understand what you need to do to fix it.

![](29.error.png)

If a control has an incorrect value, show error message directly next to it:

![](30.error.png)

Finally, capitalization. The first word on every label and in every control should be capitalized. First word after “Error:” should be capitalized as it starts a new sentence. But there’s no need to go crazy and capitalize every word in every label. They aren’t the book titles. If you are interested in details, [grammarly.com/blog/capitalization-rules/](https://www.grammarly.com/blog/capitalization-rules/) is a good read.

I have also decided to treat myself and use typographic quotes instead of programmer ones (read more [typographyforlawyers.com/straight-and-curly-quotes.html](https://typographyforlawyers.com/straight-and-curly-quotes.html)):

![](31.capitalization.png)

<center>↓ becomes ↓</center>

![](32.final.png)

# Final result

This is the original form we started with:

![](00.original.png)

And this is how looks after 17 individual fixes we’ve applied:

![](32.final.png)

This might look almost the same for the quick eye, but if you start looking into details you’ll find out it’s much more consistent and accurate.

Also, check out how the final form aligns to the grid:

![](33.final.grid.png)

I hope those tips will help you make your interfaces even better!