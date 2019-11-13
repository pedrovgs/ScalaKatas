package com.github.pedrovgs.scalakatas.gameoflife

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._
import com.github.pedrovgs.scalakatas.gameoflife.model.{Cell, Position, Universe}

trait ArbitraryUniverse extends ArbitraryCell {

  private val arbitraryPosition: Arbitrary[Position] = Arbitrary {
    for {
      xPosition <- Arbitrary.arbitrary[Int]
      yPosition <- Arbitrary.arbitrary[Int]
    } yield Position(xPosition, yPosition)
  }

  private val arrow: Arbitrary[Seq[Position]] = Arbitrary {
    def excludeBorders(position: Position) = {
      position.x != Int.MinValue && position.x != Int.MaxValue && position.y != Int.MinValue && position.y != Int.MaxValue
    }

    for {
      position <- arbitraryPosition.arbitrary.filter(excludeBorders)
      corners <- Gen.oneOf(
        Seq(
          Seq(position.topRight, position.topLeft),
          Seq(position.topRight, position.bottomRight),
          Seq(position.topLeft, position.bottomLeft),
          Seq(position.bottomRight, position.bottomLeft)
        ))
    } yield corners :+ position
  }

  private val arbitraryCellAtPosition: Arbitrary[(Position, Cell)] = Arbitrary {
    for {
      position <- arbitraryPosition.arbitrary
      cell     <- arbitraryCell.arbitrary
    } yield (position, cell)
  }

  implicit val arbitraryUniverse: Arbitrary[Universe] = Arbitrary {
    for {
      numberOfCells <- Gen.posNum[Int]
      cells         <- mapOfN(numberOfCells, arbitraryCellAtPosition.arbitrary)
    } yield Universe(cells)
  }

  def arbitraryUniverseWithNumberOfAliveCells(aliveCells: Int): Arbitrary[Universe] =
    Arbitrary {
      for {
        cells: Map[Position, Cell] <- mapOfN(aliveCells, arbitraryAliveCellAtPosition.arbitrary)
      } yield Universe(cells)
    }

  def arbitraryUniverseWithDeadCells(): Arbitrary[Universe] = Arbitrary {
    for {
      numberOfDeadCells          <- Gen.posNum[Int]
      cells: Map[Position, Cell] <- mapOfN(numberOfDeadCells, arbitraryDeadCellAtPosition.arbitrary)
    } yield Universe(cells)
  }

  def arbitraryUniverseWithAnArrow(): Arbitrary[Universe] = Arbitrary {
    for {
      arrow <- arrow.arbitrary
      cells = arrow.map(_ -> Cell.alive).toMap
    } yield Universe(cells)
  }

  private val arbitraryAliveCellAtPosition: Arbitrary[(Position, Cell)] = Arbitrary {
    for {
      position <- arbitraryPosition.arbitrary
      cell     <- Gen.const(Cell.alive)
    } yield (position, cell)
  }

  private val arbitraryDeadCellAtPosition: Arbitrary[(Position, Cell)] = Arbitrary {
    for {
      position <- arbitraryPosition.arbitrary
      cell     <- Gen.const(Cell.dead)
    } yield (position, cell)
  }
}
