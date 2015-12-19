package top

import org.zeromq.ZMQ
import sample.Person

object HelloWorldClient extends App {

  val context = ZMQ.context(1)
  println("Connecting to PB server")
  val socket = context.socket(ZMQ.REQ)
  socket.connect("tcp://localhost:5555")

  (1 to 10).foreach { requestNbr =>
    val person = Person("mushtaq", 100, None, Seq.empty)
    println(s"Sending $person $requestNbr")
    socket.send(person.toByteArray, 0)
    val reply = socket.recv(0)
    val updatedPerson = Person.parseFrom(reply)
    println(s"Received $updatedPerson $requestNbr")
  }

  socket.close()
  context.term()
}
