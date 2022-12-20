## Tree Rendering

First, use the wildcard import: 

```scala
import io.laserdisc.console4s.tree._
```
This brings in a `renderTree` function that can render a tree structure as a String

### Basic Example
While you can render your own custom ADTs (more later), you can also just use the simple [`Node`](src/main/scala/io/laserdisc/console4s/tree/Node.scala) case class for simple usage.

```scala
import io.laserdisc.console4s.tree._

// Our basic tree.  
// `Node` is a simple case class with a `value` and an arbitrary number of `Node` children
val exampleTree =
  Node(
    "Root",
    Node("A",
      Node("A1", Node("A1.1"), Node("A1.2")),
      Node("A2")),
    Node("B"),
    Node("C",
      Node("C1")
    )
  )

val tree: String = renderTree(exampleTree)
println(tree)
```

The above code results in the following output:

```
─ Root
  ├─ A
  │  ├─ A1
  │  │  ├─ A1.1
  │  │  └─ A1.2
  │  └─ A2
  ├─ B
  └─ C
     └─ C1

```



### Custom ADTs

If you have a custom [ADT](https://blog.rockthejvm.com/algebraic-data-types/) and don't want to go to the effort of converting to the [`Node`](src/main/scala/io/laserdisc/console4s/tree/Node.scala) case class, you will need to provide an implicit implementation of the [`Traverser`](src/main/scala/io/laserdisc/console4s/tree/Traverser.scala) interface, which requires you provide implementations for:

* `def children(node: T): Seq[T]`  - provide the children of the current node
* `def show(node: T): String` - provide the text label of the node

For example, let's imagine an ADT representing City/State/Country hierarchical data:

```scala
trait GeographicPlace {
  def name: String
}
case class City(override val name: String)                    extends GeographicPlace
case class State(override val name: String, cities: City*)    extends GeographicPlace
case class Country(override val name: String, states: State*) extends GeographicPlace

val Australia: GeographicPlace =
  Country(
    "Australia",
    State("New South Wales", City("Sydney")),
    State("Victoria", City("Melbourne")),
    State("Queensland", City("Brisbane")),
    State("Western Australia", City("Perth")),
    State("South Australia", City("Adelaide")),
    State("Northern Territory", City("Darwin")),
    State("Australian Capital Territory", City("Canberra")),
    State("Tasmania", City("Hobart"))
  )
```

In order to pass `Australia` to the `renderTree` function, we need to provide a `Traverser[GeographicPlace]`:

```scala
implicit val renderer: Traverser[GeographicPlace] = new Traverser[GeographicPlace] {
  override def children(place: GeographicPlace): Seq[GeographicPlace] = place match {
    case c: Country => c.states
    case s: State   => s.cities
    case _          => Seq() // city is our leaf node
  }

  override def show(place: GeographicPlace): String = place.name
}

```

Now if we invoke

```scala
println(renderTree(Australia))
```

We should see:

```
─ Australia
  ├─ New South Wales
  │  └─ Sydney
  ├─ Victoria
  │  └─ Melbourne
  ├─ Queensland
  │  └─ Brisbane
  ├─ Western Australia
  │  └─ Perth
  ├─ South Australia
  │  └─ Adelaide
  ├─ Northern Territory
  │  └─ Darwin
  ├─ Australian Capital Territory
  │  └─ Canberra
  └─ Tasmania
     └─ Hobart
```



### Custom Styling

The `renderTree` function takes a second parameter, providing a [`TreeStyle`](src/main/scala/io/laserdisc/console4s/tree/TreeStyle.scala) 

If not specified, this is `TreeStyles.Basic` which results in the formatting you've seen above.  Taking example 1, let's pass in other examples

```scala
println(renderTree(exampleTree, TreeStyles.BasicHeavy))
/* result:

━ Root
  ┣━ A
  ┃  ┣━ A1
  ┃  ┃  ┣━ A1.1
  ┃  ┃  ┗━ A1.2
  ┃  ┗━ A2
  ┣━ B
  ┗━ C
     ┗━ C1
 */
println(renderTree(exampleTree, TreeStyles.DoubleBar))
/* result:

═ Root
  ╠═ A
  ║  ╠═ A1
  ║  ║  ╠═ A1.1
  ║  ║  ╚═ A1.2
  ║  ╚═ A2
  ╠═ B
  ╚═ C
     ╚═ C1
 */
```

You can pass in your own implementation of [`TreeStyle`](src/main/scala/io/laserdisc/console4s/tree/TreeStyle.scala) 

```scala
val customStyle = new TreeStyle {
  override val dash: String = "-"
  override val pipe: String = "⌇"
  override val childNode: String = "⊕"
  override val childNodeLast: String = "╰"
  override val nodeWidth: Int = 4
}

println(renderTree(exampleTree, customStyle))

/* result:

---- Root
     ⊕---- A
     ⌇     ⊕---- A1
     ⌇     ⌇     ⊕---- A1.1
     ⌇     ⌇     ╰---- A1.2
     ⌇     ╰---- A2
     ⊕---- B
     ╰---- C
           ╰---- C1

*/
```

