package dev.infernity.polarisplugin.polarisplugin.utilities

import com.destroystokyo.paper.profile.ProfileProperty
import dev.infernity.polarisplugin.polarisplugin.PolarisPlugin
import dev.infernity.polarisplugin.polarisplugin.listeners.onPlayerInteractListener
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.List

/**
 * Utilities relating to ItemStacks.
 */
object itemUtilities {
    /**
     * Create a player head with a base64 texture
     * @param base64 A base64-encoded string to get the head texture from.
     * @return An ItemStack containing a player head.
     * @since 0.1
     */
    fun createHeadBase64(base64: String): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        /* so items can stack, we want to make same uuid each time */
        var id = UUID.randomUUID()
        try {
            id = UUID(
                base64.substring(base64.length - 20).hashCode().toLong(),
                base64.substring(base64.length - 10).hashCode()
                    .toLong()
            )
        } catch (e: Exception) {
            /* if we got an error earlier, just keep the random uuid */
        }
        val skullMeta = skull.itemMeta as SkullMeta
        skullMeta.playerProfile = Bukkit.createProfile(id, null)
        val playerProfile = skullMeta.playerProfile
        playerProfile!!.setProperty(ProfileProperty("textures", base64))
        skullMeta.playerProfile = playerProfile
        skull.itemMeta = skullMeta
        return skull
    }

    /**
     * Store data in an ItemStack.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities.getDataInItem
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities.hasDataInItem
     * @param item The ItemStack to store the data in.
     * @param key A key to store the data as.
     * @param str The data to be stored.
     * @return An ItemStack with stored data.
     * @since 0.1
     */
    fun storeDataInItem(item: ItemStack, key: NamespacedKey?, str: String): ItemStack {
        val meta = item.itemMeta
        val data = meta.persistentDataContainer
        data.set(key!!, PersistentDataType.STRING, str)
        item.itemMeta = meta
        return item
    }

    /**
     * Get data stored in an item.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities.storeDataInItem
     * @param item The ItemStack where the data is stored.
     * @param key The NamespacedKey to get the String from.
     * @return A string, containing the data.
     * @since 0.1
     */
    fun getDataInItem(item: ItemStack, key: NamespacedKey?): String? {
        val meta = item.itemMeta
        val data = meta.persistentDataContainer
        return data.get(key!!, PersistentDataType.STRING)
    }

    /**
     * Check if data is stored in an item.
     * @see dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities.storeDataInItem
     * @param item The ItemStack where the data is stored.
     * @param key The NamespacedKey to check in.
     * @return A Boolean, saying if the data is there or not.
     * @since 0.1
     */
    fun hasDataInItem(item: ItemStack, key: NamespacedKey?): Boolean {
        val meta = item.itemMeta
        val data = meta.persistentDataContainer
        return data.has(key!!)
    }

    /**
     * Rename an ItemStack.
     * @param item An ItemStack to rename.
     * @param name A string to rename the item to.
     * @return A renamed ItemStack.
     * @since 0.1
     */
    @Suppress("deprecation") // no I am not learning Components, paper
    fun renameItem(item: ItemStack, name: String?): ItemStack {
        val meta = item.itemMeta
        meta.setDisplayName(name)
        item.itemMeta = meta
        return item
    }

    /**
     * Creates an ItemStack player head, which has stored data usable on right click. See also for where it is checked in.
     * @see onPlayerInteractListener.onPlayerInteract
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
    @Suppress("deprecation") // no I am not learning Components, paper
    fun createPotionHead(
        name: String?,
        base64: String,
        potionEffect: PotionEffectType,
        duration: Int,
        amplifier: Int,
        hunger: Int,
        saturation: Float
    ): ItemStack {
        var item = createHeadBase64(base64)
        item = storeDataInItem(item, PolarisPlugin.Companion.createNamespacedKey("potionhead_flag"), "1")
        item =
            storeDataInItem(item, PolarisPlugin.Companion.createNamespacedKey("potionhead_effect"), potionEffect.name)
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("potionhead_duration"),
            Integer.toString(duration)
        )
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("potionhead_amplifier"),
            Integer.toString(amplifier)
        )
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("potionhead_hunger"),
            Integer.toString(hunger)
        )
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("potionhead_saturation"),
            java.lang.Float.toString(saturation)
        )
        val itemMeta = item.itemMeta
        var durRoman: String? = ""
        durRoman = try {
            intUtilities.IntegerToRomanNumeral(amplifier + 1)
        } catch (e: Exception) {
            "I"
        }
        val lore = arrayOf(
            ChatColor.YELLOW.toString() + stringUtilities.toDisplayCase(potionEffect.name) + " " + durRoman + " " + ChatColor.GRAY + "(" + duration.toFloat() / 20f + " seconds)",
            ChatColor.GRAY.toString() + "(" + hunger / 2 + " hunger bars)"
        )
        itemMeta.lore = List.of(*lore)
        item.setItemMeta(itemMeta)
        item = renameItem(item, name)
        return item
    }

    /**
     * Creates an ItemStack player head, which has stored data usable on right click. See also for where it is checked in.
     * @see onPlayerInteractListener.onPlayerInteract
     * @param name The name of the item.
     * @param base64 A base64-encoded String to get the head texture from.
     * @param hunger An int for how much hunger bars the head gives you.
     * @param saturation A float for how much saturation the head gives you.
     * @return an ItemStack.
     * @since 0.1
     */
    @Suppress("deprecation") // no I am not learning Components, paper
    fun createFoodHead(name: String?, base64: String, hunger: Int, saturation: Float): ItemStack {
        var item = createHeadBase64(base64)
        item = storeDataInItem(item, PolarisPlugin.Companion.createNamespacedKey("foodhead_flag"), "1")
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("foodhead_hunger"),
            Integer.toString(hunger)
        )
        item = storeDataInItem(
            item,
            PolarisPlugin.Companion.createNamespacedKey("foodhead_saturation"),
            java.lang.Float.toString(saturation)
        )
        val itemMeta = item.itemMeta
        val lore = arrayOf(ChatColor.GRAY.toString() + "(" + hunger / 2 + " hunger bars)")
        itemMeta.lore = List.of(*lore)
        item.setItemMeta(itemMeta)
        item = renameItem(item, name)
        return item
    }

    /**
     * Creates a ShapelessRecipe for creating an item's recipe.
     * @param item An ItemStack for the result.
     * @param keyString A string. This creates a key on its own. Make sure there are no conflicts!
     * @param recipeItems A list of ItemStacks for the items to create the recipe (max 9).
     * @return A ShapelessRecipe, the recipe for the item.
     */
    fun shapelessRecipeCreator(item: ItemStack?, keyString: String?, recipeItems: Array<ItemStack?>): ShapelessRecipe {
        val key: NamespacedKey = PolarisPlugin.Companion.createNamespacedKey(keyString)
        val recipe = ShapelessRecipe(key, item!!)
        for (itemStack in recipeItems) {
            recipe.addIngredient(itemStack!!)
        }
        return recipe
    }
}