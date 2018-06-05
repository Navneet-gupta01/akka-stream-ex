package com.navneetgupta.stream

import scala.concurrent.Future
import akka.stream.scaladsl.Source
import akka.stream.OverflowStrategy
import akka.stream.ThrottleMode
import scala.concurrent.duration._

object ExplicitBufferDropTail extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = Source(1 to 10000).
    map { x => println(s"passing $x"); x }.
    buffer(25, OverflowStrategy.dropTail).
    throttle(1, 1 second, 1, ThrottleMode.shaping).runForeach(println)

  runExample
}
