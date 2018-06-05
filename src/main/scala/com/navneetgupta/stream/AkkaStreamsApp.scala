package com.navneetgupta.stream

import scala.concurrent.{ ExecutionContext, Future }
import akka.stream.ActorMaterializer
import akka.actor.{ ActorSystem, Terminated }
import akka.event.{ LoggingAdapter, Logging }

trait AkkaStreamsApp extends App {
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val log: LoggingAdapter = Logging(system.eventStream, this.getClass.getSimpleName)

  def akkaStreamsExample: Future[_]

  def runExample: Future[Terminated] = (for {
    _ <- akkaStreamsExample
    term <- system.terminate()
  } yield term).recoverWith {
    case cause: Throwable =>
      log.error(cause, "Exception while executing example")
      system.terminate()
  }

  sys.addShutdownHook {
    system.terminate()
  }

}
