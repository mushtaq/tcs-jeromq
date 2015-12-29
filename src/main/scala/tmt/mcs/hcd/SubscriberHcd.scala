package tmt.mcs.hcd

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import com.trueaccord.scalapb.GeneratedMessageCompanion
import tmt.mcs.reactivemq.ZmqSubscriberFactory
import tmt.utils.{ActorRuntime, PbMessage}

class SubscriberHcd(actorRuntime: ActorRuntime, zmqSubscriberFactory: ZmqSubscriberFactory) {
  import actorRuntime._

  def connect[Msg <: PbMessage.Of[Msg]](
    publishingTopic: String,
    subscriberPort: Int,
    responseParser: GeneratedMessageCompanion[Msg]
  ) = {
    val zmqSubscriber = zmqSubscriberFactory.make(subscriberPort, responseParser)
    zmqSubscriber.stream
      .runForeach { message =>
        println(s"***************** SubscriberHcd received $message")
        DistributedPubSub(system).mediator ! Publish(publishingTopic, message)
      }.onComplete { x =>
        zmqSubscriber.shutdown()
      }
  }
}
