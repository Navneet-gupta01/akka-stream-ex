package com.navneetgupta.stream

import akka.stream._
import akka.stream.scaladsl._
import akka.{ NotUsed, Done }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

object Factorials extends AkkaStreamsApp {
  val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

  override def akkaStreamsExample: Future[_] = factorials
    .map(num ⇒ ByteString(s"$num\n"))
    .runWith(FileIO.toPath(Paths.get("factorials.txt")))

  runExample
}

/*
 * First we use the scan combinator to run a computation over the whole stream: starting with the number 1 (BigInt(1)) we multiple by each of the
 * incoming numbers, one after the other; the scan operation emits the initial value and then every calculation result. This yields the series of
 * factorial numbers which we stash away as a Source for later reuse—it is important to keep in mind that nothing is actually computed yet, this
 * is just a description of what we want to have computed once we run the stream. Then we convert the resulting series of numbers into a stream of
 * ByteString objects describing lines in a text file. This stream is then run by attaching a file as the receiver of the data. In the terminology
 * of Akka Streams this is called a Sink. IOResult is a type that IO operations return in Akka Streams in order to tell you how many bytes or elements
 * were consumed and whether the stream terminated normally or exceptionally.
 * */
