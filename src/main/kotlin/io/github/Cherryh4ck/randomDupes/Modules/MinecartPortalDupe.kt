package io.github.Cherryh4ck.randomDupes.Modules

import io.github.Cherryh4ck.randomDupes.RandomDupes
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.Minecart
import org.bukkit.entity.minecart.StorageMinecart
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPortalEvent
import org.bukkit.metadata.FixedMetadataValue

class MinecartPortalDupe(private val plugin : RandomDupes) : Listener {
    @EventHandler
    fun onEntityPortal(event: EntityPortalEvent) {
        val entity = event.entity
        val destiny = event.to
        val destinyWorld = destiny?.world ?: return

        if (entity.hasMetadata("duped")) return

        if (entity.type == EntityType.CHEST_MINECART && destinyWorld.environment == World.Environment.NETHER){
            val minecart = entity as StorageMinecart
            val origin = event.from
            val worldOrigin = entity.world
            val minecartDupe = worldOrigin.spawnEntity(origin, EntityType.CHEST_MINECART) as StorageMinecart
            minecartDupe.setMetadata("duped", FixedMetadataValue(plugin, true))
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