package com.github.pedrovgs.scalakatas.gameoflife

object model {

  case class Position(x: Int, y: Int) {
    lazy val topLeft: Position     = Position(x - 1, y - 1)
    lazy val top: Position         = Position(x, y - 1)
    lazy val topRight: Position    = Position(x + 1, y - 1)
    lazy val left: Position        = Position(x - 1, y)
    lazy val right: Position       = Position(x + 1, y)
    lazy val bottomLeft: Position  = Position(x - 1, y + 1)
    lazy val bottom: Position      = Position(x, y + 1)
    lazy val bottomRight: Position = Position(x + 1, y + 1)

    lazy val allNeighbors = Seq(topLeft, top, topRight, left, right, bottomLeft, bottom, bottomRight)
  }
  case class Universe(private val cells: Map[Position, Cell]) {
    private lazy val aliveCells      = cells.filter(_._2.isAlive)
    private lazy val minX            = if (aliveCells.isEmpty) 0 else aliveCells.minBy(_._1.x)._1.x - 1
    private lazy val minY            = if (aliveCells.isEmpty) 0 else aliveCells.minBy(_._1.y)._1.y - 1
    private lazy val maxX            = if (aliveCells.isEmpty) 0 else aliveCells.maxBy(_._1.x)._1.x + 1
    private lazy val maxY            = if (aliveCells.isEmpty) 0 else aliveCells.maxBy(_._1.y)._1.y + 1
    lazy val numberOfCellsCount: Int = (maxX - minX) * (maxY - minY)
    lazy val aliveCellsCount: Int    = aliveCells.count(_._2.isAlive)
    lazy val deadCellsCount: Int     = numberOfCellsCount - aliveCellsCount

    def computeNeighbors(cellPosition: Position): Seq[Cell] = cellPosition.allNeighbors.map(getCellAtPosition)

    def tick(): Universe = {
      if (cells.isEmpty) {
        return this
      }
      val evolvedCells = (minX to maxX)
        .flatMap { x =>
          (minY to maxY).map { y =>
            val cellPosition   = Position(x, y)
            val cell           = getCellAtPosition(cellPosition)
            val aliveNeighbors = computeNeighbors(cellPosition).count(_.isAlive)
            val evolvedCell    = cell.evolve(aliveNeighbors)
            (cellPosition, evolvedCell)
          }
        }
        .toMap
        .filter(_._2.isAlive)
      Universe(evolvedCells)
    }

    override def toString: String = {
      val stringBuilder = new StringBuilder()
      (minX to maxX).foreach { x =>
        (minY to maxY).foreach { y =>
          val cell = cells.getOrElse(Position(x, y), Cell.dead)
          cell match {
            case Cell.alive => stringBuilder.append("X")
            case _          => stringBuilder.append("_")
          }
        }
        stringBuilder.append("\n")
      }
      stringBuilder.toString()
    }

    private def getCellAtPosition(cellPosition: Position) =
      cells.getOrElse(cellPosition, Cell.dead)
  }

  object Cell {
    val alive: Cell = Cell(isAlive = true)
    val dead: Cell  = Cell(isAlive = false)

    def formStatus(isAlive: Boolean): Cell = {
      if (isAlive) alive else dead
    }
  }
  case class Cell(isAlive: Boolean) {
    private final val NEIGHBORS_TO_BE_ALIVE = (2 to 3).toList

    val isDead: Boolean = !isAlive

    def evolve(aliveNeighbors: Int): Cell =
      this match {
        case Cell.alive => evolveAliveCell(aliveNeighbors)
        case _          => evolveDeadCell(aliveNeighbors)
      }

    private def evolveAliveCell(aliveNeighbors: Int): Cell =
      Cell.formStatus(NEIGHBORS_TO_BE_ALIVE.contains(aliveNeighbors))

    private def evolveDeadCell(aliveNeighbors: Int): Cell =
      Cell.formStatus(aliveNeighbors == 3)
  }
}
