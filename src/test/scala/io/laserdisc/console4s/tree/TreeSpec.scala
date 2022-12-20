package io.laserdisc.console4s.tree

import io.laserdisc.console4s.tree.TreeStyles.BasicTreeStyle

class TreeSpec extends munit.FunSuite {

  test("render a single node") {
    val result = renderTree(Node("root node"))
    assertEquals(result, "─ root node\n")
  }

  test("render a node with one child") {

    val result = renderTree(Node("Root", Node("Child")))
    assertEquals(
      result,
      """─ Root
        |  └─ Child
        |""".stripMargin
    )
  }

  test("render a node with multiple children") {

    val result = renderTree(Node("Root", Node("Child A"), Node("Child B")))
    assertEquals(
      result,
      """─ Root
        |  ├─ Child A
        |  └─ Child B
        |""".stripMargin
    )

  }

  val SpringfieldNuclear: Node =
    Node(
      "C. Montgomery Burns",
      Node(
        "Waylon Smithers",
        Node("Lenny Leonard", Node("Inanimate Carbon Rod", Node("Homer Simpson"))),
        Node("Carl Carlson", Node("Mindy Simmons")),
        Node("Frank Grimes")
      )
    )

  test("render nested nodes") {
    assertEquals(
      renderTree(SpringfieldNuclear),
      """─ C. Montgomery Burns
        |  └─ Waylon Smithers
        |     ├─ Lenny Leonard
        |     │  └─ Inanimate Carbon Rod
        |     │     └─ Homer Simpson
        |     ├─ Carl Carlson
        |     │  └─ Mindy Simmons
        |     └─ Frank Grimes
        |""".stripMargin
    )
  }

  test("render custom styles") {
    assertEquals(
      renderTree(SpringfieldNuclear, TreeStyles.BasicHeavy),
      """━ C. Montgomery Burns
        |  ┗━ Waylon Smithers
        |     ┣━ Lenny Leonard
        |     ┃  ┗━ Inanimate Carbon Rod
        |     ┃     ┗━ Homer Simpson
        |     ┣━ Carl Carlson
        |     ┃  ┗━ Mindy Simmons
        |     ┗━ Frank Grimes
        |""".stripMargin
    )

    assertEquals(
      renderTree(SpringfieldNuclear, TreeStyles.DoubleBar),
      """═ C. Montgomery Burns
        |  ╚═ Waylon Smithers
        |     ╠═ Lenny Leonard
        |     ║  ╚═ Inanimate Carbon Rod
        |     ║     ╚═ Homer Simpson
        |     ╠═ Carl Carlson
        |     ║  ╚═ Mindy Simmons
        |     ╚═ Frank Grimes
        |""".stripMargin
    )

  }

  test("render custom widths") {
    assertEquals(
      renderTree(SpringfieldNuclear, BasicTreeStyle(nodeWidth = 5)),
      """───── C. Montgomery Burns
        |      └───── Waylon Smithers
        |             ├───── Lenny Leonard
        |             │      └───── Inanimate Carbon Rod
        |             │             └───── Homer Simpson
        |             ├───── Carl Carlson
        |             │      └───── Mindy Simmons
        |             └───── Frank Grimes
        |""".stripMargin
    )
  }

  test("handle newlines in node names") {

    def mkName(token: String, numNewLines: Int = 0) = s"$token" + 1.to(numNewLines).map(i => s"\n${token}Newline$i").mkString

    val tree = Node(
      mkName("Root"),
      Node(
        mkName("ChildA"),
        Node(mkName("ChildASub1", 3)),
        Node(mkName("ChildASub2"))
      ),
      Node(
        mkName("ChildB"),
        Node(
          mkName("ChildBSub1", 2)
        )
      )
    )

    assertEquals(
      renderTree(tree),
      """─ Root
        |  ├─ ChildA
        |  │  ├─ ChildASub1
        |  │  │  ChildASub1Newline1
        |  │  │  ChildASub1Newline2
        |  │  │  ChildASub1Newline3
        |  │  └─ ChildASub2
        |  └─ ChildB
        |     └─ ChildBSub1
        |        ChildBSub1Newline1
        |        ChildBSub1Newline2
        |""".stripMargin
    )
  }

}
