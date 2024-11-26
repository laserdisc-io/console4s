package io.laserdisc.console4s

package object tree {

  def renderTree[T](root: T, config: TreeStyle = TreeStyles.Basic)(implicit nc: Traverser[T]): String = {

    abstract class TreePosition(val curToken: String, val childToken: String)

    case object Root           extends TreePosition(config.empty, config.empty)
    case object SiblingNotLast extends TreePosition(config.childNode, config.pipe)
    case object SiblingLast    extends TreePosition(config.childNodeLast, config.blank)

    def render0(node: T, prefix: String, pos: TreePosition): String = {

      val children    = nc.children(node)
      val numChildren = children.length

      val childPrefix = s"$prefix${pos.childToken}${config.blank * (config.nodeWidth + 1)}"
      val childrenLines = children.zipWithIndex
        .map {
          case (kid, idx) if idx == numChildren - 1 => render0(kid, childPrefix, SiblingLast)
          case (kid, _)                             => render0(kid, childPrefix, SiblingNotLast)
        }
        .mkString("")

      val extraLinePrefix =
        s"$prefix${pos.childToken}${config.blank * (config.nodeWidth + 1)}${
            if (numChildren > 0) config.pipe + (config.blank * 2)
            else config.empty
          }"

      val contents: String =
        nc.show(node).split("\\n").toList match {
          case head :: tail => (head +: tail.map(t => s"\n$extraLinePrefix$t")).mkString
          case other        => other.mkString
        }

      val currentLine = s"$prefix${pos.curToken}${config.dash * config.nodeWidth}${config.blank}$contents\n"

      currentLine + childrenLines
    }

    render0(root, "", Root)

  }

  implicit val NodeTraverser: Traverser[Node] = new Traverser[Node] {
    override def children(node: Node): Seq[Node] = node.children
    override def show(node: Node): String        = node.value
  }

}
