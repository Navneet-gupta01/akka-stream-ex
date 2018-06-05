package com.navneetgupta.stream

import akka.stream.scaladsl.Sink
import scala.concurrent.Future

object BuffersWithoutAsync extends AkkaStreamsApp {

  override def akkaStreamsExample: Future[_] = source
    .map { i ⇒ println(s"A: $i"); i }
    .map { i ⇒ println(s"B: $i"); i }
    .map { i ⇒ println(s"C: $i"); i }
    .runWith(Sink.ignore)
  //.runWith(Sink.foreach(println))

  runExample
}
