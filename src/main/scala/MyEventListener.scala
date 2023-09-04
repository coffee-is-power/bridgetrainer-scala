package space.tiagodinis33.bridgetrainer

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}

import scala.annotation.unused
@unused
object MyEventListener extends Listener {
  @unused
  @EventHandler
  def on_player_join(join_event: PlayerJoinEvent): () = {
    join_event.setJoinMessage("Bem vindo ao nosso servidor de ponte!");
  }
}
