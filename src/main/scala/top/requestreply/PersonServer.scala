package top.requestreply

import org.zeromq.ZMQ
import reactivemq.ZmqServer
import sample.{PhoneNumber, Person}
import sample.PhoneNumber.PhoneType
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Random, Try}

object PersonServer extends App {

  val context = ZMQ.context(1)
  val server = new ZmqServer(context, "tcp://*:5555")

  server.start(Person) { person =>
    val number = Random.nextInt(10000).toString
    val maybePhoneType = Try(PhoneType.values(Random.nextInt(4))).toOption
    val phoneNumber = PhoneNumber(number, maybePhoneType)
    person.addPhone(phoneNumber)
  }.onComplete { x =>
    context.term()
  }
}
