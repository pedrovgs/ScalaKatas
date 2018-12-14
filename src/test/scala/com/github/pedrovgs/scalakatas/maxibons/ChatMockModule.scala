package com.github.pedrovgs.scalakatas.maxibons

import cats.Id
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty

final class ChatMockModule extends Chat[Id] {
  var messageSent: Option[String] = None

  override def sendMessage(message: Refined[String, NonEmpty]): Id[String] = {
    val rawMessage = message.value
    messageSent = Some(rawMessage)
    rawMessage
  }
}
