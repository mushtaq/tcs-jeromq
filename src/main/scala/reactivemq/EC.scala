package reactivemq

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

object EC {
  def singleThreadedEc() = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
}
