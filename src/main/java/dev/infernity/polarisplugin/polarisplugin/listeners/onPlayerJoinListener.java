package dev.infernity.polarisplugin.polarisplugin.listeners;

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getName() + " joined Polaris!");
        event.getPlayer().discoverRecipe(PolarisPlugin.createNamespacedKey("ice_cream_vanilla"));
    }
}