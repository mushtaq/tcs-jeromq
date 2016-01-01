package tmt.app.configs

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import tmt.app.library.ConfigLoader
import tmt.demo.hcd_drivers.{CommandClient, EventPublisher, EventSubscriber}
import tmt.demo.hcds.{CommandServerHcdSingleton, EventSubscriberHcd, EventPublisherHcd}
import tmt.demo.zeromq_drivers._
import tmt.sample.SampleClient
import tmt.app.utils.ActorRuntime

class Assembly(params: Params) {

  import com.softwaremill.macwire._

  lazy val configLoader = new ConfigLoader(params)

  lazy val system = ActorSystem(params.system, configLoader.load())

  lazy implicit val ec     = system.dispatcher
  lazy implicit val mat    = ActorMaterializer()(system)

  lazy val runtime: ActorRuntime = wire[ActorRuntime]
  lazy val appSettings = wire[AppSettings]

  lazy val zmqClient = wire[ZmqClient]
  lazy val zmqServer = wire[ZmqServer]
  lazy val zmqPublisherFactory = wire[ZmqPublisherFactory]
  lazy val zmqSubscriberFactory = wire[ZmqSubscriberFactory]

  lazy val commandServerHcdSingleton = wire[CommandServerHcdSingleton]
  lazy val eventSubscriberHcd = wire[EventSubscriberHcd]
  lazy val eventPublisherHcd = wire[EventPublisherHcd]

  lazy val commandsClient = wire[CommandClient]
  lazy val eventPublisher = wire[EventPublisher]
  lazy val eventSubscriber = wire[EventSubscriber]

  lazy val sampleClient = wire[SampleClient]
}
