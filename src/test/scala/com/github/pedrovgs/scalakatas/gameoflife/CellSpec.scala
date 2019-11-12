package com.github.pedrovgs.scalakatas.gameoflife

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import com.github.pedrovgs.scalakatas.gameoflife.model.Cell
import org.scalacheck.Gen
import com.github.pedrovgs.scalakatas.gameoflife.model.Cell._

class CellSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryCell {

  it should "kill any cell with less than 2 neighbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(0, 1)) { (cell: Cell, aliveneighbors: Int) =>
      cell.evolve(aliveneighbors) shouldBe dead
    }
  }

  it should "keep any live cell as alive if the number of neighbors is 2" in {
    alive.evolve(2) shouldBe alive
  }

  it should "keep any live cell as alive if the number of neighbors is 3" in {
    alive.evolve(3) shouldBe alive
  }

  it should "kill any cell with more than 3 alive neighbors" in {
    forAll(arbitraryCell.arbitrary, Gen.choose(4, Int.MaxValue)) { (cell: Cell, aliveneighbors: Int) =>
      cell.evolve(aliveneighbors) shouldBe dead
    }
  }

  it should "leave any dead cell as dead if the number of alive neighbors is not 3" in {
    val anyNumberOfneighborsButTwo = Gen.choose(0, Int.MaxValue).filter(_ != 3)
    forAll(arbitraryCell.arbitrary, anyNumberOfneighborsButTwo) { (cell: Cell, aliveneighbors: Int) =>
      cell.evolve(aliveneighbors) shouldBe dead
    }
  }

  it should "revive a dead cell if the number of alive neighbors is 3" in {
    dead.evolve(3) shouldBe alive
  }

}
