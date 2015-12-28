package tmt.library

import java.net.InetAddress

object IpInfo {
  def privateIp(env: String) = env match {
    case "prod" => InetAddress.getLocalHost.getHostAddress
    case _      => "127.0.0.1"
  }
}
