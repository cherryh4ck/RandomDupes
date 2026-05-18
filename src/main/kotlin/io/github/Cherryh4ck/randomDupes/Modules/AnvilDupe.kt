package io.github.Cherryh4ck.randomDupes.Modules

import io.github.Cherryh4ck.randomDupes.RandomDupes
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.AnvilInventory

class AnvilDupe(private val plugin : RandomDupes) : Listener {
    @EventHandler
    fun onAnvilUse(e: InventoryClickEvent) {
        val inv = e.clickedInventory as? AnvilInventory ?: return
        val item = e.currentItem ?: return
        if (e.rawSlot != 2) return

        val p = e.whoClicked as? Player ?: return
        val loc = inv.location ?: return
        if (p.inventory.firstEmpty() != -1) return

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            if (loc.block.type == Material.AIR) {
                val dupedItem = item.clone()
                dupedItem.amount = item.amount
                val dropped = p.world.dropItem(p.location, dupedItem)
                dropped.velocity = p.eyeLocation.direction
            }
        }, 1L)
    }
}