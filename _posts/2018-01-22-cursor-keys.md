---
layout: post
title: "Cursor keys belong at the center of your keyboard"
category: blog
summary: "Remap `CapsLock` + `IJKL` to act as cursor keys and teach yourself to use it"
img_rel: true
starred: true
---

*TL;DR Remap `CapsLock` + `IJKL` to act as cursor keys and teach yourself to use it*

Have you ever wondered if default keyboard layout is optimal for today’s tasks? Modern keyboards inherit their design all the way back from early typewriters. Typewriters were optimized for, well, typing. That makes modern keyboards best optimized for typing too. With your hands in the _default position_ every letter and punctuation are easy to access.

<figure><img src="./normal_position.png" /></figure>

But on modern computers you also need to move the cursor around. Typewriter didn’t have a cursor, so there were no buttons for it nor was there a good place saved for them. So, on computers cursor keys are put far away from the center.

<figure><img src="./far_away.png" /></figure>

But it’s not _that_ far, is it? Not in absolute terms, no. But to get there, you have to move your palm, which is quite effortful. If you don’t register that as a significant effort it’s only because you’re used to it. It doesn’t mean it’s not there. In case you’ve ever heard that programmers are not happy with using the mouse—it’s the same reason actually. The mouse itself is fine, it’s just too far away what makes it inconvenient. Distance matters.

Not only are cursor keys far away, you end up needing them _a lot_. My estimate is that typing/cursor movements are at 30%/70% ratio, at least if you work with text (programming, writing, etc).

So on a modern keyboard _cursors keys_ belong in the center, not the letters. A perfect keyboard would look something like this:

<figure><img src="./center.png" /></figure>

There’s actually a precedent to support my claim: a text editor called Vim. Quite old, but extremely popular amongst programmers, sometimes to the point of fanaticism. And the feature Vim users praise the most is the ability to move the cursor without changing hand position (`HJKL` keys). They actually like it so much they write plugins for every other program in the world to support same shortcuts via “Vim modes”. Talk about dedication.

<figure><img src="./vim.png" /></figure>

## The solution

So we want the benefits of having cursor keys at the center of the keyboard, we want it to work system-wide (versus being limited to a single app), and we definitely don’t want to buy a new keyboard for that. Especially the one with an uncommon layout.
 
This is what I did: I scripted `CapsLock+IJKL` to act as cursor keys system-wide.

<figure><img src="./remap.png" /></figure>

Keyboard geeks know that CapsLock key is special: an absolutely useless key sitting on a home row, almost at the center, super easy to reach. How lucky are we to have it? And `IJKL` form nice “reverse T” shape that is natural to use.

## How-to for macOS

Install <a href="https://pqrs.org/osx/karabiner/index.html" target="_blank">Karabiner Elements</a> (free):

<figure><img src="karabiner_install.png" /></figure>

Run it:

<figure><img src="karabiner_run.png" /></figure>

Import my config by [clicking on this link](karabiner://karabiner/assets/complex_modifications/import?url=https://s.tonsky.me/karabiner/capslock_ijkl_fn.json) (it’s the official way to install complex modfications):

<figure><img src="karabiner_import.png" /></figure>

Side note: if you plan to use Caps as Ctrl, use [this link instead](karabiner://karabiner/assets/complex_modifications/import?url=https://s.tonsky.me/karabiner/capslock_ijkl_ctrl.json).

After that, one more step is required. Enable it:

<figure><img src="karabiner_enable.png" /></figure>

In the end it should look like this:

<figure><img src="karabiner_result.png" /></figure>

From now on when you _hold_ Caps Lock and press any of `IJKL` it’ll move your cursor as if you’d press arrow keys. In any context. You can also combine it with modifiers (Shift/Cmd/Alt/Ctrl).

Bonus: I also map `CapsLock+H` to `Backspace` which I find very handy as well.

## How-to for Windows

For Windows I had success using <a href="https://www.autohotkey.com/" target="_blank">AutoHotKey</a> (free) with this config:

```
SetCapsLockState, AlwaysOff

CapsLock & j::Send, {blind}{Left}
CapsLock & k::Send, {blind}{Down}
CapsLock & l::Send, {blind}{Right}
CapsLock & i::Send, {blind}{Up}
CapsLock & h::Send, {blind}{Backspace}
```

## How-to for Ubuntu

The recepie below was contributed by the reader. I have not tried it and can’t confirm if it works.

Create a file `ijkl` in your home directory:

```
keycode 66 = Mode_switch
keysym j = j J Left
keysym l = l L Right
keysym i = i I Up
keysym k = k K Down 
```

Open Startup Applications Preferences and put the following in the “Command”:

```
xmodmap ijkl
```

Reboot and you're good to go.

## Important: Overriding Your Habits

I wish it was all you need to do. It is not. The last thing to reconfigure is yourself.

You already know how to type, and that’s the problem. Old typing habits will get in your way.

The first thing you need to know: people don’t automatically pick up better habits. If you already know how to do something, and new way comes to your attention, you won’t automatically switch. Even if the new way is better. Instead, after you’ve initially learned _some_ way to achieve something, your default behavior becomes to always go with it. This is called local optima. Nothing wrong with it, it’s just how we humans work.

Unfortunately, getting out of local optima is not a question of repetition or “getting better with time”. Even if you type million times a day you’ll still do it the old way, leading to no change. Improvement requires conscious effort.

The best way to trick yourself into learning a new habit is to burn the bridges. If the old habit is not an option, you’ll be forced to learn a new one instead. That’s why I’ve disabled arrow keys in my Karabiner config:

<figure><img src="karabiner_disabled.png" /></figure>

With this done, you would force yourself to use new typing method and you’ll automatically start learning. Be prepared: at first, it will feel ridiculous because you won’t be able to do very basic things like moving cursor one symbol left or right. It’ll feel like having to learn how to walk all over again. You’ll need to focus on things that you now get for granted. You’ll have to think, plan and coordinate your fingers in order to get basic things done.

It is frustrating, it is annoying as hell, but there’s no way around it. Don’t worry: it won’t take long. After a day or two you’ll be able to do most things, and after a week you’ll be able to move the cursor without thinking about it at all. Congratulations: you just learned a new habit!

After a week it’s safe to enable cursor keys and backspace back. At this moment you might try to use the old way just to see how ridiculously inconvenient it was. But you needed to develop new habit first to really appreciate and _feel_ it.

If you’re curious, learning new keyboard or an alternative layout takes about the same amount of time and effort. Still worth it.

## What’s next?

You now have a superpower. Go write something awesome!