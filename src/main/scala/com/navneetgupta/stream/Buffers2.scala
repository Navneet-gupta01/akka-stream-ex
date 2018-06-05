package com.navneetgupta.stream

import akka.stream.scaladsl.Sink
import scala.concurrent.Future

object Buffers2 extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = source
    .map { i ⇒ println(s"A: $i"); i }.async
    .map { i ⇒ println(s"B: $i"); i }
    .map { i ⇒ println(s"C: $i"); i }.async
    .runWith(Sink.ignore)
  //.runWith(Sink.foreach(println))

  runExample
}
