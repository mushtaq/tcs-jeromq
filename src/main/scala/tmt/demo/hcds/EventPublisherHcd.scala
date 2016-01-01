package tmt.demo.hcds

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import com.trueaccord.scalapb.GeneratedMessageCompanion
import tmt.demo.zeromq_drivers.ZmqSubscriberFactory
import tmt.app.utils.{ActorRuntime, PbMessage}

class EventPublisherHcd(actorRuntime: ActorRuntime, zmqSubscriberFactory: ZmqSubscriberFactory) {
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
