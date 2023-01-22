package dev.infernity.polarisplugin.polarisplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EverySecondTask extends BukkitRunnable {
    @Override
    public void run(){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getSaturation() > 20){
                player.setSaturation(20f);
            }
            if (player.getFoodLevel() > 20){
                player.setFoodLevel(20);
            }
        };
    }
}
