package io.laserdisc.console4s.tree

trait Traverser[T] {
  def children(node: T): Seq[T]
  def show(node: T): String
}
