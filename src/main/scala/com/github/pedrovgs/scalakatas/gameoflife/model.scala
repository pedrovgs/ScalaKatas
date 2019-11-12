package com.github.pedrovgs.scalakatas.gameoflife

object model {
  object Cell {
    val alive: Cell = Cell(isAlive = true)
    val dead: Cell  = Cell(isAlive = false)
  }
  case class Cell(isAlive: Boolean) {
    import Cell._
    val isDead: Boolean = !isAlive

    def evolve(aliveNeighbors: Int): Cell =
      this match {
        case Cell(true)  => evolveAliveCell(aliveNeighbors)
        case Cell(false) => evolveDeadCell(aliveNeighbors)
      }

    private def evolveAliveCell(aliveNeighbors: Int): Cell =
      if (aliveNeighbors < 2) {
        dead
      } else if (aliveNeighbors == 2 || aliveNeighbors == 3) {
        alive
      } else {
        dead
      }

    private def evolveDeadCell(aliveNeighbors: Int): Cell =
      if (aliveNeighbors == 2) {
        alive
      } else {
        dead
      }
  }
}
