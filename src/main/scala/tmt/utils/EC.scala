package tmt.utils

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

object EC {
  def singleThreadedEc() = ExecutionContext.fromExecutorService(
    Executors.newSingleThreadExecutor()
  )
}
