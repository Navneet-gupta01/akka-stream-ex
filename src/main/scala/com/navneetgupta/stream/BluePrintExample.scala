package com.navneetgupta.stream

import java.nio.file.FileSystems
import akka.stream.scaladsl.FileIO
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Framing
import akka.util.ByteString
import akka.stream.scaladsl.Keep
import scala.concurrent.Future

object BluePrintExample extends AkkaStreamsApp {

  val file = this.getClass.getClassLoader().
    getResource("current_inventory.csv")

  val inPath = FileSystems.getDefault().
    getPath(file.getPath())

  val outPath = FileSystems.getDefault().
    getPath("no_inventory.csv")
  val fileSource = FileIO.fromPath(inPath)
  val fileSink = FileIO.toPath(outPath)

  //drop(1) to drop the header read from the xsv sheet.
  // Then split other lines based on ','
  val csvHandler = Flow[String].drop(1).map(_.split(",").toList)

  val lowInventoryFlow = fileSource.
    via(Framing.delimiter(ByteString("\n"), Integer.MAX_VALUE)).
    map(_.utf8String).
    via(csvHandler).
    filter(list => list(2).toInt == 0).
    map { list =>
      ByteString(list.mkString(",")) ++ ByteString("\n")
    }.
    toMat(fileSink)(Keep.right)

  override def akkaStreamsExample: Future[_] = lowInventoryFlow.run()

  runExample
}
