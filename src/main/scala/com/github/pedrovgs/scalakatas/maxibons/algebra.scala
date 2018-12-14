package com.github.pedrovgs.scalakatas.maxibons

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

trait Chat[F[_]] {
  def sendMessage(message: String Refined NonEmpty): F[String]
}
