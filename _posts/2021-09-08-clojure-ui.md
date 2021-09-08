---
layout: post
title: "Thoughts on Clojure UI framework"
category: blog
summary: "Ideas and inspirations for new Clojure framework for desktop apps"
---

I had a long‑standing dream: to implement a UI framework. Nothing inspires me more than noticing hundreds of subtle interactions (e.g. text selection in a text box) and seeing how combined, they bring together a feel of an alive and native component.

For a long time, I thought it’s a Leviathan task: something for a hundred‑people team and tens of years. But then Flutter came along and showed that it’s actually very feasible to re‑implement from scratch the entirety of platform UI to the very last detail.

After that, I joined JetBrains and worked not on one, but two different UI frameworks, which, again, turned out to be a very doable task for a small team.

Lately, Clojurists Together and Roam Research agreed to sponsor this work. I just can’t keep ignoring the signs the Universe is sending me.

I have no framework code yet, but some foundations are laid out in [Skija](https://tonsky.me/blog/skija/) and [JWM](https://github.com/HumbleUI/JWM/).

This post is my thoughts on the subject, some opinions I have and some questions I am not sure how to answer. It’s aimed to facilitate discussion, so please, share your thoughts!

## Why cross‑platform

The very same CPU, memory, and graphics card have no problem executing Windows, Linux, or macOS apps. They don’t care. Yet you can’t run a macOS app on Windows, Linux on macOS, or Windows on Linux. This is not a fundamental property of software, it’s a stupid historical mistake and we should work as hard as we can to correct it.

## Why desktop apps

Despite mobile dominance, desktop remains important for professional and productivity apps, and to build them we need tools.

Mobile has Flutter, Compose, SwiftUI, and desktop is... less advanced. Especially cross‑platform desktop.

## Why not mobile and desktop together

They’re too different. I can’t imagine writing a single app that works both on desktop AND on mobile and is good at both. Two different UIs — sure, but in that case, mobile is already pretty well covered.

I plan to focus on the high‑quality desktop instead of finding a mediocre middle ground between desktop and mobile.

## Why not web

The web started to get more suitable for apps probably since Gmail in 2004. It’s very convenient, yet still very limited and has shortcomings.

I think the fundamental problem of the web is that its APIs are too high‑level. Without low‑level APIs, simple things are sometimes stupidly hard to do, and you have to undo the stuff web is doing for you to get what you need. This is backward.

To top this off, the web is also too bloated, [too unstable](https://tonsky.me/blog/chrome-intervention/), OS integrations are too limited, and it puts a very hard limit on your performance.

In other words, the web is good for simple stuff, but not for anything complex because of lack of control.

And you don’t get to choose a programming language :(

## Why not Electron?

Electron is pretty much web, but with better OS integrations. But it’s still full of compromises: you don’t have threads, you’re stuck with JavaScript, can’t do your own layout, render smooth 144Hz animations, etc. It adds 150Mb to your app package and makes it a memory hog.

Yet it has been a massive success. I think it means our industry is craving for a good desktop UI framework. Electron is a great step in the right direction but not the final form.

## Native or custom

There are two classes of cross‑platform UI frameworks: ones that try to wrap native widgets (SWT, QT) and ones that draw everything themselves (Swing, JavaFX, Flutter).

I don’t really see a choice here, because I’ve never seen native widgets wrapped in cross‑platform abstractions that work. They always get a million little details wrong and still don’t feel native. QT might look decent on some Linux DEs but falls apart on other systems.

<figure>
    <img src="./qt.png">
    QT delivering a mix of native and custom widgets, mistreating native and creating a visual mess
</figure>

On the other hand, the web has taught people not to care for OS‑native look and feel. Your app will be accepted on all three platforms no matter which fonts, colors, and button shapes you use, as long as it looks and feels good.

## Why new framework

Well, I want to build UI in Clojure, and Clojure is limited to what Java has: Swing/AWT or JavaFX.

Swing/AWT is very old, has lots of shortcomings, and modern problems are solved on top of the old APIs. The downside of being in Java Core is that you can’t really evolve as the world changes because you can’t break or remove things.

JavaFX has learned a lot from Swing mistakes but has very limited graphics APIs and weird HiDPI and ClearType rendering issues.

Finally, declarative frameworks seem to be a good idea, but neither Swing nor JavaFX is declarative. There’s [cljfx](https://github.com/cljfx/cljfx/) which is declarative but it’s based on JavaFX widgets and I don’t want to use those.

## Why Clojure

Finally, the biggest reason I think this is a worthy idea is Clojure itself. Having worked on UIs in ClojureScript + Figwheel, live reload is a blessing, and Clojure has even a better story there. REPL + live reload + declarative UI framework is a match made in heaven. Anything else will have to try _really_ hard to get even close to this combination.

## Tweak and reuse

The web’s solution to customization is that each button has hundreds of properties you can tweak. You can set background, gradients, border radii, but if you want tricky behavior, you are out of luck. On the other hand, if you don’t want any of that, you still have to bear the weight and complexity of 100 default properties.

I am thinking of another way of approaching things. Somewhere deep in the Compose internals I once saw something like this (not verbatim):

```
fun MaterialButton(text) {
    Hoverable {
        Clickable {
            RoundedRectangleClip {
                RippleEffect {
                    SolidFill {
                        Padding {
                            Text(text)
                        }
                    }
                }
            }
        }
    }
}
```

(and they say Lisps have too many parentheses)

What struck me here was that:

a. Internals are perfectly composable with each other, and

b. It’s trivial to write your own button!

If I don’t want different corners, I just write my own button, using 6 out of 7 existing components and only replacing RoundedRectangleClip with my own implementation. Want gradient? Replace `SolidFill` with `GradientFill`, but keep the rest!

This creates a great benefit both for the library (built‑in buttons don’t need hundreds of properties to satisfy everyone) and for the users (they can meaningfully reuse parts from the standard library and only replace parts they don’t like).

Call it a Lego model, if you will. Perfectly composable and reusable chunks and an invitation to play with it.

## First‑class rendering access

At some point, somebody has to draw the actual button. I think it would be great to have direct and first‑class access to override the rendering of anything and draw what you need directly. I can spend all day guessing what type of rounded corners you might need, or can give you the Skia canvas and let you do what you want.

Nothing hurts me more than seeing people try to render diagonals with “creative” use of border‑width. It just feels wrong:

![](./ya.png)

The same goes for the layout, by the way.

## Declarative model

I’ve been on board with React VDOM approach since 2014 when it first made its way into the Clojure community. I think it’s a fantastic model and a huge breakthrough in how we build UIs.

I also think it works twice as good with an immutable and data‑oriented language like Clojure, where you can load in and out parts of your application, keep the state and see changes update the UI live without reloading.

The approach is seeing even more adoption now, as Flutter, Compose and SwiftUI joined the hype train. And I don’t see the reason not to go in that direction either.

## Why not immediate mode

Immediate mode sounds great in terms of simplicity and speed of development. Unfortunately, it has fundamental problems with layout (you only get one pass and can’t know the size of your content in advance), so retained mode it is.

I’d love to get as close as possible to the simplicity of it, though.

## Data‑oriented

I find the particular approach Compose has chosen a step in the wrong direction.  

In Compose, you don’t pass components around. Instead, you call side‑effecting functions that modify the global state somewhere.

```
var button = Button("Press me") // <- button already added to the dom
List(button) // <- impossible
```

This approach means that if someone has created a button, you can’t “hold onto it”: you can’t log, inspect, modify, throw it away. The shape of your code defines the resulting UI tree, not the shape of your values. And programming languages have been optimizing value manipulation, not code manipulation.

In contrast, in React components are values that you can save, pass and do whatever you want. That’s the approach I like.

## Clojure‑native

All ClojureScript‑React wrappers have to transform Clojure values to React values somehow. The beauty of building a new UI framework is that it can be Clojure‑native and completely skip this step: stuff like Hiccup could be interpreted _directly_.

## Layout

I have three main inspirations here. The first one is a one‑pass layout algorithm from Flutter:

<figure>
    <iframe width="600" height="337" src="https://www.youtube-nocookie.com/embed/UUfXWzp0-DU" title="YouTube video player" frameborder="0" allow="encrypted-media; picture-in-picture" allowfullscreen></iframe>
</figure>

It’s a brilliant system: simple, performant, easy to understand, easy to extend/hack around. This is important because we want people to intercept control and build their own layout logic for the components that are different enough from the components that will be shipped.

The second is [Subform layout](https://subformapp.com/articles/why-not-flexbox/), which taught me that layout system can be beautiful and symmetric, the same units can be used for everything and that flexbox is not the pinnacle of human engineering.

The third is a notion that parents should position children, and spacing is a part of the parent’s layout, thus [margins are considered harmful](https://mxstbr.com/thoughts/margin).

## Defaults are part of the API

SwiftUI [is notorious](https://tonsky.me/blog/swiftui/) for shipping components that change defaults depending on the OS version and context they are used in.

<figure>
    <a href="https://twitter.com/keff85/status/1430836667737427970"><img src="./swiftui.jpg"></a>
</figure>

What can you build if your foundation is unsteady? Not much. The approach SwiftUI is taking is to ask developers to update their apps every year.

We don’t play this way in Clojure. In Clojure, we want people to build apps that last tens of years without a single change.

The solution is simple: if _we_ commit to some defaults (e.g. paddings, line heights, colors), _you_ can consider them to be part of the stable API.

## Text handling

Typography is constrained the most in the existing Java UI frameworks: there simply is no place in the API to specify a stylistic set or load a variable font.

Since I’ve [worked with fonts](https://github.com/tonsky/FiraCode/) a little bit, I am eager to include high-quality modern typography into the new framework.

At the very minimum, I want to get these things:

- Variable fonts
- Open‑type features (stylistic sets, fractions, etc)
- [Cap‑height‑based text alignment](https://tonsky.me/blog/font-size/)

The latter means font size and line-height will probably work a little different than they do on the web, but it will be much easier to correctly and reliably center a text label inside a button.

<figure>
    <a href="https://twitter.com/romanshamin/status/1423278913594163202"><img src="./text.jpg"></a>
</figure>

## Being pedantic

As a pedantic person, I want every little detail to be right. I believe that even small discrepancies can communicate a feeling of a poorly made, unreliable product.

Stuff I aim to get right:

- UI scaling & pixel grid. No blurry lines even on fractional UI scales.
- VSync. Getting refresh rate correct and synchronized with the monitor.
- Color spaces. Believe it or not, it’s the responsibility of the app to render in the monitor color space. With the popularity of Macs, P3, and HDR external monitors, things are not as simple as they used to be in sRGB days anymore.
- Multi‑monitor. Each can have its own scale/refresh rate/color profile.

## Performance

Getting smooth animations is very important. I am aiming at 144 Hz, as this seems to be the most common “above 60” refresh rate for the next few years. No concrete plan here, just “don’t do stupid things and measure performance often” for now.

## Startup time

The startup time of Clojure is bothering me, but maybe compiling to GraalVM or bundling a [custom Clojure runtime](https://github.com/zajac/clojure) could help — remains to be seen. There’s hope.

## Full package

I imagine everything will come together as some sort of Electron but for JVM.

The goal is this: write your app in Clojure, package and distribute it as any other native desktop app.

Definition of done: nobody can tell the app is written in Clojure.

## Your feedback

This is just a dump of everything that is in my head right now. Nothing is final, many things are vague, everything could change.

That’s why I want to start a discussion:

- What do you expect from a desktop UI framework for Clojure?
- What would you build?
- What will not work for you?
- Where am I wrong?
- Do you have a better insight into any of the topics?
- Did I miss something important that should be covered?

Share your thoughts! Reply [on Twitter](https://twitter.com/nikitonsky/status/1435624249381826566?s=20) or [drop me a letter](mailto:niki@tonsky.me).