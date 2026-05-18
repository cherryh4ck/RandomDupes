package io.github.Cherryh4ck.randomDupes.Modules

import org.bukkit.entity.Donkey
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class DonkeyDeathDupe : Listener {
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity

        if (entity.type == EntityType.DONKEY) {
            val donkey = entity as Donkey
            if (donkey.isCarryingChest) {
                val inv = donkey.inventory
                for (i in 1 until inv.size) {
                    val item = inv.getItem(i) ?: continue
                    event.drops.add(item)
                }
            }
        }
    }
}