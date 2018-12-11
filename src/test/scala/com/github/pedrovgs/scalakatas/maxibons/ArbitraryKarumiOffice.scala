package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.model._
import eu.timepit.refined.scalacheck.numeric._
import org.scalacheck.Arbitrary

trait ArbitraryKarumiOffice {

  implicit val arbitraryDeveloper: Arbitrary[Developer] = Arbitrary {
    for {
      name           <- Arbitrary.arbitrary[String]
      maxibonsToGrab <- Arbitrary.arbitrary[MaxibonsToGrab]
    } yield Developer(name, maxibonsToGrab)
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
