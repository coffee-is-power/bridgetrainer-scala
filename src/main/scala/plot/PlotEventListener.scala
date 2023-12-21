package space.tiagodinis33.bridgetrainer
package plot

import utils.Vector3

import org.bukkit

import scala.annotation.unused

class PlotEventListener(plot_index: Int) extends bukkit.event.Listener {
  private def current_plot(): Plot = global_plots(plot_index)
  private def set_current_plot(plot: Plot): Unit = global_plots = global_plots.updated(plot_index, plot)
  @bukkit.event.EventHandler
  def on_player_move(event: bukkit.event.player.PlayerMoveEvent): Unit = {
    val new_plot = current_plot() match {
      case usedPlot: UsedPlot if !usedPlot.config.bounds.contains(Vector3(event.getTo.getX, event.getTo.getY, event.getTo.getZ)) =>
        usedPlot match {
          case pending: PendingPlot if pending.player.getUniqueId.equals(event.getPlayer.getUniqueId) =>
            pending.remove_player().set_current_player(pending.player)
          case started: PlotWithPlayerStarted if started.player.getUniqueId.equals(event.getPlayer.getUniqueId) =>
            started.lost()
          case _ => usedPlot
        }
      case plot => plot
    }
    set_current_plot(new_plot)
  }

  @bukkit.event.EventHandler
  def on_player_quit(event: bukkit.event.player.PlayerQuitEvent): Unit = {
    val new_plot = current_plot() match {
      case pending: PendingPlot if pending.player.getUniqueId.equals(event.getPlayer.getUniqueId) => pending.remove_player()
      case started: PlotWithPlayerStarted if started.player.getUniqueId.equals(event.getPlayer.getUniqueId) => started.stop()
      case plot => plot
    }
    set_current_plot(new_plot)
  }

  @bukkit.event.EventHandler
  def on_pressure_plate_press(e: bukkit.event.player.PlayerInteractEvent): Unit = {
    val new_plot = current_plot() match {
      case started: PlotWithPlayerStarted
        if started.player.getUniqueId.equals(e.getPlayer.getUniqueId)
          && e.getAction == bukkit.event.block.Action.PHYSICAL
          && e.getClickedBlock.getType == bukkit.Material.GOLD_PLATE => started.won()
      case plot => plot
    }
    set_current_plot(new_plot)
  }

  @bukkit.event.EventHandler
  def on_block_place(e: bukkit.event.block.BlockPlaceEvent): Unit = {

    val new_plot = current_plot() match {
      case pending_plot: PendingPlot
        if pending_plot.player.getUniqueId.equals(e.getPlayer.getUniqueId) =>
        pending_plot.start().add_placed_block(e.getBlock)
      case started: PlotWithPlayerStarted
        if started.player.getUniqueId.equals(e.getPlayer.getUniqueId) =>
        started.add_placed_block(e.getBlock)
      case plot => plot
    }
    if !new_plot.config.bounds.contains(Vector3(e.getBlock.getX, e.getBlock.getY, e.getBlock.getZ)) then {
      e.setCancelled(true);
      e.getPlayer.sendMessage("§c[BridgeTrainer] You cannot place blocks out of bounds")
      return
    }
    set_current_plot(new_plot)
  }
}

