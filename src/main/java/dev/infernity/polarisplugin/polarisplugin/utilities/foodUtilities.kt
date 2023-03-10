package dev.infernity.polarisplugin.polarisplugin.utilities

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType

object foodUtilities {
    fun createFoods() {
        val vanillaIceCream = itemUtilities.createPotionHead(
            ChatColor.WHITE.toString() + "Ice Cream " + ChatColor.GRAY + "(Vanilla)",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmRjOGViN2QxYjVjMzhiOWU0OTAyN2M3M2UzZWUxZWNhZDE4Mzc0NzBiODFiMzdkMTc4ZGI2M2MyOWVhMjUwZSJ9fX0=",
            PotionEffectType.SPEED, 100, 0, 2, 1f
        )
        val melonIceCream = itemUtilities.createPotionHead(
            ChatColor.WHITE.toString() + "Ice Cream " + ChatColor.GRAY + "(Melon)",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiN2VlNzY0MmFmODVhNGFiNzc0OGNjZTk2NjZjYzE0MWQ1ZDM5ZWYyY2YwZWI3MDQ0MzQ0MGZkNjdjMDQ3MiJ9fX0=",
            PotionEffectType.HEAL, 1, 0, 2, 1f
        )
        val chocolate = itemUtilities.createFoodHead(
            ChatColor.WHITE.toString() + "Chocolate",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE5Zjk0OGQxNzcxOGFkYWNlNWRkNmUwNTBjNTg2MjI5NjUzZmVmNjQ1ZDcxMTNhYjk0ZDE3YjYzOWNjNDY2In19fQ==",
            2,
            1f
        )
        Bukkit.addRecipe(
            itemUtilities.shapelessRecipeCreator(
                vanillaIceCream,
                "ice_cream_vanilla",
                arrayOf<ItemStack?>(ItemStack(Material.MILK_BUCKET), ItemStack(Material.SUGAR))
            )
        )
        Bukkit.addRecipe(
            itemUtilities.shapelessRecipeCreator(
                melonIceCream,
                "ice_cream_melon",
                arrayOf(vanillaIceCream, ItemStack(Material.GLISTERING_MELON_SLICE))
            )
        )
        Bukkit.addRecipe(
            itemUtilities.shapelessRecipeCreator(
                chocolate,
                "chocolate",
                arrayOf<ItemStack?>(ItemStack(Material.COCOA_BEANS), ItemStack(Material.SUGAR))
            )
        )
    }
}