package tmt.demo.connectors

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import com.trueaccord.scalapb.GeneratedMessageCompanion
import tmt.app.utils.{ActorRuntime, PbMessage}
import tmt.demo.zeromq_drivers.ZmqSubscriberFactory

class McsToClusterFlow(actorRuntime: ActorRuntime, zmqSubscriberFactory: ZmqSubscriberFactory) {
  import actorRuntime._

  def connect[Msg <: PbMessage.Of[Msg]](
    publishingTopic: String,
    subscriberPort: Int,
    responseParser: GeneratedMessageCompanion[Msg]
  ) = {
    val zmqSubscriber = zmqSubscriberFactory.make(subscriberPort, responseParser)
    zmqSubscriber.stream
      .runForeach { message =>
        DistributedPubSub(system).mediator ! Publish(publishingTopic, message)
      }.onComplete { x =>
        zmqSubscriber.shutdown()
      }
  }
}
