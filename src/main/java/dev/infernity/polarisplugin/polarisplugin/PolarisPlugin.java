package dev.infernity.polarisplugin.polarisplugin;

import dev.infernity.polarisplugin.polarisplugin.commands.dieCommand;
import dev.infernity.polarisplugin.polarisplugin.commands.getHeadCommand;
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerInteractListener;
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerJoinListener;
import dev.infernity.polarisplugin.polarisplugin.tasks.EverySecondTask;
import dev.infernity.polarisplugin.polarisplugin.tasks.EveryTickTask;
import dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Level;


public final class PolarisPlugin extends JavaPlugin {
    // Builtin utilities that require the plugin //
    public static NamespacedKey createNamespacedKey(String keyName){
        return new NamespacedKey(getPlugin(), keyName);
    }

    private static PolarisPlugin plugin;

    public static ItemStack potionHeadItem(String name, String base64, PotionEffectType effect, String duration, String amplifier, String hunger, String saturation){
        ItemStack item = itemUtilities.createPotionHead(base64, effect, duration, amplifier, hunger, saturation);
        item = itemUtilities.renameItem(item, name);
        return item;
    }
    public static ItemStack foodHeadItem(String name, String base64, String hunger, String saturation){
        ItemStack item = itemUtilities.createFoodHead(base64, hunger, saturation);
        item = itemUtilities.renameItem(item, name);
        return item;
    }

    public static ShapelessRecipe shapelessRecipeCreator(ItemStack item, String keyString, ItemStack[] recipeItems){
        NamespacedKey key = createNamespacedKey(keyString);
        ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        for(ItemStack itemStack : recipeItems){
            recipe.addIngredient(itemStack);
        }
        return recipe;
    }

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
        ItemStack vanillaIceCream = potionHeadItem(ChatColor.WHITE + "Ice Cream " + ChatColor.GRAY  + "(Vanilla)",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmRjOGViN2QxYjVjMzhiOWU0OTAyN2M3M2UzZWUxZWNhZDE4Mzc0NzBiODFiMzdkMTc4ZGI2M2MyOWVhMjUwZSJ9fX0=",
                PotionEffectType.SPEED, "90", "0", "2", "1");
        ItemStack melonIceCream = potionHeadItem(ChatColor.WHITE + "Ice Cream " + ChatColor.GRAY  + "(Melon)",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiN2VlNzY0MmFmODVhNGFiNzc0OGNjZTk2NjZjYzE0MWQ1ZDM5ZWYyY2YwZWI3MDQ0MzQ0MGZkNjdjMDQ3MiJ9fX0=",
                PotionEffectType.HEAL, "1", "0", "2", "1");
        ItemStack chocolate = foodHeadItem(ChatColor.WHITE + "Chocolate", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE5Zjk0OGQxNzcxOGFkYWNlNWRkNmUwNTBjNTg2MjI5NjUzZmVmNjQ1ZDcxMTNhYjk0ZDE3YjYzOWNjNDY2In19fQ==", "2", "1");
        Bukkit.addRecipe(shapelessRecipeCreator(vanillaIceCream, "ice_cream_vanilla", new ItemStack[]{new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.SUGAR)}));
        Bukkit.addRecipe(shapelessRecipeCreator(melonIceCream, "ice_cream_melon", new ItemStack[]{vanillaIceCream, new ItemStack(Material.GLISTERING_MELON_SLICE)}));
        Bukkit.addRecipe(shapelessRecipeCreator(chocolate, "chocolate", new ItemStack[]{new ItemStack(Material.COCOA_BEANS), new ItemStack(Material.SUGAR)}));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PolarisPlugin getPlugin(){
        return plugin;
    }
}
