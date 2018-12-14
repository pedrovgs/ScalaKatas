package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.model._
import eu.timepit.refined.api.Refined.unsafeApply
import eu.timepit.refined.scalacheck.numeric._
import org.scalacheck.{Arbitrary, Gen}

trait ArbitraryKarumiOffice {

  implicit val arbitraryDeveloper: Arbitrary[Developer] = Arbitrary {
    for {
      name           <- Arbitrary.arbitrary[String]
      maxibonsToGrab <- Arbitrary.arbitrary[MaxibonsToGrab]
    } yield Developer(name, maxibonsToGrab)
  }

  val arbitraryHungryDeveloper: Arbitrary[Developer] = Arbitrary {
    for {
      name           <- Arbitrary.arbitrary[String]
      maxibonsToGrab <- Gen.choose(8, Int.MaxValue)
    } yield Developer(name, unsafeApply(maxibonsToGrab))
  }

  val arbitraryNotHungryDeveloper: Arbitrary[Developer] = Arbitrary {
    for {
      name           <- Arbitrary.arbitrary[String]
      maxibonsToGrab <- Gen.choose(0, 7)
    } yield Developer(name, unsafeApply(maxibonsToGrab))
  }

  implicit val arbitraryKarumiFridge: Arbitrary[KarumiFridge] = Arbitrary {
    for {
      maxibonsLeft <- Arbitrary.arbitrary[MaxibonsLeft]
    } yield KarumiFridge(maxibonsLeft)
  }

  implicit def arbitraryWorld: Arbitrary[World] = Arbitrary {
    for {
      karumiFridge <- Arbitrary.arbitrary[KarumiFridge]
    } yield World(karumiFridge)
  }
}
