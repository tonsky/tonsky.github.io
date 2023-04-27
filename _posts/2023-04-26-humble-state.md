---
layout: post
title: "Humble Chronicles: State Management"
category: blog
summary: "Search for the best state management solution for Humble UI"
draft: true
---

Recently I’ve been trying to improve state management and component API in Humble UI. For that, I’ve tried to read and compile all the possible known approaches and synthesize something from them.

I haven’t decided on anything for Humble UI yet, let’s say I’m in an experimenting phase. But I think my notes could be useful to quickly get a birds-eye overview of the field.

This is a compilation of my research so far.

# Object-oriented user interface

Classic UIs like Swing, original native Windows/macOS APIs and the browser’s DOM are all implemented in an OOP manner: components are objects and they have methods: `addChild`, `removeChild`, `render`. That fits so well, actually, that you might think OOP was invented specifically for graphical UIs.

The problem with that approach is code duplication: you have to define both how your initial UI is constructed and how it will change in the future. Also, amount of transitions scales as N² compared to the amount of states, so at some point you either get overwhelmed or miss something.

Nevertheless, OOUIs show great performance, a very straightforward programming model and are widely used everywhere.

<figure><img src="./delphi.png"></figure>

# Humble UI — current state

Given the above, it’s only natural that Humble UI started with the OOP paradigm. Yes, we have stateful widgets and component instances, not functions or classes.

Look, mutable fields and inheritance! In Clojure!

```
(defparent AWrapper [child ^:mut child-rect]
  protocols/IComponent
  (-measure [this ctx cs]
    (when-some [ctx' (protocols/-context this ctx)]
      (core/measure child ctx' cs))))

(core/deftype+ Clip []
  :extends core/AWrapper
  
  protocols/IComponent  
  (-draw [_ ctx rect ^Canvas canvas]
    (canvas/with-canvas canvas
      (canvas/clip-rect canvas rect)
      (core/draw child ctx rect canvas))))
```

These objects can lay out, draw themselves and handle events, but in true Clojure fashion, they can’t be modified.

I mean, theoretically, sure, I could add something like `setChildren` and the like, but what’s the point if we are not going in that direction anyway?

But wait! — you’d say. I’ve certainly seen Humble UI apps modifying themselves!

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./todomvc.mp4" type="video/mp4"></video></figure>

And you’ll be right. There’s a special type of component, `dynamic`, that doesn’t have a fixed list of children. Instead, it takes a function that generates and caches them based on its inputs. When inputs change, old children are thrown away and new ones are generated.

In this example, when `*clicks` changes, a new label will be created and the old one will be thrown away.

```
(def *clicks
  (atom 0))

(def app
  (ui/dynamic _ [clicks @*clicks]
    (ui/label (str "Clicks: " clicks))))
```

You can get quite far with that approach. However, not far enough. The problem with dynamic is that it throws away the entire subtree, no matter which parts have changed. Consider

```
(ui/dynamic _ [p @*p]
  (ui/padding p
    (ui/rect (paint/fill 0xFFF3F3F3)
      (ui/label "Label"))))
```

In this example, only `ui/padding` component needs to be re-created, but its children would be thrown away and re-created, too. Sometimes it can be fixed, actually, by writing it this way:

```
(let [body (ui/rect (paint/fill 0xFFF3F3F3)
             (ui/label "Label"))]
  (ui/dynamic _ [padding @*padding]
    (ui/padding padding
      body)))
```

Remember — components are values, so the `body` reference will stay the same and will be captured by dynamic.

This is both good and bad: it works, but it’s kinda backward (you wouldn’t want to write your UI this way).

It also creates very confusing “owning” semantics. Like, who should “unmount” the `body` in that case? Padding? Dynamic? Neither of them, because `body` technically outlives them both. But then, they have no way of knowing this.

Why is it a problem? Stateful components. If I throw away and re-create the text field, for example, it’ll lose selection, cursor position, scroll position, etc. Not good.

Funny enough, in current implementation text fields ask you to hold their state for them because they have no place to put it reliably.

But overall, we managed to get quite far with this approach and polish some stateful components, so I don’t consider it a waste.

# Declarative UIs

So at some point, programmers decided: we’ve had enough. Enough with OOUI, we don’t want to write

```
window.add(button);
window.show();
```

anymore. We want comfort!

And that’s how declarative UIs were born. The idea is that you describe your UI in some simpler language, bind it to your data, and then the computer goes brrr and displays it somehow.

Well, why not? Sounds good, right?

This is what people have come up with.

## Templates

You describe your UI in XML/PHP/HTML/what have you, sprinkle special instructions on top, give it to a black box and it magically works!

Example: Svelte

```
<script>
  let count = 1;
</script>

<button on:click={handleClick}>
  Count: {count}
</button>
```

The upsides of this approach are that language is probably very simple and very declarative, and also that you can do a lot of optimizations/preprocessing before turning it into UI. Maximum declarativity! 

The downsides are, of course, that you have to work in a second, “not real” language, which is usually less powerful, harder to interop with, and less dynamic.

Probably because of the limitations of templates, the MVVM pattern was born: you prepare your data in a real language and get it into a shape that can be consumed by a simple template.

## Procedural DSLs

You call builder functions in the right context and your UI framework somehow tracks them and turns them into components. Examples would be Dear Imgui or Jetpack Compose:

```
ImGui::Text("Hello, world %d", 123);
if (ImGui::Button("Save"))
    MySaveFunction();
ImGui::InputText("string", buf, IM_ARRAYSIZE(buf));
ImGui::SliderFloat("float", &f, 0.0f, 1.0f);
```

Notice that you don’t explicitly “add” or “return” components anywhere. Instead, just calling builders is enough. Almost procedural style :)

The upside: the code is very compact.

The downside: you can’t work with components as values. Like, can’t put them in an array and reverse it, or take the first 10, or something like that. Your builders become lambdas, and lambdas are opaque: you can’t do much about them except call. Call sites start to matter, where they normally don’t: what looks like a normal program has a few non-obvious gotchas.

## Value-oriented DSLs

In value-oriented DSLs, you return values from your components. Like in React:

```
export default function Button() {
  return (
    <button>I don't do anything</button>
  );
}
```

Notice that you return the button, not call some constructor that adds it to the form. How you get — doesn’t matter. The only thing that matters is the actual value you return. You can do what you want with it: use, ignore, compare, use twice, cache, throw away.

This is also the most natural way to write programs in my opinion: pure functions taking and returning data. It also suits Clojure the best, so we’ll probably want something like that for Humble UI.

Note also that `<button>` syntax, although technically being a DSL, is just a pure convenience. It doesn’t do anything smart, it’s just a more natural way of writing:

```
react.createElement('button', {})
```

which returns a button.

## Declarative-OO duality

Another interesting point is that all declarative UIs work on top of dirty, mutable, old-fashioned OOUI. Flutter has `RenderObject`, for example, and browser UIs utilize and exploit DOM.

If you, like me, ever wondered why didn’t browsers implement React natively somehow, in the same manner they adopted jQuery APIs. Well, the answer is: you kind of need DOM. VDOM sounds cool and fancy as long as all the heavy lifting is done in real DOM.

## Summing up

Declarative UIs are great but require a layer of real mutable widgets underneath. That means it’s all overhead, and all we can do is make it as small as possible.

But we still want declarativity for its developer experience. We want to write more concise code and we don’t want to write update logic.

[As Raph Levien put it](https://raphlinus.github.io/ui/druid/2019/11/22/reactive-ui.html), “industry is fast converging on the reactive approach” (reactive/declarative, nobody knows what these words mean anymore). We are not going to argue with that.

# Reconciliation

In declarative frameworks, each component exists in two forms: a lightweight, user-generated description of it (React elements, Flutter Widgets, SwiftUI Views) and a “heavy” stateful counterpart (DOM nodes, Flutter RenderObjects).

Reconciliation is a process of transforming the former into the latter. This gives up two main sources of overhead on top of OOUI: how much garbage do we generate and how fast can we reconcile it.

# Full top-down reconciliation

The simplest, but probably most wasteful, way is just to regenerate the entire UI _description_ on each frame. This is what Dear ImGui does, for example.

The problem is that then you have to reconcile the whole tree, too. And if some parts of your UI take a lot of time to generate, sorry to be you, you can’t skip them.

Here’s a diagram of the full top-down reconciliation:

<figure><img src="./reconcile_imgui.png"></figure>

## Optimized top-down reconciliation

React updates are also mostly top-down, with two important improvements.

First, when generating and comparing new tree, it has a way to be told “this subtree hasn't changed, I promise” and it’ll skip reconciliation for it altogether:

<figure><img src="./reconcile_react_1.png"></figure>

The second optimization is when you find a specific component and only reconcile its sub-trees:

<figure><img src="./reconcile_react_2.png"></figure>

Unfortunately, these techniques are not applied automatically, so a programmer’s work is required. And even then React adds quite a lot of overhead. The discovery of React team was, though, that this overhead is not important/tolerable in most cases and is a great tradeoff.

In theory, both techniques combined could allow one to update one specific component and nothing else, giving you optimal performance. But it’ll probably be a little bit cumbersome to write.

## Surgical point updates

Why can’t all React updates be surgical, affecting only the element in question and neither its parents nor children? Well, because of data dependencies! Consider this simple UI:

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./conv.mp4" type="video/mp4"></video></figure>

What would it look like in React? Something like this (yes, I asked ChatGPT to write it):

```
function CelsiusInput(props) {
  return (
    <div>
      <input value={props.celsius}
             onChange={props.onChange} />
      Celsius
    </div>
  );
}

function FahrenheitOutput(props) {
  return (
    <div>{props.fahrenheit} Fahrenheit</div>
  );
}

function TemperatureConverter() {
  const [celsius, setCelsius] = useState(0);  
  const handleCelsiusChange = 
    (e) => setCelsius(e.target.value);
  return (
    <div>
      <CelsiusInput
        celsius={celsius}
        onChange={handleCelsiusChange} /> =
      <FahrenheitOutput
        fahrenheit={celsius * 9 / 5 + 32} />
    </div>
  );
}
```

Notice that `CelsiusInput` state is declared in the parent component whose only purpose is to pass it to its sibling, `FahrenheitOutput`. Because of that, not only `CelsiusInput` and `FahrenheitOutput` will have to be re-rendered, but their parent `TemperatureConverter`, too.

Another problem is that, because of the way React is written, the body of `TemperatureConverter` will be re-evaluated, which means, for example, a new instance of `handleCelsiusChange` will be created, even though it’s completely unnecessary.

These are two problems that frameworks like Svelte and SolidJS tried to solve: update as little as possible, only components that need to be updated and nothing more:

<figure><img src="./reconcile_svelte.png"></figure>

[Quoting Ron Minsky](https://signalsandthreads.com/building-a-ui-framework/#1523):

> UIs in general, what are they doing? They are computing something to display to the user and those things need to change quickly. And one of the things that can help them change quickly is they don’t change all at once, little bits of them change. You go and click somewhere and some small part of what you’re seeing changes, it’s not everything in the entire view being transformed all at once.

How do they do it? Well,

> [...] hidden inside of every UI framework is some kind of incrementalization framework as well, because you basically need this incrementalization for performance reasons everywhere.

What’s the incrementalization framework? Imagine you compute a function once, but then are asked to compute it again, with slightly different inputs. If you store some partial computations somewhere and can do the computation faster a second time, if the input hasn’t changed too much, then you have an incremental function.

Now, imagine you build your UI like this: you start with some data sources, like counter “signal” (same as a variable, but reactive). Then you derive some “computables” from it, like “counter squared”, which is, well, counter with square function applied to it. Finally, UI components that display counter state and its square could be further derived from those signals. Something like:

```
const counter      = reactive(0);
const counterLabel = reactive(() => <div>{counter.value}</div>);
const squared      = reactive(() => counter.value ** 2);
const squaredLabel = reactive(() => <div>{squared.value}</div>);
const app          = reactive(() => {
  <div>{counterLabel} * {counterLabel} = {squaredLabel}</div>
});
```

This creates an acyclic dependency graph like this:

<figure><img src="./reactively.png"></figure>

which can be efficiently updated. For example, if we bump `counter`, it will trigger `squared` and `counterLabel` update. `squared` will trigger `squaredLabel` update. Both `counterLabel` and `squaredLabel` could be effects that update their corresponding DOM node, but the return value is the same — they return exactly the same node their work with, so they won’t trigger further updates at all: app structure is static and doesn’t need to be revisited.

This is the simplified and at the same time the most ideal case that we want to at least try to approach in Humble UI.

# State management

UI doesn’t exist in itself. Components on a screen represent some state (business model), but they often also have an internal state to keep track of (scroll position, selection, etc).

In OOUI that wasn’t a problem: you just keep the state inside components. Each component is an object, state is just that object’s fields. As I said — OOP fits UI very nicely. And if the business model changes, well, you go and change your UI with `addNode`/`removeNode`/...

## Internal state

React introduced an interesting paradigm: your component is a function (not an object), but you can request a state inside it and the framework will allocate and track that state for you. Looks a bit weird, but it works:

```
function Counter() {
  const [count, setCount] = useState(0);
  setCount(count + 1);
}
```

The trick here is that `useState` will return exactly the same object over multiple `Counter()` calls if and only if `Counter` component stays in the same place in the tree. That’s called positional memoization. Functions and lightweight descriptions (elements) are always generated anew, but positions in the tree are stable and can have state attached to them.

SwiftUI does the same, but it looks even weirder:

```
struct CounterView: View {
    @State var count = 0
    let id = UUID()
    
    var body: some View {
        Text("ID: \(id.uuidString) Count: \(count)")
    }
}
```

This looks like an object, but you can’t store normal properties inside it. On each render, the framework will create new instances of `CounterView` and any internal fields will be lost, but! It will fill fields marked with `@State` for you each time with the same object. In other words, `id` will be different on each render, but `count` will be exactly the same.

Anyways, SwiftUI approach works too, although I’d argue it’s a bit counter-intuitive.

## External state

External state mostly defines what UI components you need to construct: how many lines are in a list, enabled or disabled button, show warning or not.

### All state external

One approach is to make all state external. For example, Humble UI’s text fields right now ask you to hold their state in your atom. Fun, but can get very tedious, especially when you need to clean up the state for components that are no longer visible.

I think in the early days of ClojureScript frameworks Circle CI (IIRC) frontend’s state could be entirely serialized into a string and re-created on another machine, down do text selection and button hovers. Given that nobody else does this might suggest that it might be an overkill. Cool flex, though.

### Single atom

Another approach is to put all your state in a single atom and only pass down sub-trees of that atom. This is what Om pioneered, and other ClojureScript frameworks adopted as well. This way you can do very cheap pointer comparisons on arguments, it’ll be so cheap it makes sense to do it by default.

Of course, it only works with immutable data, but luckily, ClojureScript data tends to be immutable already. 10 years ago. Good times.

I have three issues with this approach:

1. First, I don’t want to store everything in a tree. I want to have options. I want to use a database.

2. Second, I don’t want to be limited to a single source of truth. I want an ad-hoc state, too. Like a few global atoms that control settings and which I can add/inspect/remove quickly. Why not?

3. Finally, pointer comparisons only work if you pass down sub-trees. If you e.g. `map` or `filter` a collection, the resulting pointer will be new each time and you’ll have re-render, breaking the optimization. 

## State in incremental frameworks

One simplification in incremental compared to React’s prop drilling is that you can safely pass signals through properties, as only components that actually read the value will get re-rendered.

But I’m also thinking about a different angle here: what if components themselves (real ones, heavyweight objects that do layout rendering, not lightweight descriptions) were the output of incremental functions? I mean, they’d stay stable until they need to be changed, right?

<figure><img src="./reconcile_incremental.png"></figure>

That would mean that we can keep the state inside them naturally, and we won’t need any VDOM at all, just incremental computations.

This is just a theory at this point, but I am building a proof of concept to see where it goes. Subscribe for updates!

# Component DSL

The way we write components is important: it must be ergonomic.

For example, in [Flutter architectural overview](https://docs.flutter.dev/resources/architectural-overview) they call this example trivial:

```
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('My Home Page'),
        ),
        body: Center(
          child: Builder(
            builder: (context) {
              return Column(
                children: [
                  const Text('Hello World'),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: () {
                      print('Click!');
                    },
                    child: const Text('A button'),
                  ),
                ],
              );
            },
          ),
        ),
      ),
    );
  }
}
```

But to my eye, it reads way harder than it should. Same but in Clojure + Hiccup:

```
[MaterialApp
 [Scaffold
  {:appBar
   [AppBar
    {:title [Text "My Home Page"]}]}
  [Center
   [Column
    [Text "Hello World"]
    [SizedBox {:height 20}]
    [ElevatedButton
     {:onPresed #(print "Click!")}
     [Text "A button"]]]]]]
```

Now you can finally see what’s going on!

Another important thing is that the way you write your components might impose unnecessary dependencies or other semantics. We don’t want to make users choose between fast and readable, we want them to have both.

## Parent → child dependencies

Let’s look at React example again:

```
function TemperatureConverter() {
  const [celsius, setCelsius] = useState(0);  
  const handleCelsiusChange =
    (e) => setCelsius(e.target.value);
  return (
    <div>
      <CelsiusInput
        celsius={celsius}
        onChange={handleCelsiusChange} /> =
      <FahrenheitOutput
        fahrenheit={celsius * 9 / 5 + 32} />
    </div>
  );
}
```

See the problem? By being plain JavaScript and plain function, if `TemperatureConverter` needs to be re-evaluated, the only thing to do here is to call the entire function. Meaning, do all the calculations again, allocate new objects and new functions again. Then diffing kicks in, trying to figure out which objects are the same and which are different. That’s a bit too much work than necessary, but React design forces us to do it.

Let’s look at another example, in Humble UI this time:

```
(ui/vscrollbar
  (ui/column
    (ui/label @*count)
    (ui/button #(swap! *count)
      "Increment")))
```

By the way it’s constructed, `column` depends on both `label` and `button`, and `scrollbar` depends on `column`.

This parent-children dependency comes naturally from evaluation order, but do we really want it? For example, if `*count` changes, we do want a new label to be created, or an old one to change its text? Ideally, we would also like to keep `column`, `button` and `scroll`—they might have important inner state, e.g. scroll position.

## Child → parent dependencies

Another example (pseudo-code):

```
(defcomp child []
  (ui/dynamic ctx [{:keys [accent-color]} ctx]
    (ui/fill accent-color
      (ui/label "Hello"))))

(defcomp parent []
  (ui/with-context [:accent-color 0x1EA0F2]
    (ui/center
      (child))))

(parent)
```

I guess the point here is that parent creates a child (and could do it conditionally, in a loop, or in any other non-trivial way), so it kind of depends on the child (at least it defines it). But at the same time, the child it creates depends on a property defined by a parent, too.

Again, maybe it’s not a problem if each component would be a macro that evaluates its children separately from itself, but still tricky to think about.

Think of it this way: some data flows from top to bottom, defining which components should be created. Then changes flow from leaves back to top. Sometimes change in a signal might invalidate both a leaf and its parent, and that parent might decide not to re-create said leaf! Sounds like a recipe for disaster.

## Component persistence

One more example:

```
(if @*errors
  (ui/border 0xFF0000
    (ui/text-field @*state))
  (ui/border 0xCCCCCC
    (ui/text-field @*state)))
```

Should these two text fields be different or the same? I mean, in our example we want them to be the same, but by construction, they are different _object instances_.

In React they will be the same, because React only cares about what you return and reconciles values, ignoring e.g. positional information or object instances.

But then, we can trick React, too, to re-create text field:

```
(if @*errors
  (ui/border 0xFF0000
    (ui/text-field @*state))
  (ui/text-field @*state))
```

Now, because the return structure is different, React will drop the previous instance of the text field and create a new one, even if we don’t want it. It’ll drop the state, too. If I understand this correctly, even keys wouldn’t help us in this case (Flutter seems to have `GlobalKey` for cases like this, though). 

When operating on heavy-weight components directly, we can do this transformation:

```
(let [text-field (ui/text-field @*state)]
  (if @*errors
    (ui/border 0xFF0000
      text-field)
    text-field))
```

So it feels like this approach is a little bit more capable? I’m not sure how well it converts into that incremental dream, by the way.

# Live reload

React plays very well with live reload because it does its thing in runtime. Basically, it expects nothing from you in advance and because of that can do all sorts of crazy stuff without reloading/recompiling/etc. E.g. I can define a new component and mount it into an existing tree in runtime and not lose any state in other components.

This property of React is very appealing. I’m not sure how development experience is with incremental frameworks that require additional compilation, but I assume it’s more complicated.

Full restart is also an option, as long as it happens in the same JVM and allows you to keep at least an extrernal state intact. Luckily, Clojure works well for that.

For example, after a certain threshold I switched from buffer evals for reload to full `tools.namespace` nuke & load because manual buffer evals were becoming too complex:

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="./reload.mp4" type="video/mp4"></video></figure>

This unloads the namespace, loads it back, creates all new hierarchy of components, etc. All faster than a single frame on a 144 Hz monitor. Things our computers can do if they don’t have to run Xcode!

Also, this approach should be compatible even with templates/preprocessing and it still gives you close-to-zero turnaround times and state persistence.

# Conclusion

Looks like our industry has converged on the following approaches:

- OOUI (old classics)
- VDOM (“declarative”/“reactive” UI frameworks)

Where VDOM could be implemented with:

- Templates
- Call-site positioning
- Return values

And data organization:

- Top-down props drilling
- Reactivity
- Incremental computations (reactivity on steroids)

I’m leaning towards VDOM + return values + incremental computations for Humble UI, but will have to run a few experiments to see how it feels and performs.

Also, I hope people will find this article by searching for “React vs Svelte”. It’ll be so funny.

Overall, developing a UI framework is so interesting, I’m learning so much. You should try it one day, too.

Until that, take care. See you next time!