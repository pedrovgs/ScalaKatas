name := "ScalaKatas"

version := "0.1"

scalaVersion := "2.12.8"

CommandAliases.addCommandAliases()

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.5.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.1.0"
libraryDependencies += "eu.timepit" %% "refined" % "0.9.3"
libraryDependencies += "com.github.julien-truffaut" %% "monocle-core" % "1.5.0"
libraryDependencies += "com.github.julien-truffaut" %% "monocle-macro" % "1.5.0"

libraryDependencies += "com.github.julien-truffaut" %% "monocle-law" % "1.5.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0" % Test
libraryDependencies += "eu.timepit" %% "refined-scalacheck_1.13" % "0.9.3" % Test

addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)
