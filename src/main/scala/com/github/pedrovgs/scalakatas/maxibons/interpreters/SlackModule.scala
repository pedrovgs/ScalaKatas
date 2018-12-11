package com.github.pedrovgs.scalakatas.maxibons.interpreters

import cats.effect.IO
import com.github.pedrovgs.scalakatas.maxibons.Chat
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

final class SlackModule extends Chat[IO] {
  override def sendMessage(message: Refined[String, NonEmpty]): IO[String] = IO {
    println(message)
    message.value
  }
}
