package com.github.pedrovgs.scalakatas.maxibons

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

final case class World(karumiFridge: KarumiFridge)

final case class Developer(name: String, maxibonsToGrab: Int Refined NonNegative)

final case class KarumiFridge(maxibonsLeft: Int Refined Greater[W.`2`.T]) {
  def this() = this(10)
}

object karumies {
  val pedro   = Developer("Pedro", 3)
  val sergio  = Developer("Sergio Gutierrez", 2)
  val tylos   = Developer("Tylos", 1)
  val juanjo  = Developer("Juanjo", 0)
  val sarroyo = Developer("Sergio Arroyo", 5)
  val manolo  = Developer("Manolo", 1)
}
