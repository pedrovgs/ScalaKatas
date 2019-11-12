package com.github.pedrovgs.gameoflife

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import com.github.pedrovgs.scalakatas.gameoflife.model.Cell
import org.scalacheck.Gen
import com.github.pedrovgs.scalakatas.gameoflife.model.Cell._
import org.scalacheck.Arbitrary

class CellSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryCell {

  it should "kill any cell with less than 2 neigbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(0, 1)) { (cell: Cell, aliveNeigbors: Int) =>
      cell.evolve(aliveNeigbors) shouldBe dead
    }
  }

  it should "keep any live cell as alive if the number of neigbors is 2" in {
    alive.evolve(2) shouldBe alive
  }

  it should "keep any live cell as alive if the number of neigbors is 3" in {
    alive.evolve(3) shouldBe alive
  }

  it should "kill any cell with more than 3 alive neigbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(4, Int.MaxValue)) { (cell: Cell, aliveNeigbors: Int) =>
      cell.evolve(aliveNeigbors) shouldBe dead
    }
  }

  it should "leave any dead cell as dead if the number of alive neigbors is not 2" in {
    val anyNumberOfNeigborsButTwo = Gen.choose(0, Int.MaxValue).filter(_ != 2)
    forAll(arbitraryCell.arbitrary, anyNumberOfNeigborsButTwo) { (cell: Cell, aliveNeigbors: Int) =>
      cell.evolve(aliveNeigbors) shouldBe dead
    }
  }

}
