package tmt.demo.apps.event_subscriber

import caseapp._
import tcsstr2.mcs_DriveStatus
import tmt.app.configs.{Params, Names, Assembly}

object DemoApp extends AppOf[DemoAppInner] {
  def parser = default
}

case class DemoAppInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  eventSubscriber
    .subscribe[mcs_DriveStatus](Names.DriveStatus)
    .runForeach(x => println(s"******* ClientApp received $x at ${System.currentTimeMillis()}"))
    .onComplete { x =>
      println(s"completed with value: $x")
      runtime.shutdown()
    }

}
