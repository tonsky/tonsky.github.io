---
layout: post
title: "JavaScript v. backward compatibility"
category: blog
summary: "For some unclear reason, many JS developers are opposed to the idea of backward compatibility"
img_rel: true
---

> Inside the carton is a push-button unit fastened to a small wooden box. A glass dome covers the button. If you push the button, somewhere in the world someone you don’t know will die. In return you will receive a payment of $50,000. It could be anyone. All we guarantee is that you don’t know them. And, of course, that you wouldn’t have to watch them die. For $50,000.

The question is: would you push that button? For $50,000? For $1M? If you read something like that in a short story or see it in a movie, you’d know there’s always a catch somewhere. Yet in a real world many JavaScript developers seem eager to push the button. Even if there’s no money. They’d just push the button over and over with no reward in return.

<figure><img src="http://tonsky.me/blog/compatibility/warnings.png" /></figure>

Well, it’s not exactly someone would die, of course. Let me correct:

> Inside the carton is a push-button unit fastened to a small wooden box. A glass dome covers the button. If you push the button, somewhere on the Internet some websites would break. It could be any site. It would have real users, real people using them, relying on them. But we guarantee it wouldn’t be your site. In return you will receive a convenient method that you could’ve implemented in 20 lines of code or imported from a library. 

Is that a good price to pay? Are you willing to pay it? Does it matter what you’ll get in return? I deliberately don’t link the issue because it’s trivial. Does it still worth breaking part of the Internet?

If your answer is “yes”, I have bad news for you. Let me rephrase: you can get X in a backward-compatible, clean and safe way right now, with no downsides, but you still want it to be implemented in a way that breaks live websites with live users. Really?

<figure><img src="http://tonsky.me/blog/compatibility/goodbye.png" /></figure>

I’m not sure why JavaScript developers are so opposed to the idea of backward compatibility. Imagine you come to a doctor and she says: we can put your arm in plaster and it’ll heal itself, will be as good as new, or we can cut it off. Apparently, there’re lots of JavaScript developers who would choose to cut off the hand. More specifically, they would choose to cut off someone else’s hand. And there’s nothing wrong with theirs, they just like chopping off other people’s hands. Because that’s how they see language evolution should happen.

<figure><img src="http://tonsky.me/blog/compatibility/progress.png" /></figure>

Isn’t it schizophrenic to think that language specification is permanent but websites built with it aren’t?

<figure><img src="http://tonsky.me/blog/compatibility/permanent.png" /></figure>

Maybe you just need experience to be able to see outside of your bubble. Or even to realize there’s a bubble. If you’re 22 years old web-developer who changes jobs every 18 months, everything older than 2 years seems like old crap nobody cares about, because you only talk to your friends who are also 22 years old and they don’t care so obviously nobody does. You’ve heard scary stories about IE 6, that it was terrible and didn’t support grid layout and obviously people were way stupider back then if they couldn’t figure out grid layout so why would anyone care about that old crap anyways? Anything but move fast and break things is degeneracy, oblivion, and death.

<figure><img src="http://tonsky.me/blog/compatibility/degeneracy.png" /></figure>

Let’s stop here for a moment. I always wanted to understand: what, in your opinion, is wrong with Java anyways? Last time I checked it was twice as fast as JavaScript, had static typing, dynamic linking, real multithreading, vast standard library, reliable package manager, tons of languages to compile from (including dynamic ones and yes, JS), ~100ms startup time (same as Node), perfect record of backward compatibility, language changes and API additions every major release, huge internal optimizations yet somehow it manages not to break its APIs and not to piss every developer on the planet. What’s wrong with that? A bit boring, not enough drama? Relationship not abusive enough? Well, if you call that degeneracy, I choose degeneracy.

Reality check: Java does not have a bad reputation. JavaScript does. People laugh at JavaScript, its ecosystem, how it handles things and how childish their problems are. No, I’m not a Java developer, but I wish I could write websites in Java. Would’ve be at least twice as fast.

Backward compatibility is not always hard to pull off, by the way. Changing behavior is hard. Removing stuff is hard. But adding stuff with 100% guaranteed backward compatibility? Seriously, that’s easy _anywhere_ but in JS land.

<figure><img src="http://tonsky.me/blog/compatibility/adding.png" /></figure>

I mean, how many experts do you need to understand that IF you can’t put stuff on prototypes in a backward-compatible way, either in libraries or language specification, THEN the solution is NOT to continue to do so. Stop. Just STOP. There’re other ways to extend, you know?

But why should _you_, the developer, care? Isn’t it just a problem of those old websites? Can’t they _just_ keep up? Can’t _they_ alter their websites every time language specification changes? 

<figure><img src="http://tonsky.me/blog/compatibility/developers.png" /></figure>

Hmm, let me think. Can you? Yes, _you_, you personally. Imagine every employer you ever worked for suddenly shows up at your door and asks you to update every website you’ve ever built and redo it with the grid and flexbox and adaptive pictures and whatnot. Ok I guess this example doesn’t sound so scary to 22-year-olds, but imagine the same thing happens in eight years from now and they (every employer you had up to that moment, which would be _a lot_) ask you to implement constraint layout or whatever shit will be popular at the moment. Also imagine it’ll keep happening until the end of your life, with 6-12 month intervals, because shit keeps changing constantly. Sounds realistic?

That’s why we have backward compatibility. So situations like that don’t happen. You don’t expect publishers of XVI century books to reprint all their books every time language norms change, do you? Even if they wanted to, most of them are dead by now.

<figure><img src="http://tonsky.me/blog/compatibility/publishers.png" /></figure>

Maybe it’s a question of perspective. If websites are just job for you, it ends when you get paid. Not much to care about in that case. But for the rest of us, for people who’re actually using the Internet, including (probably) your website, including (probably) you, we see it as an informational resource, a data network for the whole Earth for all future generations. And yes, we’re pissed off when stuff doesn’t work. And no, it’s not our problem, and it’s not website developer’s problem, it’s a fail of engineering community as a whole if you can’t figure out how to make things that last at least 8 years.

Just EIGHT YEARS! I can read books that are _centuries_ old just fine. Inception came out 8 years ago. Imagine you couldn’t watch Inception today because of some technical mumbo-jumbo?

It’s _your_ personal responsibility to build a technology that doesn’t break every time wind changes. Get back to the drawing board and start thinking. Stuff ain’t that hard. It’s been done before. And no, “every developer, live or dead, should sit tight and update every website they have ever built until the end of times” is not an answer.

## UPD: Emergency update

People seem to misread what I’m saying and still try to make it about MooTools and how they are the bad guys here. They are not. In fact, it’s irreleveant. It’s not about MooTools, how old it is or how unpopular it is. If there existed just one website that had just one line of hand-crafted JS code that would break with that update it’s totally not worth it. Because there’re clean ways to extend the language, and there’re dirty ways, and dirty ones have no benefits. Why choose them if you can make _everyone_ happy?

JS standard lets developers put stuff on prototypes, that’s game over, NO LANGUAGE UPDATE should EVER put stuff there, period. It’s not MooTools who should stop doing that, it’s TC39. Because stuff will break. It’s a breaking change. Nobody is in control of this situation. Given that you can get _exactly_ the same results without the breaking change, why break? Pick a different way, make sure you’re in control, make sure it’s safe, and _then_ start evolving JS.