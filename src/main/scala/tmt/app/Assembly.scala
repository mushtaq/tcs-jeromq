package tmt.app

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import tmt.library.ConfigLoader
import tmt.mcs.clients.CommandsClient
import tmt.mcs.hcd.CommandsHcdSingleton
import tmt.mcs.reactivemq._
import tmt.sample.SampleClient
import tmt.utils.ActorRuntime

class Assembly(params: Params) {

  import com.softwaremill.macwire._

  lazy val configLoader = new ConfigLoader(params)

  lazy val system = ActorSystem("ClusterSystem", configLoader.load())

  lazy implicit val ec     = system.dispatcher
  lazy implicit val mat    = ActorMaterializer()(system)

  lazy val runtime: ActorRuntime = wire[ActorRuntime]

  lazy val appSettings = wire[AppSettings]

  lazy val zmqClient = wire[ZmqClient]

  lazy val zmqServer = wire[ZmqServer]

  lazy val zmqPublisherFactory = wire[ZmqPublisherFactory]

  lazy val zmqSubscriberFactory = wire[ZmqSubscriberFactory]

  lazy val mcsHcdServerSingleton = wire[CommandsHcdSingleton]

  lazy val mcsHcdClient = wire[CommandsClient]

  lazy val sampleClient = wire[SampleClient]
}
