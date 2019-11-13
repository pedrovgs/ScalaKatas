package com.github.pedrovgs.scalakatas.gameoflife

import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}
import org.scalatest.{FlatSpec, Matchers}
import com.github.pedrovgs.scalakatas.gameoflife.model.{Cell, Position, Universe}
import org.scalacheck.Gen

import scala.annotation.tailrec

class UniverseSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryUniverse with ArbitraryCell {

  private val knownScenarios = Table(
    ("Initial universe", "Expected universe", "Number of ticks"),
    (Universe(Map()), Universe(Map()), 1),
    (Universe(Map(Position(1, 0) -> Cell.alive, Position(1, 1) -> Cell.alive)), Universe(Map()), 1),
    (Universe(Map(Position(0, 1) -> Cell.alive, Position(1, 1) -> Cell.alive, Position(2, 1) -> Cell.alive)),
     Universe(Map(Position(1, 0) -> Cell.alive, Position(1, 1) -> Cell.alive, Position(1, 2) -> Cell.alive)),
     1),
    (Universe(Map(Position(1, 0) -> Cell.alive, Position(1, 1) -> Cell.alive, Position(1, 2) -> Cell.alive)),
     Universe(Map(Position(0, 1) -> Cell.alive, Position(1, 1) -> Cell.alive, Position(2, 1) -> Cell.alive)),
     1),
    (Universe(
       Map(Position(0, 1) -> Cell.alive,
           Position(1, 1) -> Cell.alive,
           Position(2, 1) -> Cell.alive,
           Position(2, 2) -> Cell.alive)),
     Universe(
       Map(Position(1, 0) -> Cell.alive,
           Position(1, 1) -> Cell.alive,
           Position(2, 1) -> Cell.alive,
           Position(2, 2) -> Cell.alive)),
     1),
    (Universe(
       Map(Position(2, 2) -> Cell.alive,
           Position(3, 2) -> Cell.alive,
           Position(4, 2) -> Cell.alive,
           Position(4, 3) -> Cell.alive)),
     Universe(
       Map(
         Position(2, 2) -> Cell.alive,
         Position(3, 1) -> Cell.alive,
         Position(3, 3) -> Cell.alive,
         Position(4, 1) -> Cell.alive,
         Position(4, 3) -> Cell.alive,
         Position(5, 2) -> Cell.alive
       )),
     4)
  )

  private val anyNumberOfTicks = Gen.posNum[Int].suchThat(_ >= 1)

  it should "evaluate all the already known scenarios" in {
    forAll(knownScenarios) { (initialUniverse, expectedUniverse, numberOfTicks) =>
      tickUniverse(initialUniverse, numberOfTicks) shouldBe expectedUniverse
    }
  }

  it should "kill any universe after any ticks if there is only one cell alive" in {
    forAll(arbitraryUniverseWithNumberOfAliveCells(1).arbitrary, anyNumberOfTicks) { (universe, ticks) =>
      tickUniverse(universe, ticks).aliveCellsCount shouldBe 0
    }
  }

  it should "calculate the universe number of cells as the sum of alive and dead cells" in {
    forAll { universe: Universe =>
      universe.aliveCellsCount + universe.deadCellsCount shouldBe universe.numberOfCellsCount
    }
  }

  it should "not generate any cell starting with an empty universe" in {
    forAll(arbitraryUniverseWithNumberOfAliveCells(0).arbitrary, anyNumberOfTicks) { (universe, ticks) =>
      tickUniverse(universe, ticks).aliveCellsCount shouldBe 0
    }
  }

  it should "keep a universe full of dead cells dead" in {
    forAll(arbitraryUniverseWithDeadCells().arbitrary, anyNumberOfTicks) { (universe, ticks) =>
      tickUniverse(universe, ticks).aliveCellsCount shouldBe 0
    }
  }

  it should "add new alive cell but remove the borders when the universe has an arrow" in {
    forAll(arbitraryUniverseWithAnArrow().arbitrary) { universe =>
      val theTipAndTheRevived = 2
      withClue(s"arrow universe should revive a cell: $universe") {
        tickUniverse(universe, 1).aliveCellsCount shouldBe theTipAndTheRevived
      }
    }
  }

  @tailrec
  private def tickUniverse(universe: Universe, ticks: Int): Universe =
    if (ticks == 0) {
      universe
    } else {
      tickUniverse(universe.tick(), ticks - 1)
    }
}
