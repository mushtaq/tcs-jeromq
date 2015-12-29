package tmt.mcs.clients

import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.stream.scaladsl.Source
import com.trueaccord.scalapb.GeneratedMessage
import tmt.utils.ActorRuntime

class PublisherClient(actorRuntime: ActorRuntime) {

  import actorRuntime._

  def publish[Msg <: GeneratedMessage](messages: Source[Msg, Any], topic: String) = {
    messages.runForeach { message =>
      println(s"********* PublisherClient is publishing: $message")
      mediator ! Publish(topic, message)
    }
  }
}
