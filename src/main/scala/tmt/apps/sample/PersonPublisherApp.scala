package tmt.apps.sample

import akka.stream.scaladsl.Source
import caseapp._
import sample.Person
import tmt.app.{Params, Assembly}

case class PersonPublisher(params: Params) extends App {

  val assembly = new Assembly(params)
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

object PersonPublisherApp extends AppOf[PersonPublisher] {
  def parser = default
}
