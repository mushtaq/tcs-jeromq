package tmt.apps

import akka.stream.scaladsl.Source
import sample.Person
import tmt.reactivemq.ZmqPublisher
import tmt.utils.ActorRuntime

object PersonPublisherApp extends App {

  val runtime = ActorRuntime.create()
  import runtime._

  val publisher = new ZmqPublisher[Person](
    address = "tcp://*:5555",
    runtime = runtime
  )

  val people = Source(1 to 100).map(i => Person(name = s"mushtaq-$i", id = i))

  publisher
    .publish(people)
    .onComplete { x =>
      publisher.shutdown()
      runtime.shutdown()
    }

}
