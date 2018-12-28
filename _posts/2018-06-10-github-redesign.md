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

What if I told you we can make Github header twice as small, or display more content on a repository page, or make everything cleaner and more streamlined, or easier to use? What if we can do all four at the same time?

Today we’ll look into problems on Github repository page and see how to fix them. Let’s take [a typical repository](https://github.com/github/VisualStudio): active, rather popular, has all the features enabled (being developed by Github, it even has found use for Topics and Projects). As of June 2018, repository page looks like this:

<figure class="w768"><img src="./10_as-is.png" /></figure>

_/if you’re curious like me, this is how it looked [before 2015 redesign](https://blog.github.com/2015-11-16-a-new-look-for-repositories/) and [before 2013](https://blog.github.com/2013-06-17-repository-next/)/_

## Unifying tabs

Let’s start with the biggest issue right away: information architecture. Take tabs. Currently there’re two levels of tabs, one nested under the other:

<figure class="w768"><img src="./20_tabs.png" /></figure>

This is bad for two reasons. First, people (unlike coders) don’t like hierarchies that much. Nested structures are hard to remember, navigate, and grouping is very often non-intuitive. Flat is always preferable.

Second, it makes important links inaccessible directly: if I’m in Wiki tab and need to see Releases, what do I do? There’s no Releases tab visible, so I must figure out somehow that Releases are part of Code (?) which makes almost no sense. Releases are as much part of the Code as Issues or Wiki are.

So the solution here is to unify and flatten all tabs in a single navigational control. Here:

<figure class="w768"><img src="./30_tabs-merged.png" /></figure>

Organized that way, tabs are immediately accessible _from anywhere_ in the repo. _This is a big deal_. Also look at how much vertical space we’re winning! Impressive, but we can do even better.

We still have one problem though: tabs don’t fit. We’re going to solve it by removing icons, but let me build up a case for that first.

## Removing icons

Icons are visual cues to help you scan the UI quickly, where “quickly” means faster than reading text labels. If it’s faster to read labels then icons aren’t working.

E.g. if you put too many icons in a row and they are _all_ different, they won’t work: you can’t _highlight everything_. Things can only stand out if there’re just a few of them:

<figure><img src="./35_icons.png" /></figure>

Then, if you use obscure graphics, people will have to read labels anyways, so, again, not working. And let’s be honest: the domain of repository and project management is pretty abstract. No matter how good you are at design and how hard you try, you won’t come up with a great icon for a commit. Or a release. Or an issue. Or a license. There’s simply none. I mean,

<figure><img src="./40_is-this-a-commit.png" /></figure>

So Github tab icons are purely decorative. If you don’t believe me, see for yourself. They’re dimmed compared to the text label:

<figure><img src="./45_commits.png" /></figure>

Github decided they’ll put less visual weight on them because they think, too, that it’s near impossible to figure out why backwards clock means commit. Even they think you’ll be looking at the label anyways.

But even being decorative, they’re bad at it. I mean, Icon + Label + Counter make for a symmetric and weak composition:

<figure><img src="./50_composition.png" /></figure>

So by removing icons we a) win lots of space, b) make design stronger, and c) clean up lots of visual noize. Win-win-win! Here’s the result:

<figure class="w768"><img src="./60_icons-removed.png" /></figure>

I had to remove Contributors tab though (which was actually not a tab, but a sub-tab from Insights that was duplicated in Code for some reason) to accomodate for Settings tab. Don’t worry, we’ll get Contributors back later.

And if you’re worried if there’s enough space to grow, Github is already locked at 1020px width layout and English language only, so if it fits now then it fits.

## Vanity counters

This is the “vanity menu”:

<figure class="w400"><img src="./70_vanity.png" /></figure>

The thing with vanity metrics is that there should only be one. One metrics is simple to understand and focus. Two or three split attention, making everything weaker.

Luckily for us, both watchers and forks perform poorly as metrics anyways. For watches, people tend not to watch too much. For forks, people tend to fork for no reason.

But stars work. They work because they have no other function but to represent vanity. So let’s keep stars and move them to get more attention:

<figure class="w400"><img src="./73_vanity.png" /></figure>

## Fixing “Watch” button/dropdown

In watch button we have a classic “button or status” UX dilemma. The label reads “Watch” but means you’re not watching, the label “Unwatch” means you _are_ watching, and there’s third status, “Ignoring”, which should be indicated with Watch button too but then current status would be unclear... So they invented “Stop ignoring” button. Anyway it’s a mess:

<figure class="w768"><img src="./75_vanity.png" /></figure>

Even worse, it’s a completely unnecessary mess. We _already have_ a clear and well-established semantics of how buttons and dropdowns are supposed to work: buttons show action but can’t represent status, dropdows show current status _and_ can be used to change it at the same time.

Funny, but Github is already using a dropdown! We just need to fix it to work as any other dropdown on Earth works: to show current status. Trivial fix, really:

<figure class="w500"><img src="./77_vanity.png" /></figure>

Minor, but annoying: that checkmark is really hard to spot. Let’s highlight selected option:

<figure class="w500"><img src="./78_vanity.png" /></figure>

All together so far:

<figure class="w768"><img src="./80_vanity-reorganized.png" /></figure>

Uff, we’ve done a lot! If you need a little break, now would be a perfect time for it. Don’t worry, I’ll wait.

## Moving description

Back? Great! Let’s turn our attention to the reposiory description now:

<figure class="w768"><img src="./90_details.png" /></figure>

Currently it’s located inside Code tab, which is inconsistent: it’s a description of the whole repository, not just its code. We’ll move it above the tabs where it makes sense, right next to the repository name:

<figure class="w768"><img src="./100_details-moved.png" /></figure>

It still needs couple of touches. First, font is neither big nor small (it’s 16px, standing between 18px repository name and 14px tabs). Let’s make it 14px so that there’re only two distinct font sizes:

<figure class="w768"><img src="./112_details.png" /></figure>

_/also I’m sure there’s no need to render “https://” in the URL/_

The second change is moving topics to the same line as description. There’re usually just a few of those, so it seems wasteful to spend a whole line on them:

<figure class="w768"><img src="./114_details.png" /></figure>

Look at us, winning more vertical space!

## Removing background

Blue topics on blue background became harder to read. This is because they used to be on #FFF and now they’re on Github’s #FAFBFC “very light dirty blue”.

Let’s change tab’s background to #FFF too:

<figure class="w768"><img src="./116_details.png" /></figure>

A bit of rationale behind old “toned down” tabs background and new pure white. My guess is Github had to add it because the structure of their menus layers was becoming too complex and they needed visual cues to help you “split” it in order to understand. Top black bar serves the same purpose: to separate. I mean, they were literally spending good half of 768px screen at navigation alone.

So in the old Github the color was absolutely necessary and cannot be removed. Old navigation without a background would be a mess:

<figure class="w768"><img src="./117_details.png" /></figure>

But because we simplified whole header so much and removed one intermediate layer, we don’t need that color coding anymore. Instead, we can enjoy fresh crisp white:

<figure class="w768"><img src="./119_details.png" /></figure>

And look at all that free space! Pretty impressive, don’t you think?

## Streamlining description editing

A small touch. If you own the repository, you get to edit both description and topics:

<figure class="w768"><img src="./120_details.png" /></figure>

Firstly, “Edit” button is badly misplaced — too far, too easy to miss. Secondly, separate edit button for topics is an artefact of topics system being developed at different time, maybe by a different team.

We can easily replace them both with contextually placed single button that switches _everything_ into edit mode at once and stays small and unobtrusive rest of the time. After all, you edit those once, maybe twice per the lifetime of the repo. No need for so many big buttons where small icon would do:

<figure class="w768"><img src="./122_details.png" /></figure>

## Compacting code browser

The code browser is that rectangular are that has a list of all your reposiory files. We can’t get rid of it or move it to another, less noticeable place. Github IS about files, and they must have the front seat. This part is different though:

<figure class="w768"><img src="./130_code_browser.png" /></figure>

The traditional File Explorer (or Finder) have established simple pattern: files on the left, details on the right:

<figure class="w768"><img src="./132_file_explorer.png" /></figure>

In Windows it kind of made sense, but only as long as you care about those details. And since Date modified, or File type, or Size are occasionaly needed, the whole UI wasn’t wasted: there are situations where you need to find a particulary big file, or something recently changed, or something on a specific date.

Enter Github. Those aren’t your files stored on your hard drive, so you’re not particulary interested in their size, or type, or date modified. You might be interested in a repo as a whole and sometimes in contents of individual files. So Github had to figure out what to put in those columns. Not filling those would mean too much wasted space.

So they put info about last commit there. That filled the space, but was pretty useless: commits often touch files for completely arbitrary reasons, so the date tells you almost nothing. The commit message is even more confusing: it looks like a description of a file, but it _isn’t_. “Tweak layout” is not a description to the “Docs” folder. “Need these debug tools too” for “Tools”—why should I care?

Just a lists of recent commits would be much, much more useful:

<figure class="w768"><img src="./140_code_commits.png" /></figure>

You can now see if project is actively developed, maintained or abandoned, if issue you care about was recently fixed, if new version was recently published etc.

What about that free space on the right? Well, we can put useful stats there:

<figure class="w768"><img src="./150_lang_stats.png" /></figure>

Language statistics was unfairly hidden away, now it’s front and foremost. I also added code size in lines of code — an obvious addition that Github misses for some reason.

Top 4 contributors are rendered as avatars, rest is mentioned as a total number. This is important: people and their contributions make Github tick, so it’s only fair if we see their faces.

License might be important if you consider using the project.

Activity graph was a great late addition to Github, but for some reason inaccessible from the repository page.

All together, in my opinion, makes this particular page much more practical in everyday use.