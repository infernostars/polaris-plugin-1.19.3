package dev.infernity.polarisplugin.polarisplugin.utilities;

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

public class itemUtilities {
    public static ItemStack createHeadBase64(String base64) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        /* so items can stack, we want to make same uuid each time */
        UUID id = new UUID(
                base64.substring(base64.length() - 20).hashCode(),
                base64.substring(base64.length() - 10).hashCode()
        );
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setPlayerProfile((PlayerProfile) Bukkit.createProfile(id, null));
        PlayerProfile playerProfile = skullMeta.getPlayerProfile();
        playerProfile.setProperty(new ProfileProperty("textures", base64));
        skullMeta.setPlayerProfile(playerProfile);
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack storeDataInItem(ItemStack item, NamespacedKey key, String str) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, str);
        item.setItemMeta(meta);
        return item;
    }

    public static String getDataInItem(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(key, PersistentDataType.STRING);
    }

    public static Boolean hasDataInItem(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.has(key);
    }

    @SuppressWarnings("deprecation") // components are no
    public static ItemStack renameItem(ItemStack item, String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    @SuppressWarnings("deprecation") // no i am not learning Components, paper
    public static ItemStack createPotionHead(String base64, PotionEffectType potionEffect, String duration, String amplifier, String hunger, String saturation) {
        ItemStack item = itemUtilities.createHeadBase64(base64);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_flag"), "1");
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_effect"), potionEffect.getName());
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_duration"), duration);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_amplifier"), amplifier);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_hunger"), hunger);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_saturation"), saturation);
        ItemMeta itemMeta = item.getItemMeta();
        String durRoman = "";
        try {
            durRoman = intUtilities.IntegerToRomanNumeral(parseInt(amplifier) + 1);
        } catch(Exception e) {
            durRoman = "I";
        }
        String[] lore = {ChatColor.YELLOW + stringUtilities.toDisplayCase(potionEffect.getName()) + " " + durRoman + " " + ChatColor.GRAY + "(" + (parseFloat(duration) / 20) + " seconds)",
                ChatColor.GRAY + "(" + parseFloat(hunger)/2 + " hunger bars)"};
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    @SuppressWarnings("deprecation") // no i am not learning Components, paper
    public static ItemStack createFoodHead(String base64, String hunger, String saturation) {
        ItemStack item = itemUtilities.createHeadBase64(base64);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_flag"), "1");
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_hunger"), hunger);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_saturation"), saturation);
        ItemMeta itemMeta = item.getItemMeta();
        String[] lore = {ChatColor.GRAY + "(" + parseFloat(hunger)/2 + " hunger bars)"};
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

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
        NamespacedKey key = PolarisPlugin.createNamespacedKey(keyString);
        ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        for(ItemStack itemStack : recipeItems){
            recipe.addIngredient(itemStack);
        }
        return recipe;
    }
}
