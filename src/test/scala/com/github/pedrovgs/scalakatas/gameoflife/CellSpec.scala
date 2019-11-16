package com.github.pedrovgs.scalakatas.gameoflife

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import org.scalacheck.Gen
import com.github.pedrovgs.scalakatas.gameoflife.model._

class CellSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryCell {

  it should "kill any cell with less than 2 neighbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(0, 1)) { (cell: Cell, Aliveneighbors: Int) =>
      cell.evolve(Aliveneighbors) shouldBe Dead
    }
  }

  it should "keep any live cell as Alive if the number of neighbors is 2" in {
    Alive.evolve(2) shouldBe Alive
  }

  it should "keep any live cell as Alive if the number of neighbors is 3" in {
    Alive.evolve(3) shouldBe Alive
  }

  it should "kill any cell with more than 3 Alive neighbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(4, Int.MaxValue)) { (cell: Cell, Aliveneighbors: Int) =>
      cell.evolve(Aliveneighbors) shouldBe Dead
    }
  }

  it should "leave any Dead cell as Dead if the number of Alive neighbors is not 3" in {
    val anyNumberOfNeighborsButTwo = Gen.choose(0, Int.MaxValue).filter(_ != 3)
    forAll(arbitraryCell.arbitrary, anyNumberOfNeighborsButTwo) { (cell: Cell, Aliveneighbors: Int) =>
      cell.evolve(Aliveneighbors) shouldBe Dead
    }
  }

  it should "revive a Dead cell if the number of Alive neighbors is 3" in {
    Dead.evolve(3) shouldBe Alive
  }

}
