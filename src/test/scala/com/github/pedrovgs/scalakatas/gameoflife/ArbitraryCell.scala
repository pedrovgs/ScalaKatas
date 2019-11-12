package com.github.pedrovgs.scalakatas.gameoflife

import org.scalacheck.Arbitrary
import com.github.pedrovgs.scalakatas.gameoflife.model.Cell

trait ArbitraryCell {
  implicit val arbitraryCell: Arbitrary[Cell] = Arbitrary {
    for {
      alive <- Arbitrary.arbitrary[Boolean]
    } yield Cell(alive)
  }

}
