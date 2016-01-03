package tmt.demo.zeromq_drivers

import akka.stream.scaladsl.Source
import com.trueaccord.scalapb.GeneratedMessageCompanion
import org.zeromq.ZMQ
import tmt.app.configs.AppSettings
import tmt.app.utils.{PbMessage, EC, ActorRuntime}

import scala.concurrent.Future

class ZmqSubscriber[Msg <: PbMessage.Of[Msg]](
  port: Int,
  responseParser: GeneratedMessageCompanion[Msg],
  settings: AppSettings,
  runtime: ActorRuntime
) {

  import runtime._

  val address = s"tcp://${settings.mcsHostname}:$port"

  private val socket = zmqContext.socket(ZMQ.SUB)
  println(s"ZmqSubscriber connecting to $address")
  socket.connect(address)
  socket.subscribe(Array.empty)

  private val ec = EC.singleThreadedEc()

  val stream = {
    Source
      .repeat(())
      .mapAsync(1)(_ => receive())
      .map(bytes => responseParser.parseFrom(bytes))
      .map(x => {println(s"********* ZmqSubscriber received $x"); x})
  }

  private def receive() = Future {
    socket.recv(0)
  }(ec)

  def shutdown(): Unit = {
    socket.close()
    ec.shutdown()
  }
}

class ZmqSubscriberFactory(settings: AppSettings, runtime: ActorRuntime) {
  def make[Msg <: PbMessage.Of[Msg]](port: Int, responseParser: GeneratedMessageCompanion[Msg]) =
    new ZmqSubscriber[Msg](port, responseParser, settings, runtime)
}
