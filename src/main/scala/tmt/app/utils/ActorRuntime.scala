package tmt.app.utils

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.util.Timeout
import org.zeromq.ZMQ

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class ActorRuntime(_system: ActorSystem, _mat: Materializer, _ec: ExecutionContext) {
  implicit val system = _system
  implicit val mat    = _mat
  implicit val ec     = _ec
  implicit val timeout = Timeout(10.seconds)

  val zmqContext = ZMQ.context(1)

  def shutdown() = {
    system.terminate()
    zmqContext.close()
  }
}
