package space.tiagodinis33.bridgetrainer
package plot

import org.bukkit
import org.bukkit.scheduler.BukkitRunnable
import space.tiagodinis33.bridgetrainer.config.PlotConfig

import scala.util.Random;

sealed trait Plot(val config: PlotConfig)

sealed trait UsedPlot extends Plot {
  final def remove_player(): UnusedPlot = {
    UnusedPlot(config)
  }
}

case class PendingPlot(plot_config: PlotConfig, player: bukkit.entity.Player) extends Plot(plot_config), UsedPlot {
  def start(): PlotWithPlayerStarted = {
    player.sendMessage("§e[BridgeTrainer] §aYou begun bridging, GO!")
    PlotWithPlayerStarted(plot_config, player, start_timestamp = System.currentTimeMillis)
  }
}

case class PlotWithPlayerStarted(plot_config: PlotConfig, player: bukkit.entity.Player, placed_blocks: Set[bukkit.Location] = Set.empty, start_timestamp: Long) extends Plot(plot_config), UsedPlot {


  def won(): PendingPlot = {
    val elapsed = (System.currentTimeMillis - start_timestamp) / 1000;
    player.sendMessage("§e[BridgeTrainer] §aGood job! You took " + elapsed + "s to complete the bridge!")
    stop()
  }

  def lost(): PendingPlot = {
    val elapsed = (System.currentTimeMillis - start_timestamp) / 1000;
    player.sendMessage("§e[BridgeTrainer] §cYou fell! §aYou took " + elapsed + "s!")
    stop()
  }

  def stop(): PendingPlot = {
    placed_blocks.foreach { block_location =>
      val block = block_location.getBlock
      val material = block.getType
      val data = block.getData
      block.setType(bukkit.Material.AIR)
      val falling_block = block.getWorld.spawnFallingBlock(block.getLocation, material, data)
      falling_block.setDropItem(false);
      falling_block.setVelocity(new bukkit.util.Vector((Random.nextGaussian() * 2.5)-1.25, (Random.nextGaussian() * 2.5)-1.25, (Random.nextGaussian() * 2.5)-1.25))
      val despawn_runnable = new BukkitRunnable {
        override def run(): Unit = {
          falling_block.remove()
        }
      };
      despawn_runnable.runTaskLater(bukkit.plugin.java.JavaPlugin.getPlugin(classOf[Main]), 20 * 3);
    }
    UnusedPlot(plot_config).set_current_player(player)
  }

  def add_placed_block(block: bukkit.block.Block): PlotWithPlayerStarted = {
    var new_placed_blocks = Set.from(this.placed_blocks)
    new_placed_blocks += block.getLocation;
    PlotWithPlayerStarted(plot_config, player, new_placed_blocks, start_timestamp)
  }
}

case class UnusedPlot(plot_config: PlotConfig) extends Plot(plot_config) {
  def set_current_player(player: bukkit.entity.Player): PendingPlot = {
    player.teleport(plot_config.spawn.to_bukkit_location(plot_config.world))
    player.getInventory.clear()
    player.getInventory.addItem(
      new bukkit.inventory.ItemStack(bukkit.Material.SANDSTONE, 64),
      new bukkit.inventory.ItemStack(bukkit.Material.SANDSTONE, 64),
      new bukkit.inventory.ItemStack(bukkit.Material.SANDSTONE, 64)
    )
    player.setHealth(player.getMaxHealth)
    PendingPlot(plot_config, player)
  }
}


