package com.navneetgupta.stream

import akka.stream._
import akka.stream.scaladsl._
import akka.{ NotUsed, Done }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

object StreamFirst extends AkkaStreamsApp {
  val source: Source[Int, NotUsed] = Source(1 to 100)
  override def akkaStreamsExample: Future[_] = source.runForeach(i ⇒ println(i))

  runExample
}
