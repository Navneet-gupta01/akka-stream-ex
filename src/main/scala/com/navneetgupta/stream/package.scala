package com.navneetgupta

import akka.stream.scaladsl.Sink
import scala.concurrent.Future
import akka.stream.IOResult
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import akka.stream.scaladsl.FileIO
import java.nio.file.Paths
import akka.stream.scaladsl.Keep
import akka.stream.scaladsl.Source
import akka.NotUsed

package object stream {
  val source: Source[Int, NotUsed] = Source(1 to 100)

  def lineSink(filename: String): Sink[String, Future[IOResult]] =
    Flow[String]
      .map(s ⇒ ByteString(s + "\n"))
      .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)
}
