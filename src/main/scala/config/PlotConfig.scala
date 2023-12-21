package space.tiagodinis33.bridgetrainer
package config

import space.tiagodinis33.bridgetrainer.utils.{AABB, Vector3}

import scala.beans.BeanProperty

class PlotConfig(@BeanProperty var bounds: AABB, @BeanProperty var spawn: Vector3, @BeanProperty var world: String) {
  def this() = {
    this(AABB(), Vector3(), "")
  }

  override def toString = s"PlotConfig(bounds=$bounds, spawn=$spawn, world=$world)"
}
