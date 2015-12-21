package tmt.requestreply

import sample.Person
import tmt.reactivemq.{ActorConfigs, ZmqClient}

import scala.concurrent.Future

object PersonClient extends App {

  val default = ActorConfigs.create()
  import default._

  val client = new ZmqClient[Person, Person](
    address = "tcp://localhost:5555",
    responseParser = Person,
    actorConfigs = default
  )

  Future.traverse(1 to 10) {  requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    client.query(person).map(x => println(s"Received $x"))
  }.onComplete { x =>
    client.close()
    default.shutdown()
  }

}
