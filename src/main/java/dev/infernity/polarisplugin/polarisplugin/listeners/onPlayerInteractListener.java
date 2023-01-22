package dev.infernity.polarisplugin.polarisplugin.listeners;

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin;
import dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static java.lang.Float.min;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class onPlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()){
            if (!(event.getItem() == null)){
                ItemStack item = event.getItem();
                ItemStack itemOne = item.asOne();
                Player player = event.getPlayer();
                if (itemOne.getType() == Material.PLAYER_HEAD){
                    if (itemUtilities.hasDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_flag"))){
                        PotionEffectType potionEffectType = PotionEffectType.getByName(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_effect")));
                        int duration = parseInt(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_duration")));
                        int amplifier = parseInt(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_amplifier")));
                        PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier);
                        potionEffect.apply(player);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        player.setFoodLevel(player.getFoodLevel() + parseInt(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_hunger"))));
                        player.setSaturation(
                                player.getSaturation() + parseFloat(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("potionhead_saturation")))
                        );
                        event.setCancelled(true);
                    } else if (itemUtilities.hasDataInItem(itemOne, PolarisPlugin.createNamespacedKey("foodhead_flag"))) {
                        if (!(player.getSaturation() >= 20f && player.getFoodLevel() >= 20)) {
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                            player.setFoodLevel(player.getFoodLevel() + parseInt(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("foodhead_hunger"))));
                            player.setSaturation(
                                    player.getSaturation() + parseFloat(itemUtilities.getDataInItem(itemOne, PolarisPlugin.createNamespacedKey("foodhead_saturation")))
                            );
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
