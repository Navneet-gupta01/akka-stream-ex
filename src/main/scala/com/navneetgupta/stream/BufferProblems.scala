package com.navneetgupta.stream

import scala.concurrent.duration._
import akka.stream.scaladsl.{ Source, Sink, Flow }
import scala.concurrent.Future
import akka.stream.Attributes

object BufferProblems extends AkkaStreamsApp {
  case class Tick()

  val fastSource = Source.tick(1 second, 1 second, Tick())
  val slowSource = Source.tick(3 second, 3 second, Tick())

  val asyncZip =
    Flow[Int]
      .zip(slowSource)
      .async.withAttributes(Attributes.inputBuffer(initial = 1, max = 1))

  override def akkaStreamsExample: Future[_] =
    fastSource
      .conflateWithSeed(seed = (_) => 1)((count, _) => count + 1)
      .log("Before AsyncZip")
      .via(asyncZip)
      .take(10)
      .log("After AsyncZip")
      .runForeach { case (i, t) => println("Received: {}", i) }

  runExample
}
