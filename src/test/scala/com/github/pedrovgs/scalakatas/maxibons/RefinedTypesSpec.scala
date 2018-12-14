package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.model.{MaxibonsLeft, MaxibonsToGrab}
import eu.timepit.refined.scalacheck.numeric._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class RefinedTypesSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryKarumiOffice {

  "Developer types" should "generate a number of maxibons to grab bigger than 0" in {
    forAll { maxibonsToGrab: MaxibonsToGrab =>
      maxibonsToGrab.value should be >= 0
    }
  }

  "KarumiFridge types" should "generate a number of maxibons left between 3 and 12 both included" in {
    forAll { maxibonsLeft: MaxibonsLeft =>
      val fridgeMaxibonsLeft = maxibonsLeft.value
      fridgeMaxibonsLeft should be >= 3
      fridgeMaxibonsLeft should be <= 12
    }
  }

}
