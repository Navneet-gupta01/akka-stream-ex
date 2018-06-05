package com.navneetgupta.stream

import akka.stream.scaladsl._

import scala.concurrent.Future
import scala.concurrent.duration._
import akka.NotUsed
import akka.Done

object BasicStream extends AkkaStreamsApp {

  val source: Source[Int, NotUsed] = Source(1 to 5)
  val sink: Sink[Int, Future[Done]] = Sink.foreach[Int](println)
  val dataflow: RunnableGraph[NotUsed] = source.to(sink)
  val sink2: Sink[Int, Future[Int]] = Sink.fold(0)(_ + _)
  val dataflow2: RunnableGraph[Future[Int]] =
    source.log("dataflow2").toMat(sink2)(Keep.right)

  val flow = Flow[Int].map(_ * 2).filter(_ % 2 == 0)
  val fut2 = source.via(flow).toMat(sink2)(Keep.right)

  val flow2 = Flow[Int].map(_ * 2).filter(_ % 4 == 0)
  val fut3 = source.via(flow2).toMat(sink2)(Keep.right)

  override def akkaStreamsExample: Future[_] = for {
    _ <- Future { dataflow.run }
    a <- dataflow2.run
    b <- fut2.run
    c <- fut3.run
  } yield { log.info("a is : {} ", a); log.info("b is : {}", b); log.info("c is : {}", c) }

  runExample
}
