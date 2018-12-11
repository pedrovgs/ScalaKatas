name := "ScalaKatas"

version := "0.1"

scalaVersion := "2.12.8"

CommandAliases.addCommandAliases()

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.5.0"
libraryDependencies += "eu.timepit" %% "refined" % "0.9.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
