package io.github.Cherryh4ck.randomDupes.Modules

import io.github.Cherryh4ck.randomDupes.RandomDupes
import io.papermc.paper.event.player.PlayerItemFrameChangeEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import kotlin.random.Random

class ItemFrameDupe(private val plugin : RandomDupes) : Listener {
    @EventHandler
    fun onItemFrameRemoved(event: PlayerItemFrameChangeEvent) {
        if (event.action != PlayerItemFrameChangeEvent.ItemFrameChangeAction.REMOVE) return
        if (Random.nextInt(100) >= plugin.itemFrameProbability) return

        val itemFrame = event.itemFrame
        val item = event.itemStack.clone()

        Bukkit.getScheduler().runTask(plugin, Runnable {
            if (itemFrame.isValid) itemFrame.setItem(item)
        })
    }
}