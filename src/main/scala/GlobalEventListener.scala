package space.tiagodinis33.bridgetrainer

import org.bukkit

object GlobalEventListener extends bukkit.event.Listener {
  @bukkit.event.EventHandler
  def on_damage(e: bukkit.event.entity.EntityDamageEvent): Unit = e.setCancelled(true)

  @bukkit.event.EventHandler
  def on_falling_block_collision(e: bukkit.event.entity.EntityChangeBlockEvent): Unit =
    if (e.getEntity.isInstanceOf[bukkit.entity.FallingBlock]) e.setCancelled(true)
  @bukkit.event.EventHandler
  def on_join(e: bukkit.event.player.PlayerJoinEvent): Unit = {
    introduce_player(e.getPlayer)
  }
}
