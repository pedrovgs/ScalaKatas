package com.github.pedrovgs.scalakatas.gameoflife

import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import com.github.pedrovgs.scalakatas.gameoflife.model._

trait ArbitraryCell {
  implicit val arbitraryCell: Arbitrary[Cell] = Arbitrary {
    for {
      cell <- Gen.oneOf(Gen.const(Alive), Gen.const(Dead))
    } yield cell
  }

}
