package space.tiagodinis33.bridgetrainer
package utils

import scala.beans.BeanProperty

class AABB(@BeanProperty var min: Vector3, @BeanProperty var max: Vector3) {

  def this()= {
    this(Vector3(), Vector3())
  }
  require(min >= max, "`min` must be greater or equal to `max`")
  def contains(point: Vector3): Boolean = {
    point >= min && point <= max
  }

  def intersects(other: AABB): Boolean = {
    !(other.min.x > max.x || other.max.x < min.x ||
      other.min.y > max.y || other.max.y < min.y ||
      other.min.z > max.z || other.max.z < min.z)
  }

  def union(other: AABB): AABB = {
    val newMin = Vector3(Math.min(min.x, other.min.x), Math.min(min.y, other.min.y), Math.min(min.z, other.min.z))
    val newMax = Vector3(Math.max(max.x, other.max.x), Math.max(max.y, other.max.y), Math.max(max.z, other.max.z))
    new AABB(newMin, newMax)
  }

  override def toString = s"AABB(min=$min, max=$max)"
}
