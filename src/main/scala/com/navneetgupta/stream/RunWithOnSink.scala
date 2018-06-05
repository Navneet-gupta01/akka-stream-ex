package com.navneetgupta.stream

import akka.stream.scaladsl.Sink
import scala.concurrent.Future

object RunWithOnSink extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = for {
    a <- source.runWith(Sink.fold(0)(_ + _))
  } yield (println("=======================value of a is  {}=========================", a))

  runExample
}
