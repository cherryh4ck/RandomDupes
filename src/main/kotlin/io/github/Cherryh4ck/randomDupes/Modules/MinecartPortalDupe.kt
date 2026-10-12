package io.github.Cherryh4ck.randomDupes.Modules

import io.github.Cherryh4ck.randomDupes.RandomDupes
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.minecart.StorageMinecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPortalEvent
import org.bukkit.persistence.PersistentDataType

class MinecartPortalDupe(private val plugin : RandomDupes) : Listener {
    private val dupedKey = NamespacedKey(plugin, "duped")
    @EventHandler
    fun onEntityPortal(event: EntityPortalEvent) {
        val entity = event.entity
        val destiny = event.to
        val destinyWorld = destiny?.world ?: return

        if (entity.persistentDataContainer.has(dupedKey)) return

        if (entity.type == EntityType.CHEST_MINECART && destinyWorld.environment == World.Environment.NETHER){
            val minecart = entity as StorageMinecart
            val origin = event.from
            val worldOrigin = entity.world
            val minecartDupe = worldOrigin.spawnEntity(origin, EntityType.CHEST_MINECART) as StorageMinecart
            minecartDupe.persistentDataContainer.set(dupedKey, PersistentDataType.BYTE, 1)
            val origInv = minecart.inventory
            val dupeInv = minecartDupe.inventory
            for (i in 0 until origInv.size) {
                val item = origInv.getItem(i) ?: continue
                dupeInv.setItem(i, item.clone())
            }
            minecartDupe.velocity = minecart.velocity
        }
    }
}