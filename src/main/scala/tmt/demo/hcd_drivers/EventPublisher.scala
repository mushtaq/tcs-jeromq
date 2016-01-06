package tmt.demo.hcd_drivers

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.stream.scaladsl.Source
import com.trueaccord.scalapb.GeneratedMessage
import tmt.app.utils.ActorRuntime

class EventPublisher(actorRuntime: ActorRuntime) {

  import actorRuntime._

  def publish[Msg <: GeneratedMessage](
    messages: Source[Msg, Any],
    topic: String
  ) = {
    messages.runForeach { message =>
      println(s"********* PublisherClient is publishing: $message")
      DistributedPubSub(system).mediator ! Publish(topic, message)
    }
  }
}
