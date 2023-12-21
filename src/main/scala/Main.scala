package space.tiagodinis33.bridgetrainer

import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginLogger
import org.bukkit.plugin.java.JavaPlugin
import space.tiagodinis33.bridgetrainer.config.load_config
import space.tiagodinis33.bridgetrainer.plot.{PendingPlot, Plot, PlotEventListener, PlotWithPlayerStarted, UnusedPlot}

import scala.jdk.CollectionConverters.ListHasAsScala
import scala.annotation.unused

@unused
class Main extends JavaPlugin {
  override def onEnable(): Unit = {
    this.saveDefaultConfig()
    val config = load_config(this.getConfig.saveToString())
    println(config)
    val plots = config.plots.asScala.iterator.map(UnusedPlot.apply).toVector
    global_plots = plots
    plots.zipWithIndex.foreach { (_, i) => getPluginManager.registerEvents(PlotEventListener(i), this) }
    getPluginManager.registerEvents(GlobalEventListener, this)
    Bukkit.getOnlinePlayers.forEach(introduce_player)
  }
}

var global_plots: Vector[Plot] = Vector.empty
def request_plot(): Option[(UnusedPlot, Int)] = global_plots.iterator.zipWithIndex.collectFirst { case (unused: UnusedPlot, id) => (unused, id) }
def introduce_player(player: Player): Unit =
  request_plot() match
    case Some((plot, id)) => global_plots = global_plots.updated(id, plot.set_current_player(player))
    case None => player.kickPlayer("§cSorry but all plots are busy.")