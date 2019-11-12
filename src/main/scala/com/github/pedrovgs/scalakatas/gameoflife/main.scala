package com.github.pedrovgs.scalakatas.gameoflife

import com.github.pedrovgs.scalakatas.gameoflife.model.{Cell, Position, Universe}

object main extends App {

  val universe        = Universe(Map(Position(0, 0) -> Cell.alive, Position(1, 0) -> Cell.alive, Position(2, 0) -> Cell.alive))
  val evolvedUniverse = universe.tick().tick()
  println(evolvedUniverse)

}
