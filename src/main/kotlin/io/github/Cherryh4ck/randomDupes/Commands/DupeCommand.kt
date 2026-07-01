package io.github.Cherryh4ck.randomDupes.Commands

import io.github.Cherryh4ck.randomDupes.RandomDupes
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class DupeCommand(private val plugin : RandomDupes) : TabExecutor {
    val mm = MiniMessage.miniMessage()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            val item = sender.inventory.itemInMainHand
            if (item.type.isAir) {
                sender.sendMessage(mm.deserialize("${plugin.prefix} <red>You must have something in your hand..."))
                return true
            }

            val clone = item.clone()
            clone.amount = 1
            sender.world.dropItemNaturally(sender.location, clone)
        }
        else{
            sender.sendMessage(mm.deserialize("${plugin.prefix} <red>You must be a player to use this command."))
        }
        return true
    }

    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String> ): List<String?> {
        return emptyList()
    }
}