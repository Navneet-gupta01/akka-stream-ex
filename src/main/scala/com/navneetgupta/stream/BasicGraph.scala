package com.navneetgupta.stream

import akka.stream.ClosedShape
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.GraphDSL
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.RunnableGraph
import akka.NotUsed
import akka.stream.scaladsl.Broadcast
import akka.stream.scaladsl.Merge
import scala.concurrent.Future

object BasicGraph extends AkkaStreamsApp {
  val g = RunnableGraph.fromGraph(GraphDSL.create() {
    implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val in = Source(1 to 5)
      val out = Sink.foreach[Int](i => print(s"$i "))
      val f1 = Flow[Int].map(_ * 2)
      val f2 = Flow[Int].map(_ * 1)
      val f3 = Flow[Int].map(_ * 2)
      val f4 = Flow[Int].map(_ * 1)
      val bcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))
      in ~> f1 ~> bcast ~> f2 ~> merge ~> f4 ~> out
      bcast ~> f3 ~> merge
      ClosedShape
  })
  override def akkaStreamsExample: Future[_] = Future { g.run }

  runExample
}
