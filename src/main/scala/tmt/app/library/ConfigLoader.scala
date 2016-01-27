package tmt.app.library

import java.net.InetAddress

import com.typesafe.config._
import tmt.app.configs.Params
import tmt.app.library.ConfigObjectExtensions.RichConfig
import collection.JavaConverters._

class ConfigLoader(params: Params) {

  def load() = config.withFallback(binding).resolve()

  def config = ConfigFactory.load(
    params.configName,
    ConfigParseOptions.defaults(),
    ConfigResolveOptions.defaults().setAllowUnresolved(true)
  )

  def binding = {
    val bindingConfig = ConfigFactory.empty()
      .withPair("hostname", InetAddress.getLocalHost.getHostAddress)
      .withPair("port", Integer.valueOf(params.port))
      .withPair("roles", params.roles.asJava)

    ConfigFactory.empty().withValue("binding", bindingConfig.root())
  }

}
