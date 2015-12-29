package tmt.sample

import caseapp._
import sample.Person
import tmt.app.{Params, Assembly}

case class PersonSubscriber(params: Params) extends App {
  val assembly = new Assembly(params)
  import assembly._

  val subscriber = zmqSubscriberFactory.make(5555, Person)

  subscriber.stream.runForeach { x =>
    println(s"Received $x")
  }.onComplete { x =>
    println(s"completed with value: $x")
    subscriber.shutdown()
    runtime.shutdown()
  }
}

object PersonSubscriberApp extends AppOf[PersonSubscriber] {
  def parser = default
}
