package io.github.Cherryh4ck.randomDupes.Modules

import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryPickupItemEvent
import org.bukkit.event.inventory.InventoryType

class LavaDupe : Listener {
    @EventHandler
    fun onHopperPickup(event: InventoryPickupItemEvent) {
        val inv = event.inventory
        val item = event.item
        val loc = inv.location ?: return

        if (inv.type == InventoryType.HOPPER){
            if (loc.block.getRelative(BlockFace.UP).type == Material.LAVA){
                val itemStack = item.itemStack
                if (inv.firstEmpty() != -1){
                    inv.addItem(itemStack)
                }
            }
        }
    }
}