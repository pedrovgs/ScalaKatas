package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.KarumiHQs.{maxibonsToRefill, minNumberOfMaxibons}
import eu.timepit.refined.api.Refined.unsafeApply
import org.scalacheck.Gen

object arbitrary {

  val arbitraryDeveloper: Gen[Developer] = for {
    name           <- Gen.alphaStr
    maxibonsToGrab <- Gen.choose(0, Int.MaxValue)
  } yield Developer(name, unsafeApply(maxibonsToGrab))

  val arbitraryKarumiFridge: Gen[KarumiFridge] = for {
    maxibonsLeft <- Gen.choose(3, minNumberOfMaxibons + maxibonsToRefill)
  } yield KarumiFridge(unsafeApply(maxibonsLeft))

  val arbitraryWorld: Gen[World] = for {
    karumiFridge <- arbitraryKarumiFridge
  } yield World(karumiFridge)

}
