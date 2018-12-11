package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.KarumiHQs.{maxibonsToRefill, minNumberOfMaxibons}
import com.github.pedrovgs.scalakatas.maxibons.arbitrary._
import com.github.pedrovgs.scalakatas.maxibons.interpreters.SlackModule
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class MaxibonsSpec extends FlatSpec with Matchers with PropertyChecks {

  it should "always have more than 2 maxibons" in {
    forAll(arbitraryWorld, arbitraryDeveloper) { (world, developer) =>
      openFridge(world, developer).karumiFridge.maxibonsLeft.value should be > minNumberOfMaxibons
    }
  }

  it should "always have more than 2 maxibons even if they go in groups" in {
    forAll(arbitraryWorld, Gen.listOf(arbitraryDeveloper)) { (world, developers) =>
      openFridge(world, developers).karumiFridge.maxibonsLeft.value should be > minNumberOfMaxibons
    }
  }

  it should "use 2 as the min number of maxibons" in {
    minNumberOfMaxibons shouldBe 2
  }

  it should "use 10 as the number of maxibons to refill when needed" in {
    maxibonsToRefill shouldBe 10
  }

  private def openFridge(world: World, developer: Developer): World =
    openFridge(world, Seq(developer))

  private def openFridge(world: World, developers: Seq[Developer]): World = {
    val karumiHQs = new KarumiHQs(new SlackModule())
    karumiHQs.openFridge(world, developers).unsafeRunSync()
  }
}
