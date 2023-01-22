package dev.infernity.polarisplugin.polarisplugin.commands;

import dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class getHeadCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("polarisPlugin.gethead")) {
                Player p = (Player) sender;
                ItemStack head = itemUtilities.createHeadBase64(args[0]);
                p.getInventory().addItem(head);
                p.sendMessage("§aHead created!");
            } else {
                Player p = (Player) sender;
                p.sendMessage("§cYou cannot make a head!");
            }
        }
        return false;
    }
}
