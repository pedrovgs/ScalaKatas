package com.github.pedrovgs.scalakatas.gameoflife

import com.github.pedrovgs.scalakatas.gameoflife.model._

object main extends App {

  val universe        = Universe(Map(Position(0, 0) -> Alive, Position(1, 0) -> Alive, Position(2, 0) -> Alive))
  val evolvedUniverse = universe.tick().tick()
  println(evolvedUniverse)

}
