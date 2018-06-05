package com.navneetgupta.stream

import scala.concurrent.Future
import akka.stream.scaladsl.Sink

object WithoutAsyncBoundary1 extends AkkaStreamsApp {
  val a = source.
    map { x => println(s"pre-map: $x"); x }.
    map(_ * 3).
    map { x => println(s"pre-filter: $x"); x }.
    filter(_ % 2 == 0).
    runForeach(x => s"done: $x")
  override def akkaStreamsExample: Future[_] = a

  runExample
}
