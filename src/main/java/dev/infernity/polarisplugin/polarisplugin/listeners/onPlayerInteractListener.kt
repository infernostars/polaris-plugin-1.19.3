package dev.infernity.polarisplugin.polarisplugin.listeners

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin
import dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class onPlayerInteractListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action.isRightClick) {
            if (event.item != null) {
                val item = event.item
                val itemOne = item!!.asOne()
                val player = event.player
                if (itemOne.type == Material.PLAYER_HEAD) {
                    if (itemUtilities.hasDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("potionhead_flag")
                        )
                    ) {
                        val potionEffectType = PotionEffectType.getByName(
                            itemUtilities.getDataInItem(
                                itemOne,
                                PolarisPlugin.Companion.createNamespacedKey("potionhead_effect")
                            )!!
                        )
                        val duration = itemUtilities.getDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("potionhead_duration")
                        )!!
                            .toInt()
                        val amplifier = itemUtilities.getDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("potionhead_amplifier")
                        )!!
                            .toInt()
                        val potionEffect = PotionEffect(potionEffectType!!, duration, amplifier)
                        potionEffect.apply(player)
                        player.inventory.itemInMainHand.amount = player.inventory.itemInMainHand.amount - 1
                        player.foodLevel = player.foodLevel + itemUtilities.getDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("potionhead_hunger")
                        )!!
                            .toInt()
                        player.saturation = player.saturation + itemUtilities.getDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("potionhead_saturation")
                        )!!
                            .toFloat()
                        event.isCancelled = true
                    } else if (itemUtilities.hasDataInItem(
                            itemOne,
                            PolarisPlugin.Companion.createNamespacedKey("foodhead_flag")
                        )
                    ) {
                        if (!(player.saturation >= 20f && player.foodLevel >= 20)) {
                            player.inventory.itemInMainHand.amount = player.inventory.itemInMainHand.amount - 1
                            player.foodLevel = player.foodLevel + itemUtilities.getDataInItem(
                                itemOne,
                                PolarisPlugin.Companion.createNamespacedKey("foodhead_hunger")
                            )!!
                                .toInt()
                            player.saturation = player.saturation + itemUtilities.getDataInItem(
                                itemOne,
                                PolarisPlugin.Companion.createNamespacedKey("foodhead_saturation")
                            )!!
                                .toFloat()
                        }
                        event.isCancelled = true
                    }
                }
            }
        }
    }
}