package tmt.app

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import tmt.actors.{McsHcdClient, McsHcdServerSingleton}
import tmt.apps.mcs.McsClient
import tmt.library.ConfigLoader
import tmt.reactivemq._
import tmt.utils.ActorRuntime

class Assembly(env: String, seedName: Option[String]) {

  import com.softwaremill.macwire._

  lazy val configLoader = wire[ConfigLoader]

  lazy val system = ActorSystem("ClusterSystem", configLoader.load(env, seedName))

  lazy implicit val ec     = system.dispatcher
  lazy implicit val mat    = ActorMaterializer()(system)

  lazy val runtime: ActorRuntime = wire[ActorRuntime]

  lazy val appSettings = wire[AppSettings]

  lazy val zmqClient = wire[ZmqClient]

  lazy val zmqServer = wire[ZmqServer]

  lazy val zmqPublisherFactory = wire[ZmqPublisherFactory]

  lazy val zmqSubscriberFactory = wire[ZmqSubscriberFactory]

  lazy val mcsClient = wire[McsClient]

  lazy val mcsHcdServerSingleton = wire[McsHcdServerSingleton]

  lazy val mcsHcdClient = wire[McsHcdClient]

  lazy val sampleClient = wire[SampleClient]
}
