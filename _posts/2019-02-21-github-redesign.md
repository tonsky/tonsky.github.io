---
layout: post
title: "Redesigning Github repository page"
category: blog
summary: "Better design for Github repository page"
draft: true
---

<style>
.w768 { width: 768px; margin-left: -112px; margin-right: -112px; }
.w500 { width: 500px; margin-left: 22px; margin-right: 22px; }
.w400 { width: 400px; margin-left: 72px; margin-right: 72px; }
</style>

Github design is pretty good: it gets the job done, it’s clean, has consistent visual language, its design is calm and suitable for everyday use.

Given all that, there are still many areas that could be improved even further. Today we’ll take one interface—repository page—and look what UI problems it has and if we can fix them.

<figure class="w768"><img src="./10_as-is.png" /></figure>

Visual Studio GitHub extension is a typical repository: active, popular, uses all the features. Being developed by Github, it even has found use for Topics and Projects.

## First problem: nested tabs

Let’s start with the biggest issue right away: information architecture. Take tabs. Currently there’re two levels of tabs, one nested under the other:

<figure class="w768"><img src="./20_tabs.png" /></figure>

If you are a programmer, you might be surprised but other people normally don’t like hierarchies. Nested structures are hard to grasp, remember, navigate, and grouping is very often non-intuitive. Nested tabs is one of the worst UI patterns out there.

Then there’s a plain usability issue: let’s say I’m in Wiki and need to see Releases. What should I do? There’s no Releases tab visible, so I must figure out somehow that Releases are part of Code (?) which makes almost no sense. Releases are as much part of the Code as Issues or Wiki are.

Solution here is to flatten all tabs into a single navigational control:

<figure class="w768"><img src="./30_tabs-merged.png" /></figure>

Organized that way, tabs are immediately accessible _from anywhere_ in the repo. _This is a big deal_.

As a bonus, we also won quite a bit of vertical space without sacrificing anything! Vertical space is also very important, as it lets you get to content faster and see more of it on a single screen.

We still have one problem though: tabs don’t fit. We’re going to solve it by removing icons, but let me build up a case for that first.

## Problem 2: Redundant icons

Icons are visual cues that help you scan the UI quickly, where “quickly” means faster than reading text labels. If, for some reason, reading labels is still faster then icons aren’t working.

One example of icons misuse: if you put too many icons in a row and they are _all_ different, they won’t work.

<figure><img src="./35_icons.png" /></figure>

Prioritizing, or highlighting, something means deprioritizing everything else. You can’t highlight everything.

Another way to fail at icons: if you use obscure graphics, people will have to read labels anyways, so, again, not working.

<figure class="w768"><img src="./37_obscure_icons.jpeg" /></figure>

Now let’s be honest: the domain of repository and project management is pretty abstract. No matter how good you are at design and how hard you try, you won’t come up with a great icon for a commit. Or a release. Or an issue. Or a license. Great icon is something _other_ people know and understand. And for repositories there’s simply none. I mean,

<figure><img src="./40_is-this-a-commit.png" /></figure>

So Github tab icons are purely decorative. If you don’t believe me, look at what Github itself is doing. They dim them:

<figure><img src="./45_commits.png" /></figure>

That’s a sure sign that they, too, think it’s near impossible to figure out why backwards clock means commit. Github knows people will be looking at the labels anyways.

But even being decorative, they’re bad at it. I mean, Icon + Label + Counter make for a symmetric and weak composition:

<figure><img src="./50_composition.png" /></figure>

By removing icons we:

1. win lots of space,
2. make design stronger, and
3. clean up lots of visual noize.

Win-win-win! Here’s the result, tabs without icons:

<figure class="w768"><img src="./60_icons-removed.png" /></figure>

Test yourself, see if you can find Releases tab without an icon and if it was harder than before? It wasn’t, was it?

A note on this design: I am using the same limitations Github already uses: English language only, locked page width of 1020px. For different conditions, say, for adaptive design we might want a different solution.

Another note: I had to remove Contributors tab (which was actually not a tab, but a sub-tab from Insights that was duplicated in Code for some reason) to accommodate for Settings tab. Don’t worry, we’ll get Contributors back later.

## Problem 3: Vanity counters

This is the “vanity menu”:

<figure class="w400"><img src="./70_vanity.png" /></figure>

The thing with vanity metrics is that there should be just one. One metrics is simple to understand and focus. Two or three split attention, making everything weaker.

Luckily for us, both watchers and forks perform poorly as metrics anyways. For watches, people tend not to watch too much. For forks, people tend to fork for no reason.

But stars work. They work because they have no other function but to represent vanity. So let’s keep stars and make them first in a row to get more attention:

<figure class="w400"><img src="./73_vanity.png" /></figure>

## Problem 4: “Watch” button ambiguity

In watch button we have a classic “button or status” UX dilemma. Buttons say what can be done but don’t say what current status is. Status say current state but it’s not clear what it would change to.

In Github case “Watch” has a button label but it doesn’t act as a button: clicking on it won’t make you watch the repo. It’s not a status either: if you see “Watch” it means you are _not_ watching. Same for other two states: they are neither buttons nor states.

<figure class="w768"><img src="./75_vanity.png" /></figure>

The problem here is that single button can’t be used to switch between three states. But dropdown can! Dropdowns are a well-established UI component that shows status _and_ can be used to change it at the same time.

Funny, but Github is already using a dropdown! We just need to fix it to work as any other dropdown on Earth works: to show current status. Trivial fix, really:

<figure class="w500"><img src="./77_vanity.png" /></figure>

Minor, but annoying: that checkmark is really hard to spot. Let’s highlight the whole row:

<figure class="w500"><img src="./78_vanity.png" /></figure>

All together so far:

<figure class="w768"><img src="./80_vanity-reorganized.png" /></figure>

Uff, we’ve done a lot! If you need a little break, now would be a perfect time for it. Don’t worry, I’ll wait.

Back? Great! Let’s move on.

## Problem 5: Repo description

This is the repository description:

<figure class="w768"><img src="./90_details.png" /></figure>

The problem with it is: why is it located under the Code tab? It’s a description of the whole repository, not just its code, right?

To fix this let’s move it above the tabs, to the are that belongs to the whole repository:

<figure class="w768"><img src="./100_details-moved.png" /></figure>

It still needs a couple of touches.

First, font is neither big nor small (it’s 16px, standing between 18px repository name and 14px tabs). Let’s make it 14px so that there’re only two distinct font sizes. Also I’m sure there’s no need to render “https://” in the URL:

<figure class="w768"><img src="./112_details.png" /></figure>

The second change is to move topics to the same line as description. There’re usually just a few of those, so it seems wasteful to spend a whole line on them:

<figure class="w768"><img src="./114_details.png" /></figure>

Look at us, winning more vertical space again!

## Problem 6: Removing background color

Blue topics on blue background became harder to read. This is because they used to be on #FFF and now they’re on Github’s #FAFBFC “very light dirty blue”.

Let’s change tab’s background to #FFF too:

<figure class="w768"><img src="./115_details.png" /></figure>

But why was that background there in the first place? What did it do?

My guess is Github had to add it because the structure of their menus layers was becoming too complex and they needed visual cues to help you “split” it in order to understand. Top black bar serves the same purpose: to separate. I mean, they were literally spending good half of 768px screen at navigation alone.

<figure class="w768"><img src="./116_details.png" /></figure>

When split in layers, at least you are not immediately scared by it. Without a background it would be a mess.

But because we simplified whole header so much and removed one intermediate layer, we don’t need that color coding anymore. Instead, we can enjoy fresh crisp white:

<figure class="w768"><img src="./119_details.png" /></figure>

And look at all that free space! Finally we can see some content in the top half of the screen too.

## Problem 7: Description editing

A small touch. If you own the repository, you get to edit both its description and its topics:

<figure class="w768"><img src="./120_details.png" /></figure>

First, “Edit” button is badly misplaced — too far, easy to miss.

Second, separate edit button for topics is an artefact of topics system being developed at different time, maybe by a different team.

We can easily replace them both with contextually placed single button that switches _everything_ into edit mode at once and stays small and unobtrusive rest of the time. After all, you edit those once, maybe twice per the lifetime of the repo. No need for so many big buttons where small icon would do:

<figure class="w768"><img src="./122_details.png" /></figure>

## Problem 8: Files description

The traditional Windows File Explorer, together with macOS Finder, have established a simple pattern for file browsing: files on the left, details on the right.

<figure class="w768"><img src="./132_file_explorer.png" /></figure>

Github decided to copy that pattern, but they put a very unexpected thing as details: a message from the last commit when that particular file was changed.

<figure class="w768"><img src="./130_code_browser.png" /></figure>

Why? I don’t know. Commits often touch files for completely arbitrary reasons, so the last commit tells you almost nothing. I can’t think of any case when somebody would need that particular information.

Maybe they wanted Github to be “about git” more than about traditional file browsing, and this was the only thing they could  think of? That’s my speculation, anyways.

The problem with those details is not that such a huge area is filled with something so rarely useful. The problem is that it looks _so much_ like file description it confuses me every time. “Tweak layout” is not a description of the “Docs” folder. “Need these debug tools too” for “Tools”—why should I care?

That means we can get rid of the descriptions without really losing anything:

<figure class="w768"><img src="./135_no_description.png" /></figure>

## Problem 9: Repository overview

When you get to the repository, the first page you see should not necessarily be Code. We better call it Overview—something to get a quick idea of what’s going on.

Now, Github repository is more than just a list of files. It’s also about how those files change over time. What new features were added? Which bugs were fixed? Were there any new releases recently? All those are as important as the files themselves.

That’s why I suggest we add list of recent commits to the Overview page next to files.

<figure class="w768"><img src="./140_code_commits.png" /></figure>

You can now see if project is actively developed, maintained or abandoned, if issue you care about was recently fixed, if new version was recently published etc (notice tags and branches pills in commits list—I miss this feature on Commits tab SO MUCH).

What about that free space on the right? We can put useful stats there:

<figure class="w768"><img src="./150_lang_stats.png" /></figure>

First, I added contributors. Github is all about people and collaboration, that’s why they put so much emphasis on Fork and PR buttons. Well, people are the face of that collaboration. It’s only fair to see some of their faces.

Activity is a relatively new feature that haven’t found its place on repository page yet—until now. Helps you see how much momentum the project has.

Language statistics was unfairly hidden away, now it’s front and foremost. I also added code size in lines of code — an obvious addition that Github still doesn’t have for some reason.

All together, in my opinion, new Overview tab is more helpful in everyday use.

## Problem 10: Contextualizing buttons

Both “Create new file”, “Upload files” and “Find files” all relate to files, so it will be reasonable to move them right next to the thing they operate on: Files.

<figure class="w768"><img src="./160_files_buttons.png" /></figure>

## Problem 11: Hidden clone link

Github always used to have clone URL directly on a page. That was pretty useful—when you needed to grab the code, no additional clicks were required.

<figure class="w768"><img src="./165_clone.png" /></figure>

Unfortunately, during one of the redesigns Github hide clone URL behind a button. My guess is they did it because there was simply not enough space. And because people kept looking for it they painted the button green.

Well, after moving file buttons we have enough space to restore full-length Clone link:

<figure class="w768"><img src="./170_clone.png" /></figure>

I also unified all four ways to get the repo into a single control, because semantically they are pretty much the same: a way to get code on your machine.

## Last problem: dated look

This is how Github [looked back in 2015](https://blog.github.com/2015-11-16-a-new-look-for-repositories/):

<figure class="w768"><img src="./180_github_2015.png" /></figure>

And this is a screenshot [from 2013](https://blog.github.com/2013-06-17-repository-next/):

<figure class="w768"><img src="./190_github_2013.jpg" /></figure>

As you can see, color scheme and main UI elements hasn’t changed much since then. It’s not necessarily bad and mostly proves that the original design was good enough to stand the test of time.

If we want to update the design, we should be careful not to lose qualities that make it tick: clearly distinguishable elements, calm muted colors, perfect for long professional use. It doesn’t try to win your heart on the first glance, but with prolonged use you start to appreciate how it makes emphasis on the content and never gets in your way.

...TBD...