---
layout: post
title: "Humble Chronicles: Decomposition"
category: blog
summary: "Overall shape of Humble UI project, a Clojure UI framework"
---

Now feels like a good time to make this blog an actual log, documenting my findings as I develop Clojure UI library, [Humble UI](https://github.com/HumbleUI/HumbleUI/).

This is an introductory post, describing the overall shape of the project.

None of the decisions are final and might change at any time. In fact, my expectation is that talking about them in public might help either solidify or replace them, rubber duck-style.

# Library decomposition

Humble UI is a Clojure framework, but it’s based on [JWM](https://github.com/HumbleUI/JWM/) and [Skija](https://github.com/HumbleUI/Skija/), both Java libraries. Skija draws graphics, JWM takes care of window management and OS integrations.

<figure>
  <img src="./deps.png">
</figure>

Yes, the fact that there are three libraries instead of a single monolithic package makes it harder to work with.

But I think it’s a worthy goal: without strong coupling, each library is much more versatile on its own. Use one, use both, or use neither: the decision is up to you. For example, Skija is already being used with AWT, LWJGL, winit, etc. The same applies to JWM: want window management but do your own graphics? Easy!

The use of Java also makes these available to every JVM language out there. So I am happy both with separation and the choice of Java as an implementation language.

# Shared types

As you can imagine, in a UI framework there are lots of points, vectors, and rectangles flying around. The same is true for both Skija and JWM, actually.

And if there’s one thing I hate to see the most it’s pointless conversions between structurally the same types but named differently.  E.g. from `class SkijaPoint { int x, y; }` to `class JWMPoint { int x, y; }` to `(defrecord HumblePoint [^int x ^int y])`.

So we need to unify. How?

a) Use built-in AWT classes. A good option but might require linking with java.desktop module and that’s a huge dependency.

b) Use a shared library. That’s what I [ended up doing](https://github.com/HumbleUI/Types), even though I hate to add one more project to the mix. Turned out Point and Rect are pretty much all you need to share, so it’s not that bad and probably won’t need to update it too often.

<figure>
  <img src="./deps2.png">
  Sorry, no logo :)
</figure>


# Java interop

Clojure has a great Java interop, but it’s relying on type annotations too much. And points and rectangles are really everywhere. An example:

```
(let [content-y (- (.-offset ^VScroll child))
      content-h (.-height ^IPoint (.-child-size ^VScroll child))
      scroll-y  (.-y ^IPoint child-rect)
      scroll-h  (.-height ^IPoint cs)
      scroll-r  (.getRight ^IRect child-rect)
```

Now, this poses an interesting challenge. How does one improve on this?

Solution one will be `(defrecord HumblePoint [^int x ^int y])`. But then we're back to square one: converting from JWM points to Clojure ones.

Solution two is to make `class IPoint` implement `clojure.lang.ILookup`, `IPersistentCollection`, `Associative` etc. It’s pretty easy to do and could make any Java class behave like Clojure map!

```
(:x (IPoint. 1 2)) ;; => 1
```

This makes working with Java classes from Clojure _very_ pleasant. Code snipped above turns into

```
(let [content-y (- (:offset child))
      content-h (:height (:child-size child))
      scroll-y  (:y child-rect)
      scroll-h  (:height cs)
      scroll-r  (:right child-rect)
```

which is _much_ more readable in my opinion.

The problem is, to implement e.g. `clojure.lang.ILookup` you need to depend on Clojure (static typing problems, ugh). And Clojure is a huge dependency to impose on everyone who would want to use Skija or JWM from Java.

I was struggling with this dilemma for a while until I arrived at a rather unorthodox decision: implement two versions of `types` library, one with Clojure interfaces and one without. Both contain the same classes, but the latter implements a few Clojure interfaces on them.

`io.github.humbleui.types`:

```
public class IPoint {
    public final int _x;
    public final int _y;
}
```

`io.github.humbleui.types-clojure`:

```
public class IPoint extends AFn implements Associative {
    public final int _x;
    public final int _y;

    @Override
    public Object valAt(Object key) {
        return valAt(key, null);
    }

    @Override
    public Object invoke(Object arg1) {
        return valAt(arg1, null);
    }

    ...
}
```

Both Skija and JWM depend on Clojure-free version of `types`. But when used through Humble UI we already have Clojure on classpath, so we replace `types` with `types-clojure`:

<figure>
  <img src="./deps3.png">
</figure>

Am I happy with this decision? I don’t know. It sure sounds complicated, and I don’t like that.

But:

- it works,
- it is simple for the end-user (you depend on `humbleui` and it does the right thing by default),
- I _love_ how natural it feels to use Clojure-enabled classes, even though they are written in Java.

I guess, until we find a better solution, we’ll keep this one.