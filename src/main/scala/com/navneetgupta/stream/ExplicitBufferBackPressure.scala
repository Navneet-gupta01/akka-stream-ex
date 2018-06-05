package com.navneetgupta.stream

import scala.concurrent.Future
import akka.stream.scaladsl.Source
import akka.stream.OverflowStrategy
import akka.stream.ThrottleMode
import scala.concurrent.duration._

object ExplicitBufferBackPressure extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = Source(1 to 1000000).
    map { x => println(s"passing $x"); x }.
    buffer(25, OverflowStrategy.backpressure).
    throttle(1, 1 second, 1, ThrottleMode.shaping).runForeach(println)

  runExample
}
