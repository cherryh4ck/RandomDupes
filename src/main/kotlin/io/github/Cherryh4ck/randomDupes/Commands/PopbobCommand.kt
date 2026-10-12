package io.github.Cherryh4ck.randomDupes.Commands

import io.github.Cherryh4ck.randomDupes.RandomDupes
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class PopbobCommand(private val plugin : RandomDupes) : TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            if (!plugin.popbobDupeIsEnabled) {
                sender.sendMessage(plugin.minimessage.deserialize("${plugin.prefix} <red>This dupe is disabled."))
                return true
            }

            if (plugin.popbobDupeNeedsPermission && !sender.hasPermission("randomdupes.popbob.dupe")) {
                sender.sendMessage(plugin.minimessage.deserialize("${plugin.prefix} <red>You lack permissions to do this."))
                return true
            }

            var item = sender.inventory.itemInMainHand
            if (item.type != Material.TNT_MINECART) {
                sender.sendMessage(plugin.minimessage.deserialize("${plugin.prefix} <red>You lack the main material to do this."))
                return true
            }

            item = sender.inventory.itemInOffHand
            if (item.type.isAir) {
                sender.sendMessage(plugin.minimessage.deserialize("${plugin.prefix} <red>You need an item in your offhand to do this."))
                return true
            }

            sender.inventory.setItemInMainHand(ItemStack(Material.AIR))
            val clone = item.clone()
            clone.amount = 1
            sender.world.dropItemNaturally(sender.location, clone)
        }
        else{
            sender.sendMessage(plugin.minimessage.deserialize("${plugin.prefix} <red>You must be a player to use this command."))
        }
        return true
    }

    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String> ): List<String?> {
        return emptyList()
    }
}