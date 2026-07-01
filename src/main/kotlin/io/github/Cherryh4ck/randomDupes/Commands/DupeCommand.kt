package io.github.Cherryh4ck.randomDupes.Commands

import io.github.Cherryh4ck.randomDupes.RandomDupes
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.UUID

class DupeCommand(private val plugin : RandomDupes) : TabExecutor {
    val mm = MiniMessage.miniMessage()
    val cooldowns: MutableSet<UUID> = java.util.concurrent.ConcurrentHashMap.newKeySet()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            if (!plugin.dupeIsEnabled) {
                sender.sendMessage(mm.deserialize("${plugin.prefix} <red>This dupe is disabled."))
                return true;
            }

            val item = sender.inventory.itemInMainHand
            if (item.type.isAir) {
                sender.sendMessage(mm.deserialize("${plugin.prefix} <red>You must have something in your hand..."))
                return true
            }

            if (cooldowns.contains(sender.uniqueId)) {
                sender.sendMessage(mm.deserialize("${plugin.prefix} Please wait before using this command again."))
                return true
            }

            val clone = item.clone()
            clone.amount = 1
            sender.world.dropItemNaturally(sender.location, clone)
            if (plugin.cooldownSeconds != 0L) {
                cooldowns.add(sender.uniqueId)
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    cooldowns.remove(sender.uniqueId)
                }, plugin.cooldownSeconds)
            }
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