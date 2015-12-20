package top.requestreply

import org.zeromq.ZMQ
import sample.Person

object ReqRepClient extends App {

  val context = ZMQ.context(1)
  println("Connecting to PB server")
  val socket = context.socket(ZMQ.REQ)
  socket.connect("tcp://localhost:5555")

  (1 to 10).foreach { requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    println(s"Sending $person")
    socket.send(person.toByteArray, 0)
    val reply = socket.recv(0)
    val updatedPerson = Person.parseFrom(reply)
    println(s"Received $updatedPerson")
  }

  socket.close()
  context.term()
}
