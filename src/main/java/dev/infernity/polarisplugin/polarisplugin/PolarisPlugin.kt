package dev.infernity.polarisplugin.polarisplugin

import dev.infernity.polarisplugin.polarisplugin.commands.dieCommand
import dev.infernity.polarisplugin.polarisplugin.commands.getHeadCommand
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerInteractListener
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerJoinListener
import dev.infernity.polarisplugin.polarisplugin.tasks.EverySecondTask
import dev.infernity.polarisplugin.polarisplugin.utilities.foodUtilities
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class PolarisPlugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        val task = EverySecondTask()
        task.runTaskTimer(this, 0, 20)
        logger.log(Level.INFO, "Plugin up!")
        server.pluginManager.registerEvents(onPlayerJoinListener(), this)
        server.pluginManager.registerEvents(onPlayerInteractListener(), this)
        getCommand("die")!!.setExecutor(dieCommand())
        getCommand("getHead")!!.setExecutor(getHeadCommand())
        foodUtilities.createFoods()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        // Builtin utilities that require the plugin //
        fun createNamespacedKey(keyName: String?): NamespacedKey {
            return NamespacedKey(plugin!!, keyName!!)
        }

        var plugin: PolarisPlugin? = null
            private set
    }
}