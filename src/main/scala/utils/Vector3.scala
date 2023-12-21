package space.tiagodinis33.bridgetrainer
package utils

import org.bukkit

import scala.beans.BeanProperty
class Vector3(@BeanProperty var x: Double, @BeanProperty var y: Double, @BeanProperty var z: Double) {
  def this() = {
    this(0, 0, 0)
  }
  def >=(other: Vector3): Boolean = x >= other.x && y >= other.y & z >= other.z
  def <=(other: Vector3): Boolean = x <= other.x && y <= other.y && z <= other.z
  def to_bukkit_location(world_name: String): bukkit.Location = {
    val world = bukkit.Bukkit.getWorld(world_name);
    new bukkit.Location(world, x, y, z)
  }

  def to_bukkit_location_with_rotation(world_name: String, yaw: Int, pitch: Int): bukkit.Location = {
    val world = bukkit.Bukkit.getWorld(world_name);
    new bukkit.Location(world, x, y, z, yaw, pitch)
  }

  override def toString = s"Vector3(x=$x, y=$y, z=$z)"
}