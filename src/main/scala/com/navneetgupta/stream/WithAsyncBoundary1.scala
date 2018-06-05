package com.navneetgupta.stream

import scala.concurrent.Future
import akka.stream.scaladsl.Sink

object WithAsyncBoundary1 extends AkkaStreamsApp {
  val a = source.
    map { x => println(s"pre-map: $x"); x }.
    map(_ * 3).async.
    map { x => println(s"pre-filter: $x"); x }.
    filter(_ % 2 == 0).
    runForeach(x => s"done: $x")

  override def akkaStreamsExample: Future[_] = a

  runExample
}
