ThisBuild / scalaVersion := "2.13.10"

lazy val publishSettings = Seq(
  Test / publishArtifact := false,
  pomIncludeRepository   := (_ => false),
  organization           := "io.laserdisc",
  homepage               := Some(url("http://laserdisc.io/console4s")),
  developers             := List(Developer("barryoneill", "Barry O'Neill", "", url("https://github.com/barryoneill"))),
  licenses               := Seq("MIT" -> url("https://raw.githubusercontent.com/laserdisc-io/console4s/master/LICENSE")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/laserdisc-io/console4s/tree/master"),
      "scm:git:git@github.com:laserdisc-io/console4s.git",
      "scm:git:git@github.com:laserdisc-io/console4s.git"
    )
  )
)

val root =
  (project in file("."))
    .settings(
      name := "console4s",
      Dependencies.Testing,
      publishSettings,
      Seq(
        addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
        scalacOptions ++= Seq(
          "-encoding",
          "UTF-8",
          "-deprecation",
          "-unchecked",
          "-feature",
          "-language:higherKinds",
          "-language:implicitConversions",
          "-language:postfixOps",
          "-Xlint:_,-byname-implicit",
          "-Xfatal-warnings"
        )
      ),
      addCommandAlias("format", ";scalafmtAll;scalafmtSbt"),
      addCommandAlias("checkFormat", ";scalafmtCheckAll;scalafmtSbtCheck"),
      addCommandAlias("fullTest", ";clean;checkFormat;test")
    )
    .enablePlugins(GitVersioning)
