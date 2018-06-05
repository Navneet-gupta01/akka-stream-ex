package com.navneetgupta.stream

import akka.stream.scaladsl.Sink
import scala.concurrent.Future
import akka.stream.Attributes

object Buffers1 extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = source
    .map { i ⇒ println(s"A: $i"); i }.async.withAttributes(Attributes.inputBuffer(initial = 1, max = 1))
    .map { i ⇒ println(s"B: $i"); i }.async
    .map { i ⇒ println(s"C: $i"); i }.async
    .runWith(Sink.ignore)
  //.runWith(Sink.foreach(println))

  runExample
}
