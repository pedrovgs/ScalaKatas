package com.github.pedrovgs.scalakatas.maxibons

import cats.Monad
import cats.implicits._
import com.github.pedrovgs.scalakatas.maxibons.model.{Developer, World}
import eu.timepit.refined.api.Refined.unsafeApply
import eu.timepit.refined.auto._

object KarumiHQs {
  val maxibonsToRefill    = 10
  val minNumberOfMaxibons = 2
}

final class KarumiHQs[F[_]: Monad](C: Chat[F]) {

  import KarumiHQs._

  def openFridge(world: World, developers: List[Developer]): F[World] =
    developers.foldM(world)(openFridge)

  def openFridge(world: World, developer: Developer): F[World] =
    for {
      finalMaxibonsAmount <- refillMaxibonsIfNeeded(developer, computeMaxibonsLeft(world, developer))
    } yield World.maxibonsLeftLens.set(unsafeApply(finalMaxibonsAmount))(world)

  private def refillMaxibonsIfNeeded(developer: Developer, maxibonsLeft: Int) =
    if (shouldRefillMaxibons(maxibonsLeft)) {
      askTheTeamForMoreMaxibons(developer).map(_ => maxibonsLeft + maxibonsToRefill)
    } else {
      Monad[F].pure(maxibonsLeft)
    }

  private def shouldRefillMaxibons(maxibonsLeft: Int): Boolean =
    maxibonsLeft <= minNumberOfMaxibons

  private def computeMaxibonsLeft(world: World, developer: Developer) =
    Math.max(0, world.karumiFridge.maxibonsLeft - developer.maxibonsToGrab)

  private def askTheTeamForMoreMaxibons(developer: Developer): F[String] =
    C.sendMessage(unsafeApply(s"Hi there! I'm ${developer.name}. We need more maxibons"))
}
