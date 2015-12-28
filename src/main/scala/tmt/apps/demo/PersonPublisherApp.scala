package tmt.apps.demo

import akka.stream.scaladsl.Source
import sample.Person
import tmt.app.Assembly

object PersonPublisherApp extends App {

  val assembly = new Assembly("dev", None)
  import assembly._

  val publisher = zmqPublisherFactory.make[Person](5555)

  val people = Source(1 to 100).map(i => Person(name = s"mushtaq-$i", id = i))

  publisher
    .publish(people)
    .onComplete { x =>
      publisher.shutdown()
      runtime.shutdown()
    }

}
