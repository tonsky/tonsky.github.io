---
layout: post
title: "Building an ultimate writing machine from Sublime Text"
category: blog
summary: "My setup for long-form writing in Sublime Text"
---

_UPD: It’s been more than a month since I wrote the original draft for this article. I’ve since published three other blog posts here and more than twenty in [my telegam channel](https://t.me/nikitonsky_pub). The setup has proven itself fantastic, I am LOVING it and have zero urge looking anywhere else._

I’ve been a long-time fan of [iA Writer](https://ia.net/writer) for, well, writing. I bought the original iPad version, the original macOS version (when it was still called MacOS X), the Android one, and even the one they re-released as a “paid full-price update”. For me, it has found the perfect balance between features and simplicity, design and focus.

Here’s a screenshot of the original version of iA Writer [from 2011](https://tonsky.livejournal.com/231968.html):

<figure>
    <img src="./ia_writer_2011.jpg"/>
</figure>

And this is how it looks today, in 2021:

<figure>
  <picture>
    <source srcset="./ia_writer_2021.webp" type="image/webp">
    <img src="./ia_writer_2021.png"/>
  </picture>
</figure>

The design is so timeless it barely changed in 10 years.

And I’ve been happy with it, I am not going to complain. It _is_ great, both for the inspiration, for the innovation, and the actual implementation. I wholeheartedly recommend it to anyone doing any writing.

## iA Writer limitations

Yet, with time, I started to feel that it’s not the perfect fit for my particular workflow:

- iA Writer is designed to work with a single document at a time. I prefer to operate in projects and folders, switching back and forth between files.
- iA Writer’s library is tied to iCloud and you are supposed to keep all your files there. Recently I [moved away from iCloud](https://tonsky.me/blog/syncthing/) and in general prefer when tools don’t dictate where I keep my files.
- Having a self-hosted blog, publishing consists of an occasional CSS/JS/templates editing, which, again, iA Writer is not optimized for.
- I got used to Sublime’s conveniences like multiple cursors, Find in Files, Go Anywhere, and Git integration. It feels limiting not having those at your fingertips.

# Optimizing Sublime Text for writing

I decided to try to replicate what iA Writer does but inside Sublime Text. This is my setup.

## Color scheme

Gladly, iA Writer uses the absolute minimum of colors, so this was the easiest part to reproduce.

<figure>
  <picture>
    <source srcset="./color_scheme.webp" type="image/webp">
    <img src="./color_scheme.png"/>
  </picture>
</figure>

Source code: [github.com/tonsky/sublime-scheme-writer](https://github.com/tonsky/sublime-scheme-writer).

In Package Control: `Writer Color Scheme`.

P.S. Yes, there’s a dark version too.

## Font

iA Writer debuted with a proprietary [Nitti font](https://boldmonday.com/typeface/nitti/), but later switched to IBM Plex Mono, which is published under Open Font License.

After few tries and errors, I managed to set font size, line height and page width to the values that corresponds the closes to what iA Writer is doing:

<figure>
  <picture>
    <source srcset="./font_ibm_plex_mono.webp" type="image/webp">
    <img class="hoverable" style="width: 600px; height: 350px;" src="./font_ibm_plex_mono.png"/>
  </picture>
  Hover over image or click to compare to iA Writer
</figure>

The parameters are:

```
  "font_face": "IBM Plex Mono",
  "font_size": 16,
  "line_padding_bottom": 3,
  "line_padding_top": 3,
  "wrap_width": 72
```

This is already a very good match, but perfectionist in me wanted to get the exact match. Two things needed adjustment: ever so slightly increased font weight (Text instead of Regular), and an extra 3.5% character width. So I took a liberty and modified original IBM Plex Mono to match those characteristics. Check it out:

<figure>
  <picture>
    <source srcset="./font_writer.webp" type="image/webp">
    <img class="hoverable" style="width: 600px; height: 350px;" src="./font_writer.png"/>
  </picture>
  Hover over image or click to compare to iA Writer
</figure>

It matches almost perfectly! You can get my version of IBM Plex Mono at [github.com/tonsky/font-writer](https://github.com/tonsky/font-writer).

## Profile switching

We are set up for writing, but how do we get back to coding?

Problem is, writing mode affects multiple separate settings (19 in my case), it’s not just font and color scheme.

Meet Profile Switcher! It keeps multiple separate `Preferences.sublime-settings` files and switches between them instantly.

<figure>
  <picture>
    <source srcset="./profiles_commands.webp" type="image/webp">
    <img src="./profiles_commands.png"/>
  </picture>
</figure>

<figure>
  <picture>
    <source srcset="./profiles_switch.webp" type="image/webp">
    <img src="./profiles_switch.png"/>
  </picture>
</figure>

Sources: [github.com/tonsky/sublime-profiles](https://github.com/tonsky/sublime-profiles)

In Package Control: `Profile Switcher`.

P.S. An alternative way would be to put writing-specific settings in Markdown syntax-specific settings file. I like this approach less, because it affects e.g. Sublime Merge diff view 🤷 and all markdown files, some of which I don’t want to see in writing mode.

Also, Profile Switcher seems like something that can be useful beyond just writing/coding duo.

## All together (TL;DR)

1. Download and install [Writer font](https://github.com/tonsky/font-writer).
2. Via Package Control, install `Writer Color Scheme`
3. Via Package Control, install `Profile Switcher`
4. Create a `Writing` profile via the `Profiles: Create profile` command.
5. Put these settings in (just replace everything you currently have):
```
{
  "caret_extra_width": 2,
  "draw_centered": true,
  "draw_indent_guides": false,
  "draw_white_space": ["none"],
  "font_face": "Writer",
  "font_size": 16,
  "gutter": false,
  "highlight_line": false,
  "line_padding_bottom": 3,
  "line_padding_top": 3,
  "margin": 10,
  "scroll_context_lines": 2,
  "scroll_past_end": 0.5,
  "word_wrap": true,
  "wrap_width": 72,
  "color_scheme": "Packages/sublime-scheme-writer/Writer.sublime-color-scheme",
  "theme": "Adaptive.sublime-theme",
}
```

You should see something like that:

<figure>
  <picture>
    <source srcset="./all_together_after.webp" type="image/webp">
    <img src="./all_together_after.png"/>
  </picture>
</figure>

Compare to where we started:

<figure>
  <picture>
    <source srcset="./all_together_before.webp" type="image/webp">
    <img src="./all_together_before.png"/>
  </picture>
</figure>

To go back, use `Profiles: Switch profile` command and select `Default`.

# Missing features

Sadly, I wasn’t able to reproduce everything, which is another reason you might consider buying iA Writer instead of following this guide. It packs so much more than just a cool font.

## Duospaced font

Wait, what? Duospaced? What’s that?

Well, it’s another genius idea from iA: make `W` and `M` 1.5× width while leaving the rest of the font monospaced. It still looks and feels monospaced, but `M`s and `W`s are not ugly for no reason anymore. I wish more fonts were like that.

<figure>
  <picture>
    <source srcset="./Monospace-and-Duospace-Raster.webp" type="image/webp">
    <img src="Monospace-and-Duospace-Raster.png">
  </picture>
  Illustration from <a href="https://ia.net/topics/in-search-of-the-perfect-writing-font" target="_blank">ia.net</a>
</figure>

Can we reproduce it in Sublime Text? Yes and no. ST can display non-monospaced fonts, but it turns off bolds/italics if the font is not monospaced.

Seems like a pretty old limitation, maybe with enough votes it can get the attention it deserves? [Vote here](https://github.com/sublimehq/sublime_text/issues/279), meanwhile, we’re stuck with a strict monospaced version for writing.

If you want to try out Duospace font, it’s available freely at [github.com/iaolo/iA-Fonts](https://github.com/iaolo/iA-Fonts/tree/master/iA%20Writer%20Duo).

## Hanging punctuation

A feature I love since the very first version, the one and only way to format Markdown headers.

<figure>
  <picture>
    <source srcset="./hanging_punctuation.webp" type="image/webp">
    <img src="./hanging_punctuation.png">
  </picture>
</figure>

Why is it so cool? If `###` is aligned to the body text, you have to skip `###` and find the first meaningful letter.

If it hangs outside, nothing gets in your way.

## Dynamic typography

Another thing that is hard to reproduce in Sublime Text is dynamic typography. As far as I understand, iA Writer switches dynamically between multiple versions with different spacing and weights depending on current font size and screen DPI. This is hard to reverse-engineer and even harder to reproduce in Sublime Text.

Although, with profiles...

## Syntax highlighting

First of all, yes, iA Writer has syntax highlighting for plain English. It looks like this:

<figure>
  <picture>
    <source srcset="./syntax_highlighting.webp" type="image/webp">
    <img src="./syntax_highlighting.png">
  </picture>
</figure>

Supposedly, it helps one write more balanced sentences. As you might have noticed, I never used it :)

## Focus mode

In Focus mode only the current sentence is highlighted, everything else is dimmed. Helps you get into the zone, I wish this one was doable in ST.

<figure>
  <picture>
    <source srcset="./focus_mode.webp" type="image/webp">
    <img src="./focus_mode.png">
  </picture>
</figure>

# Conclusion

Sublime Text can be customized pretty heavily and become a fantastic writing tool.

Specialized software like iA Writer can do more, though.

Hope you find this useful, or (even better!) get inspired to do some writing.