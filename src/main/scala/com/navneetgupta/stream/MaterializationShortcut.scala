package com.navneetgupta.stream

import akka.stream.scaladsl.{ Flow, Sink, Source }
import scala.concurrent.Future

object MaterializationShortcut extends AkkaStreamsApp {
  val source = Source(List(1, 2, 3, 4, 5)).log("source")
  val sink = Sink.fold[Int, Int](0)(_ + _)
  val multiplier = Flow[Int].map(_ * 2).log("multiplier")

  multiplier.to(sink).runWith(source)

  override def akkaStreamsExample: Future[_] = for {
    a <- source.via(multiplier).runFold(0)(_ + _)
    b <- multiplier.runWith(source, sink)._2
  } yield (log.info("Flow test a: {} , b: {}", a, b))

  runExample
}
