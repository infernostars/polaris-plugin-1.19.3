package dev.infernity.polarisplugin.polarisplugin.utilities;

import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin;
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
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



/**
 * Utilities relating to ItemStacks.
 */
public class itemUtilities {
    /**
     * Create a player head with a base64 texture
     * @param base64 A base64-encoded string to get the head texture from.
     * @return An ItemStack containing a player head.
     * @since 0.1
     */
    public static ItemStack createHeadBase64(String base64) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        /* so items can stack, we want to make same uuid each time */
        UUID id = UUID.randomUUID();
        try {
            id = new UUID(
                    base64.substring(base64.length() - 20).hashCode(),
                    base64.substring(base64.length() - 10).hashCode()
            );
        } catch (Exception e) {
            /* if we got an error earlier, just keep the random uuid */
        }
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setPlayerProfile((PlayerProfile) Bukkit.createProfile(id, null));
        PlayerProfile playerProfile = skullMeta.getPlayerProfile();
        playerProfile.setProperty(new ProfileProperty("textures", base64));
        skullMeta.setPlayerProfile(playerProfile);
        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     * Store data in an ItemStack.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities#getDataInItem(ItemStack, NamespacedKey)
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities#hasDataInItem(ItemStack, NamespacedKey)
     * @param item The ItemStack to store the data in.
     * @param key A key to store the data as.
     * @param str The data to be stored.
     * @return An ItemStack with stored data.
     * @since 0.1
     */
    public static ItemStack storeDataInItem(ItemStack item, NamespacedKey key, String str) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, str);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Get data stored in an item.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities#storeDataInItem(ItemStack, NamespacedKey, String)
     * @param item The ItemStack where the data is stored.
     * @param key The NamespacedKey to get the String from.
     * @return A string, containing the data.
     * @since 0.1
     */
    public static String getDataInItem(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.get(key, PersistentDataType.STRING);
    }

    /**
     * Check if data is stored in an item.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities#storeDataInItem(ItemStack, NamespacedKey, String)
     * @param item The ItemStack where the data is stored.
     * @param key The NamespacedKey to check in.
     * @return A Boolean, saying if the data is there or not.
     * @since 0.1
     */
    public static Boolean hasDataInItem(ItemStack item, NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        return data.has(key);
    }

    /**
     * Rename an ItemStack.
     * @param item An ItemStack to rename.
     * @param name A string to rename the item to.
     * @return A renamed ItemStack.
     * @since 0.1
     */
    @SuppressWarnings("deprecation") // no I am not learning Components, paper
    public static ItemStack renameItem(ItemStack item, String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates an ItemStack player head, which has stored data usable on right click. See also for where it is checked in.
     * @see onPlayerInteractListener#onPlayerInteract(PlayerInteractEvent)
     * @param name The name of the item.
     * @param base64 A base64-encoded String to get the head texture from.
     * @param potionEffect A PotionEffectType for what potion effect it is.
     * @param duration An int for how long the potion effect lasts. This is in ticks!
     * @param amplifier An int for how long the potion effect lasts. Starts at zero.
     * @param hunger An int for how much hunger bars the head gives you.
     * @param saturation A float for how much saturation the head gives you.
     * @return an ItemStack.
     * @since 0.1
     */
    @SuppressWarnings("deprecation") // no I am not learning Components, paper
    public static ItemStack createPotionHead(String name, String base64, PotionEffectType potionEffect, int duration, int amplifier, int hunger, float saturation) {
        ItemStack item = itemUtilities.createHeadBase64(base64);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_flag"), "1");
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_effect"), potionEffect.getName());
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_duration"), Integer.toString(duration));
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_amplifier"), Integer.toString(amplifier));
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_hunger"), Integer.toString(hunger));
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("potionhead_saturation"), Float.toString(saturation));
        ItemMeta itemMeta = item.getItemMeta();
        String durRoman = "";
        try {
            durRoman = intUtilities.IntegerToRomanNumeral(amplifier + 1);
        } catch(Exception e) {
            durRoman = "I";
        }
        String[] lore = {ChatColor.YELLOW + stringUtilities.toDisplayCase(potionEffect.getName()) + " " + durRoman + " " + ChatColor.GRAY + "(" + ((float) duration / 20f) + " seconds)",
                ChatColor.GRAY + "(" + hunger/2 + " hunger bars)"};
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        item = itemUtilities.renameItem(item, name);
        return item;
    }

    /**
     * Creates an ItemStack player head, which has stored data usable on right click. See also for where it is checked in.
     * @see onPlayerInteractListener#onPlayerInteract(PlayerInteractEvent)
     * @param name The name of the item.
     * @param base64 A base64-encoded String to get the head texture from.
     * @param hunger An int for how much hunger bars the head gives you.
     * @param saturation A float for how much saturation the head gives you.
     * @return an ItemStack.
     * @since 0.1
     */
    @SuppressWarnings("deprecation") // no I am not learning Components, paper
    public static ItemStack createFoodHead(String name, String base64, int hunger, float saturation) {
        ItemStack item = itemUtilities.createHeadBase64(base64);
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_flag"), "1");
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_hunger"), Integer.toString(hunger));
        item = itemUtilities.storeDataInItem(item, PolarisPlugin.createNamespacedKey("foodhead_saturation"), Float.toString(saturation));
        ItemMeta itemMeta = item.getItemMeta();
        String[] lore = {ChatColor.GRAY + "(" + hunger/2 + " hunger bars)"};
        itemMeta.setLore(List.of(lore));
        item.setItemMeta(itemMeta);
        item = itemUtilities.renameItem(item, name);
        return item;
    }

    /**
     * Creates a ShapelessRecipe for creating an item's recipe.
     * @param item An ItemStack for the result.
     * @param keyString A string. This creates a key on its own. Make sure there are no conflicts!
     * @param recipeItems A list of ItemStacks for the items to create the recipe (max 9).
     * @return A ShapelessRecipe, the recipe for the item.
     */
    public static ShapelessRecipe shapelessRecipeCreator(ItemStack item, String keyString, ItemStack[] recipeItems){
        NamespacedKey key = PolarisPlugin.createNamespacedKey(keyString);
        ShapelessRecipe recipe = new ShapelessRecipe(key, item);
        for(ItemStack itemStack : recipeItems){
            recipe.addIngredient(itemStack);
        }
        return recipe;
    }
}
