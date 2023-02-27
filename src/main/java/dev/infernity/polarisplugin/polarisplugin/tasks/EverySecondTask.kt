package dev.infernity.polarisplugin.polarisplugin.tasks

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class EverySecondTask : BukkitRunnable() {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.saturation > 20) {
                player.saturation = 20f
            }
            if (player.foodLevel > 20) {
                player.foodLevel = 20
            }
        }
    }
}