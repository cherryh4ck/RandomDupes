package io.github.Cherryh4ck.randomDupes

import io.github.Cherryh4ck.randomDupes.Modules.AnvilDupe
import io.github.Cherryh4ck.randomDupes.Modules.DonkeyDeathDupe
import org.bukkit.plugin.java.JavaPlugin

class RandomDupes : JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(DonkeyDeathDupe(), this)
        server.pluginManager.registerEvents(AnvilDupe(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
