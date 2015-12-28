package tmt.library

import java.net.InetAddress

object IpInfo {
  def privateIpAndPort(env: String) = env match {
    case "prod" => (InetAddress.getLocalHost.getHostAddress, 2552)
    case _      => ("127.0.0.1", 0)
  }
}
