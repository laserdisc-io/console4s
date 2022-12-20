import sbt.Keys._
import sbt._

object Dependencies {

  val Testing = libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test

}
