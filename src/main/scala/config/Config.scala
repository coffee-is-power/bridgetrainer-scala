package space.tiagodinis33.bridgetrainer
package config

import org.yaml.snakeyaml.Yaml

import java.util
import scala.Array.copy
import scala.beans.BeanProperty
import scala.io.Source
import scala.util.Try


class Config(@BeanProperty var plots: util.List[PlotConfig]) {
  def this() = {
    this(new util.ArrayList())
  }

  override def toString = s"Config(plots=$plots)"
}

def load_config(config_contents: String): Config = {
  new Yaml().loadAs(config_contents, classOf[Config])
}
