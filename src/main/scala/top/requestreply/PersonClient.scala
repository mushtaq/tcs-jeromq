package top.requestreply

import org.zeromq.ZMQ
import reactivemq.ZmqClient
import sample.Person
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PersonClient extends App {
  val context = ZMQ.context(1)
  val client = new ZmqClient(context, "tcp://localhost:5555")

  Future.traverse(1 to 10) {  requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    client.query(person, Person).map(x => println(s"Received $x"))
  }.onComplete { x =>
    client.close()
    context.term()
  }

}
