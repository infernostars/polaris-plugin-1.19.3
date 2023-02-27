package dev.infernity.polarisplugin.polarisplugin.commands

import dev.infernity.polarisplugin.polarisplugin.utilities.itemUtilities
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class getHeadCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (sender.hasPermission("polarisPlugin.gethead")) {
                val p = sender
                val head = itemUtilities.createHeadBase64(args[0])
                p.inventory.addItem(head!!)
                p.sendMessage("§aHead created!")
            } else {
                sender.sendMessage("§cYou cannot make a head!")
            }
        }
        return false
    }
}