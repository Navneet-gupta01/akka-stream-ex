package com.navneetgupta.stream

import akka.stream._
import akka.stream.scaladsl._
import akka.{ NotUsed, Done }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

object TimeBasedProcessing extends AkkaStreamsApp {
  val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)
  override def akkaStreamsExample: Future[_] =
    factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, 1.second)
      .runWith(lineSink("TimeBasedProcessing.txt"))

  runExample
}
