package reactivemq

import com.trueaccord.scalapb.{Message, GeneratedMessage}

object PbMessage {
  type Of[T] = GeneratedMessage with Message[T]
}
