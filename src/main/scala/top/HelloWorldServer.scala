package top

import org.zeromq.ZMQ
import sample.Person
import sample.Person.{PhoneType, PhoneNumber}

object HelloWorldServer extends App {

  val context = ZMQ.context(1)
  val socket = context.socket(ZMQ.REP)
  socket.bind("tcp://*:5555")

  while (!Thread.currentThread.isInterrupted) {
    val reply = socket.recv(0)
    val person = Person.parseFrom(reply)
    println(s"Received : [$person]")
    val updatedPerson = person.addPhone(PhoneNumber("12345", Some(PhoneType.MOBILE)))
    socket.send(updatedPerson.toByteArray, 0)
    Thread.sleep(1000)
  }

  socket.close()
  context.term()
}
