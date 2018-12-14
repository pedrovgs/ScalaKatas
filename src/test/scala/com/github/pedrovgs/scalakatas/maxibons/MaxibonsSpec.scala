package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.KarumiHQs.{maxibonsToRefill, minNumberOfMaxibons}
import com.github.pedrovgs.scalakatas.maxibons.interpreters.SlackModule
import com.github.pedrovgs.scalakatas.maxibons.model.{Developer, KarumiFridge, Karumies, World}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class MaxibonsSpec extends FlatSpec with Matchers with PropertyChecks with ArbitraryKarumiOffice {

  it should "always have more than 2 maxibons" in {
    forAll { (world: World, developer: Developer) =>
      val maxibonsLeft = openFridge(world, developer).karumiFridge.maxibonsLeft.value
      maxibonsLeft should be > minNumberOfMaxibons
    }
  }

  it should "always have more than 2 maxibons even if they go in groups" in {
    forAll { (world: World, developers: List[Developer]) =>
      val maxibonsLeft = openFridge(world, developers).karumiFridge.maxibonsLeft.value
      maxibonsLeft should be > minNumberOfMaxibons
    }
  }

  it should "ask for more maxibons using the chat client if we need to refill" in {
    forAll(arbitraryHungryDeveloper.arbitrary) { developer: Developer =>
      val world       = World(new KarumiFridge())
      val messageSent = openFridgeAndGetChatMessageSent(world, developer)
      messageSent shouldBe Some(s"Hi there! I'm ${developer.name}. We need more maxibons")
    }
  }

  it should "not ask for more maxibons using the chat client if we don't need to refill" in {
    forAll(arbitraryNotHungryDeveloper.arbitrary) { developer: Developer =>
      val world       = World(new KarumiFridge())
      val messageSent = openFridgeAndGetChatMessageSent(world, developer)
      messageSent shouldBe None
    }
  }

  it should "left the office with 6 maxibons left if Pedro and Manolo go to the fridge" in {
    val world  = World(new KarumiFridge())
    val fridge = openFridge(world, List(Karumies.pedro, Karumies.manolo)).karumiFridge
    fridge.maxibonsLeft.value shouldBe 6
  }

  it should "use 2 as the min number of maxibons" in {
    minNumberOfMaxibons shouldBe 2
  }

  it should "use 10 as the number of maxibons to refill when needed" in {
    maxibonsToRefill shouldBe 10
  }

  private def openFridge(world: World, developer: Developer): World =
    openFridge(world, List(developer))

  private def openFridge(world: World, developers: List[Developer]): World = {
    val karumiHQs = new KarumiHQs(new SlackModule())
    karumiHQs.openFridge(world, developers).unsafeRunSync()
  }

  private def openFridgeAndGetChatMessageSent(world: World, developer: Developer): Option[String] = {
    val chat      = new ChatMockModule()
    val karumiHQs = new KarumiHQs(chat)
    karumiHQs.openFridge(world, developer)
    chat.messageSent
  }
}
