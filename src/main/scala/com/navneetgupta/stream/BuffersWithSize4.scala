package com.navneetgupta.stream

import akka.stream.scaladsl.Sink
import scala.concurrent.Future
import akka.stream.Attributes
import akka.stream.scaladsl.Source

object BuffersWithSize4 extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = Source(1 to 1000)
    .map { i ⇒ println(s"A: $i"); i }.async.withAttributes(Attributes.inputBuffer(initial = 8, max = 16))
    .map { i ⇒ println(s"B: $i"); i }.async.withAttributes(Attributes.inputBuffer(initial = 4, max = 8))
    .map { i ⇒ println(s"C: $i"); i }.async.withAttributes(Attributes.inputBuffer(initial = 2, max = 4))
    .runWith(Sink.ignore)
  //.runWith(Sink.foreach(println))

  runExample
}
