package com.github.pedrovgs.scalakatas.maxibons

import cats.Monad
import cats.implicits._
import eu.timepit.refined.api.Refined.unsafeApply
import eu.timepit.refined.auto._

object KarumiHQs {
  private val maxibonsToRefill = 10
}

class KarumiHQs[F[_]: Monad](C: Chat[F]) {

  import KarumiHQs._

  def openFridge(world: World, developers: Seq[Developer]): F[World] = developers match {
    case Nil => Monad[F].pure(world)
    case dev :: rest =>
      for {
        headUpdatedWorld    <- openFridge(world, dev)
        restOfTheDevelopers <- openFridge(headUpdatedWorld, rest)
      } yield restOfTheDevelopers
  }

  def openFridge(world: World, developer: Developer): F[World] =
    for {
      finalMaxibonsAmount <- refillMaxibonsIfNeeded(developer, computeMaxibonsLeft(world, developer))
    } yield world.copy(karumiFridge = world.karumiFridge.copy(maxibonsLeft = unsafeApply(finalMaxibonsAmount)))

  private def refillMaxibonsIfNeeded(developer: Developer, maxibonsLeft: Int) =
    if (shouldRefillMaxibons(maxibonsLeft)) {
      askTheTeamForMoreMaxibons(developer).map(_ => maxibonsLeft + maxibonsToRefill)
    } else {
      Monad[F].pure(maxibonsLeft)
    }

  private def shouldRefillMaxibons(maxibonsLeft: Int): Boolean =
    maxibonsLeft <= 2

  private def computeMaxibonsLeft(world: World, developer: Developer) =
    Math.max(0, world.karumiFridge.maxibonsLeft - developer.maxibonsToGrab)

  private def askTheTeamForMoreMaxibons(developer: Developer): F[String] =
    C.sendMessage(unsafeApply(s"Hi there! I'm ${developer.name}. We need more maxibons"))
}
