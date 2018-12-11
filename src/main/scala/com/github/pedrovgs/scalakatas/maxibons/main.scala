package com.github.pedrovgs.scalakatas.maxibons

import com.github.pedrovgs.scalakatas.maxibons.interpreters.SlackModule
import com.github.pedrovgs.scalakatas.maxibons.model.{KarumiFridge, Karumies, World}

object main extends App {
  override def main(args: Array[String]): Unit = {
    super.main(args)
    val program      = new KarumiHQs(new SlackModule)
    val initialWorld = World(new KarumiFridge())
    val finalWorld = program
      .openFridge(
        initialWorld,
        List(Karumies.pedro, Karumies.manolo, Karumies.tylos, Karumies.sarroyo, Karumies.sergio, Karumies.juanjo))
      .unsafeRunSync()
    println(s"Execution result " + finalWorld)
  }
}
