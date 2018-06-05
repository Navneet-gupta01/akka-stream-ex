package com.navneetgupta.stream

import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.stream.OverflowStrategy
import akka.stream.ThrottleMode
import scala.concurrent.duration._
import scala.concurrent.Future

object ExternalBufferBackPressure extends AkkaStreamsApp {

  //  val jobs: Source[Int, NotUsed] = Source(1 to 100000)
  //  jobs.buffer(1000, OverflowStrategy.backpressure)
  //jobs.buffer(1000, OverflowStrategy.dropTail) space for the new element by dropping one element from the tail of the buffer. Dropping from the tail is a very common strategy but it must be noted that this will drop the youngest waiting job.
  //jobs.buffer(1000, OverflowStrategy.dropNew)  Instead of dropping the youngest element from the tail of the buffer a new element can be dropped without enqueueing it to the buffer at all.
  //jobs.buffer(1000, OverflowStrategy.dropHead) another example with a queue of 1000 jobs, but it makes space for the new element by dropping one element from the head of the buffer. This is the oldest waiting job.
  //jobs.buffer(1000, OverflowStrategy.dropBuffer) Compared to the dropping strategies above, dropBuffer drops all the 1000 jobs it has enqueued once the buffer gets full.

  //jobs.buffer(1000, OverflowStrategy.fail)

  override def akkaStreamsExample: Future[_] = Source(1 to 1000000).
    map { x => println(s"passing $x"); x }.
    buffer(5, OverflowStrategy.backpressure).
    throttle(1, 1 second, 1, ThrottleMode.shaping).runForeach(println)

  runExample

}
