import laserdisc.sbt.CompileTarget.Scala2And3
import laserdisc.sbt.LaserDiscDevelopers

ThisBuild / laserdiscCompileTarget := Scala2And3
ThisBuild / laserdiscRepoName := "console4s"

val root = (project in file("."))
  .settings(
    name       := "console4s",
    developers := List(LaserDiscDevelopers.Barry),
    Dependencies.Testing
  )
  .enablePlugins(LaserDiscDefaultsPlugin)
