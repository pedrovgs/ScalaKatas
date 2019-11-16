package com.github.pedrovgs.scalakatas.gameoflife

import org.scalatest.prop.{PropertyChecks, TableDrivenPropertyChecks}
import org.scalatest.{FlatSpec, Matchers}
import com.github.pedrovgs.scalakatas.gameoflife.model._
import org.scalacheck.Gen

import scala.annotation.tailrec

class UniverseSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryUniverse with ArbitraryCell {

  private val knownScenarios = Table(
    ("Initial universe", "Expected universe", "Number of ticks"),
    (Universe(Map()), Universe(Map()), 1),
    (Universe(Map(Position(1, 0) -> Alive, Position(1, 1) -> Alive)), Universe(Map()), 1),
    (Universe(Map(Position(0, 1) -> Alive, Position(1, 1) -> Alive, Position(2, 1) -> Alive)),
     Universe(Map(Position(1, 0) -> Alive, Position(1, 1) -> Alive, Position(1, 2) -> Alive)),
     1),
    (Universe(Map(Position(1, 0) -> Alive, Position(1, 1) -> Alive, Position(1, 2) -> Alive)),
     Universe(Map(Position(0, 1) -> Alive, Position(1, 1) -> Alive, Position(2, 1) -> Alive)),
     1),
    (Universe(Map(Position(0, 1) -> Alive, Position(1, 1) -> Alive, Position(2, 1) -> Alive, Position(2, 2) -> Alive)),
     Universe(Map(Position(1, 0) -> Alive, Position(1, 1) -> Alive, Position(2, 1) -> Alive, Position(2, 2) -> Alive)),
     1),
    (Universe(Map(Position(2, 2) -> Alive, Position(3, 2) -> Alive, Position(4, 2) -> Alive, Position(4, 3) -> Alive)),
     Universe(
       Map(
         Position(2, 2) -> Alive,
         Position(3, 1) -> Alive,
         Position(3, 3) -> Alive,
         Position(4, 1) -> Alive,
         Position(4, 3) -> Alive,
         Position(5, 2) -> Alive
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
