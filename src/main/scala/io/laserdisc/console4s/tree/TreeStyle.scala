package io.laserdisc.console4s.tree

trait TreeStyle {
  val dash: String
  val pipe: String
  val childNode: String
  val childNodeLast: String
  val nodeWidth: Int

  val blank: String = " "
  val empty: String = ""

}

object TreeStyles {

  val Basic: TreeStyle      = BasicTreeStyle()
  val BasicHeavy: TreeStyle = BasicHeavyTreeStyle()
  val DoubleBar: TreeStyle  = DoubleBarTreeStyle()

  // a useful site for discovery - https://www.amp-what.com/

  case class BasicTreeStyle(
      override val dash: String = "─",
      override val pipe: String = "│",
      override val childNode: String = "├",
      override val childNodeLast: String = "└",
      override val nodeWidth: Int = 1
  ) extends TreeStyle

  case class BasicHeavyTreeStyle(
      override val dash: String = "━",
      override val pipe: String = "┃",
      override val childNode: String = "┣",
      override val childNodeLast: String = "┗",
      override val nodeWidth: Int = 1
  ) extends TreeStyle

  case class DoubleBarTreeStyle(
      override val dash: String = "═",
      override val pipe: String = "║",
      override val childNode: String = "╠",
      override val childNodeLast: String = "╚",
      override val nodeWidth: Int = 1
  ) extends TreeStyle

}
