package com.navneetgupta.stream

import akka.stream._
import akka.stream.scaladsl._
import akka.{ NotUsed, Done }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._

object FactorialsReusable extends AkkaStreamsApp {

  val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

  override def akkaStreamsExample: Future[_] = factorials.map(_.toString).runWith(lineSink("factorial2.txt"))

  runExample
}
