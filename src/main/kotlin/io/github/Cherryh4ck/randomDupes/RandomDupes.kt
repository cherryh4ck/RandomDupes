package io.github.Cherryh4ck.randomDupes

import io.github.Cherryh4ck.randomDupes.Commands.DupeCommand
import io.github.Cherryh4ck.randomDupes.Modules.AnvilDupe
import io.github.Cherryh4ck.randomDupes.Modules.DonkeyDeathDupe
import io.github.Cherryh4ck.randomDupes.Modules.LavaDupe
import io.github.Cherryh4ck.randomDupes.Modules.MinecartPortalDupe
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

class RandomDupes : JavaPlugin() {
    val minimessage = MiniMessage.miniMessage()
    var prefix = config.getString("general.prefix")

    override fun onEnable() {
        saveDefaultConfig()
        reloadConfig()
        hookListeners()

        val randomdupes = getCommand("randomdupes")
        randomdupes?.setExecutor(this)
        randomdupes?.tabCompleter = this

        getCommand("dupe")?.setExecutor(DupeCommand(this))

        logToConsole("Plugin started.")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private val listeners = mapOf(
        "donkey-death-dupe.enable" to DonkeyDeathDupe(),
        "anvil-dupe.enable" to AnvilDupe(this),
        "lava-dupe.enable" to LavaDupe(),
        "minecart-portal-dupe.enable" to MinecartPortalDupe(this)
    )

    fun reloadPlugin(){
        reloadConfig()
        hookListeners()
        prefix = config.getString("general.prefix")
    }

    fun hookListeners(){
        listeners.forEach { (path, listener) ->
            HandlerList.unregisterAll(listener)
            if (config.getBoolean(path, false)) {
                server.pluginManager.registerEvents(listener, this)
                logToConsole("<yellow>Hooked dupe: <green>$path")
            }
        }
    }

    fun logToConsole(message: String) {
        Bukkit.getConsoleSender().sendMessage(minimessage.deserialize("$prefix $message"))
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(minimessage.deserialize("$prefix <gold>v. ${this.pluginMeta.version}"))
            return true
        }

        when (args[0].lowercase()) {
            "reload" -> {
                reloadPlugin()
                sender.sendMessage(minimessage.deserialize("$prefix <gold>Plugin reloaded."))
            }
            else -> {
                sender.sendMessage(minimessage.deserialize("$prefix <red>Not a valid command."))
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String>? {
        val completions = mutableListOf<String>()
        if (args.size == 1) {
            val subs = listOf("reload")
            for (s in subs) {
                if (s.startsWith(args[0].lowercase())) {
                    completions.add(s)
                }
            }
        }
        return completions
    }
}
