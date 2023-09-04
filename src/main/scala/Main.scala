package space.tiagodinis33.bridgetrainer

import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin

import scala.annotation.unused

@unused
class Main extends JavaPlugin {
  override def onEnable(): Unit = {
    getPluginManager.registerEvents(MyEventListener, this)
  }
}
