package tmt.demo.hcd

import akka.actor.Actor
import akka.pattern.pipe
import tcsstr2.{Tcs_Command, command_response, mcs_DriveStatus, tcs_mcs_PositionDemand}
import tmt.app.configs.{AppSettings, Names}
import tmt.demo.connectors.{McsToClusterFlow, ClusterToMcsFlow}
import tmt.demo.zeromq_drivers.ZmqClient

class HcdActor(
  zmqClient: ZmqClient,
  clusterToMcsFlow: ClusterToMcsFlow,
  mcsToClusterFlow: McsToClusterFlow,
  appSettings: AppSettings
) extends Actor {

  import context.dispatcher

  override def preStart() = {

    //connection to push cluster tcs_mcs_PositionDemand events to mcs
    clusterToMcsFlow.connect[tcs_mcs_PositionDemand](
      subscriberTopic = Names.PositionDemands,
      publishingPort = appSettings.mcsPositionDemandPort
    )

    //connection to push mcs mcs_DriveStatus events to cluster
    mcsToClusterFlow.connect(
      publishingTopic = Names.DriveStatus,
      subscriberPort = appSettings.mcsDriveStatusPort,
      responseParser = mcs_DriveStatus
    )
  }

  //send tcs commands to mcs and reply back to sender
  def receive = {
    case command: Tcs_Command =>
      val result = zmqClient.query(command, command_response)
      result pipeTo sender()
  }
}

