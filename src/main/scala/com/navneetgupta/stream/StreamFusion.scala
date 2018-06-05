package com.navneetgupta.stream

import scala.concurrent.Future
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Flow

object StreamFusion extends AkkaStreamsApp {
  val flow = Flow[Int].map(_ * 3).filter(_ % 2 == 0)
  //val fused = Fusing.aggressive(flow) In 2.4 only in 2.5 By default Akka Streams will fuse the stream operators. This means that the processing steps of a flow or stream graph can be executed within the same Actor and has two consequences

  override def akkaStreamsExample: Future[_] = Source(List(1, 2, 3, 4, 5)).via(flow).runForeach(x => println("value is ", x))

  runExample
}
