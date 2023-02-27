package dev.infernity.polarisplugin.polarisplugin;

import dev.infernity.polarisplugin.polarisplugin.commands.dieCommand;
import dev.infernity.polarisplugin.polarisplugin.commands.getHeadCommand;
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerInteractListener;
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerJoinListener;
import dev.infernity.polarisplugin.polarisplugin.tasks.EverySecondTask;
import dev.infernity.polarisplugin.polarisplugin.utilities.foodUtilities;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class PolarisPlugin extends JavaPlugin {
    // Builtin utilities that require the plugin //
    public static NamespacedKey createNamespacedKey(String keyName){
        return new NamespacedKey(getPlugin(), keyName);
    }

    private static PolarisPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        EverySecondTask task = new EverySecondTask();
        task.runTaskTimer(this, 0, 20);
        this.getLogger().log(Level.INFO, "Plugin up!");
        getServer().getPluginManager().registerEvents(new onPlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new onPlayerInteractListener(), this);
        getCommand("die").setExecutor(new dieCommand());
        getCommand("getHead").setExecutor(new getHeadCommand());
        foodUtilities.createFoods();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PolarisPlugin getPlugin(){
        return plugin;
    }
}
