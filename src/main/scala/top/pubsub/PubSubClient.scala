package top.pubsub

import org.zeromq.ZMQ
import sample.Person

object PubSubClient extends App {

  val context = ZMQ.context(1)
  println("Connecting to publisher")
  val socket = context.socket(ZMQ.SUB)
  socket.connect("tcp://localhost:5555")
  socket.subscribe(Array.empty)

  (1 to 100).foreach { requestNbr =>
    val message = socket.recv(0)
    val person = Person.parseFrom(message)
    println(s"Received $person")
  }

  socket.close()
  context.term()
}
