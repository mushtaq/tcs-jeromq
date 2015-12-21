package tmt.requestreply

import tmt.reactivemq.{ActorConfigs, ZmqServer}
import sample.{PhoneNumber, Person}
import sample.PhoneNumber.PhoneType

import scala.util.{Random, Try}

object PersonServer extends App {

  val default = ActorConfigs.create()
  import default._

  val server = new ZmqServer[Person, Person](
    address = "tcp://*:5555",
    requestParser = Person,
    actorConfigs = default
  )

  server.start { person =>
    val number = Random.nextInt(10000).toString
    val maybePhoneType = Try(PhoneType.values(Random.nextInt(4))).toOption
    val phoneNumber = PhoneNumber(number, maybePhoneType)
    person.addPhone(phoneNumber)
  }.onComplete { x =>
    server.stop()
    default.shutdown()
  }
}
