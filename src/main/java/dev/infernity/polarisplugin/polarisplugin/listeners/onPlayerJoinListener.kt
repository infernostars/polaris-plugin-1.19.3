package dev.infernity.polarisplugin.polarisplugin.listeners

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class onPlayerJoinListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage(Component.text(event.player.name + " joined Polaris!"))
        event.player.discoverRecipe(PolarisPlugin.Companion.createNamespacedKey("ice_cream_vanilla"))
    }
}