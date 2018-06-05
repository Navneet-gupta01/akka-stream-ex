package com.navneetgupta.stream

import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Source
import akka.stream.scaladsl.Tcp.IncomingConnection
import scala.concurrent.Future
import akka.stream.scaladsl.Tcp.ServerBinding
import akka.stream.scaladsl.Tcp
import akka.stream.scaladsl.Framing
import akka.util.ByteString

object TcpCalculator extends AkkaStreamsApp {
  val Calculation = """(\d+)(:\s*([-+*\/])\s*((:\s[- +])\d+)\s*)+$""".r("a", "b", "c")

  val calcFlow = Flow[String].
    map {
      case Calculation(a, "+", b) =>
        println("+ =" + a, "+", b); a.toInt + b.toInt
      case Calculation(a, "-", b) =>
        println("- =" + a, "-", b); a.toInt - b.toInt
      case Calculation(a, "*", b) =>
        println("* =" + a, "*", b); a.toInt * b.toInt
      case Calculation(a, "/", b) =>
        println("/ =" + a, "/", b); a.toInt / b.toInt
      case other =>
        println("other =" + other); 0
    }

  val connections: Source[IncomingConnection, Future[ServerBinding]] =
    Tcp().bind("localhost", 8888)

  override def akkaStreamsExample: Future[_] =
    connections runForeach { connection =>
      println(s"New connection from: ${connection.remoteAddress}")
      val calc = Flow[ByteString].
        via(Framing.delimiter(
          ByteString("\n"),
          maximumFrameLength = 256,
          allowTruncation = true)).
        map(x => {
          val s = x.utf8String
          println(s)
          s
        }).
        via(calcFlow).
        map(i => ByteString(s"$i\n"))
      connection.handleWith(calc)
    }

  runExample
}
