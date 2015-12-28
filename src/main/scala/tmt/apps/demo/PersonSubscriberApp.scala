package tmt.apps.demo

import sample.Person
import tmt.app.Assembly

object PersonSubscriberApp extends App {

  val assembly = new Assembly("dev", None)
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
