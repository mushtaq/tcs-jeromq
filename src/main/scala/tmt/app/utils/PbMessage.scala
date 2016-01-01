package tmt.app.utils

import com.trueaccord.scalapb.{GeneratedMessage, Message}

object PbMessage {
  type Of[T] = GeneratedMessage with Message[T]
}
