package tmt.mcs.apps.subscribers

import caseapp._
import tcsstr2.mcs_DriveStatus
import tmt.app.{Assembly, Names, Params}

case class Client(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  subscriberClient
    .subscribe[mcs_DriveStatus](Names.DriveStatus)
    .runForeach(x => println(s"******* ClientApp received $x at ${System.currentTimeMillis()}"))
    .onComplete { x =>
      println(s"completed with value: $x")
      runtime.shutdown()
    }

}

object ClientApp extends AppOf[Client] {
  def parser = default
}
