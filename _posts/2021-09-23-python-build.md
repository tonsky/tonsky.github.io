---
layout: post
title: "Python as a build tool"
category: blog
summary: "Why Skija and JWM use Python instead of existing build tool"
---

Normally, when starting a Java project (or any other programming project, really), you don’t want to reinvent the wheel. You go with the de-facto build system, folder structure, environment etc. The ones that rest of the world is using.

Yet, both [Skija](https://github.com/JetBrains/skija/tree/master/script) and [JWM](https://github.com/HumbleUI/JWM/tree/main/script) are built using Python scripts instead of more traditional Ant/Maven/Gradle/SBT. Why? Let’s find out!

# Maven

When we just started Skija, I used Maven because I knew it well. Pretty soon we run into Maven limitations: it’s a very rigid system. It does well on standard projects, but when you need something extra, it becomes an obstacle.

And we had very non-standard project. Skija is 50/50 Java AND C++ project. We build native artifacts and pack them in JARs files. We have multiple different JARs built from the same source (one per platform). We also pre-process Java sources with Lombok before compiling them.

> Funny fact: the only IDE that supports simultaneous Java + C++ development is Android Studio. No, we weren’t ready to make this sacrifice.

With each complication, I fought hard to make it work with the Maven. But its rigidness made easy things extremely hard. E.g. I couldn’t control which files from the directory should go into JARs and which should not. I couldn’t tell it at which directory to look.

Eventually, I gave up. It was _ridicously_ hard affair to achieve trivial things. E.g. you need to generate/modify XML file on the fly just to pass credentials from the environment. Or I couldn’t _stop_ Maven from running tests when I didn’t need to.

# Gradle

We didn’t even try Gradle on Skija project, but I saw my colleagues use it in the wild. Gradle is mostly magic: you write “hints” to the build system and then, hopefully, right thing will happen at the right time. It’s also full of defaults and conventions, so many details are implicit. You have to know them to guess what will happen, but you can’t google them, because there’s no code.

# Make

We tried Make but I was unable to make it compile Java incrementally. It can process single files, but `javac` requires that you collect all new classes first and then invoke it once. It also kind of depends on Bash (or some other scripting) for things it doesn’t directly support,  see below.

# Bash

We tried Bash, but Bash is a terrible, terrible language. Variables do not work as you expect them to work, there are significant whitespaces in your script, spaces in strings are special, and to top it all there are slight differences in CLI utilities you have to use with Bash. We had OS checks for `sed` and `xargs` already because they work differently on macOS/Linux, and we barely wrote 200 lines of Bash.

Bash also wasn’t cross-platform enough. The easiest way to make it work on Windows was installing Git (Yes, Git! I know!), but then C++ compilation started to behave funny because it couldn’t figure out if it’s supposed to build Windows or Linux binaries. 

Once again, it started to feel like too much effort for such simple stuff. I just wanted to invoke `cmake` and `javac` and move some files around. Is it too much to ask?

# Babashka

Being faithful Clojurist, I tried Babashka. It’s a Clojure interpreter with some extra batteries and ridicously fast startup time. In other words, perfect for scripting.

Babashka almost made it. It was imperative, doing exactly what I wanted in a most straightforward way possible. It was cross-platform. Sure, it felt a little like a stretch (JetBrains isn’t known for its love for Clojure, and other people who wanted to participate would have to learn it) but I was in despair and ready to try anything.

Babashka also was fast. I didn’t mentioned it earlier, but by invoking javac/cmake directly you can have an incremental rebuild of both Java and C++ done and new version of the app running by the time that only takes Maven/Gradle to wake up. It’s a difference between 1 second and 3-5 seconds, but when you do it a few times a minute (yes, I like to try things out a lot), it becomes important and _very_ noticeable.

So what was wrong? Unfortunately, batteries weren’t exactly there. Working with files (default directory, parent, listing children etc) was a bit cumbersome, invoking external processes was also tricky. I caught myself trying to improve usability of some functions but then it wouldn’t be portable, I’d have to copy the implementation whenever I go, etc.

Also, I was really concerned about forcing random people to learn Clojure. I’d probably try it again in when it matures and when the rest of the code will already be in a Clojure. Then it’ll be a match made in Heaven.

# Python

Enter Python. It’s cross-platform. It’s fast to invoke. Unlike Bash, it’s a real programming language. It’s one of the most popular languages on the planet. Standard library seems pretty good: it is optmised for scripting as much as for programming. All the necessary batteries are there: to this day, all my build scripts only depend on Python standard library and on zero external dependencies.

From the very beginning, I was unbelievably happy. The contrast with Maven was stark: if I wanted to move some files to the folder, I just wrote one line to copy them. If I wanted to run a test, I wrote one line that invokes `java`. No more fighting with the defaults. No more “declarative” hints that might or might not do what you need.

Easy things were easy, hard things... well, that’s the thing. We didn’t have any hard things, it’s just a build script!

You might be wondering, what about java-specific tasks?

E.g. how do you download maven dependencies? Well, it’s a simple `urllib.request` (we considered vendoring, too). How do you build a classpath? With string concatenation, `"a.jar" + ":" + "b.jar"`. How do you build a jar? By invoking `jar` tool, the one that comes with JVM. I think at some point we even were zipping files directly with `zipfile.ZipFile`, not sure why we changed. Oh, and we also create `pom.properties`/`pom.xml` in Python on the fly.

Wait, really? Yes, really. It’s not that hard! “Real” Java developers could argue that it’s not a “proper” way to do things because responsibilty, separation of concerns, you are not supposed to write this code, it’s already implemented, yada-yada-yada. But hey, it’s three lines of code, Maven wouldn’t do it any better than my Python script, and the end result is the same. If generating `pom.properties` is a price I have to pay to avoid touching Maven, I am happy to pay!

In retrospect, Python is the obvious choice. It is the perfect glue. It’s scriptable. It doesn’t care what language rest of your system is written in. It’s has all the batteries. It’s fast and cross-platfrom. Google has figured it out long time ago, because (I assume) there’s a lot of gluing in Google. Now I have figured it out, too.

# Conclusion

If you play by common wisdom rules, you get rewarded with integration, support, ease of setup. If you have lots of programmers, or a huge project, you get the benefit of standartization.

But there’s overhead, and the overhead is non-zero. I would argue that for small or non-standard projects it makes more sense to write a few custom scripts rather than investing and fighting with existing build systems that weren’t designed for your use-case. You’ll win some perfromance, too.

As for the best scripting language, please don’t use Bash. I know, it’s tempting, it’s just “two lines of code”. It always starts small until one day it grows into a unportable, unsupportable mess. In fact, Bash is so simple and so natural to just start using that I had to make a very strict rule: never use Bash. Just. Don’t.

But Python is great. Use Python! Or Ruby, Ruby might be good, too. Just, please, don’t use 2.7, it’s not even funny anymore.

If you want, you can take a look at [our scripts](https://github.com/JetBrains/skija/tree/master/script).
