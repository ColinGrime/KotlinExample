package com.github.colingrime

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class KotlinExample : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        spawnParticles(e.player)
    }

    private fun spawnParticles(player: Player) {
        val world = player.world
        val locsToSpawn: ArrayList<Location> = arrayListOf()
        var doesKill = false

        val start = player.eyeLocation
        for (i in 1..200) {
            val loc = start.clone().add(start.direction.multiply(i / 4))
            locsToSpawn.add(loc)

            for (e in world.getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                if (e != player && e is LivingEntity) {
                    e.health = 0.0
                    doesKill = true
                    break
                }
            }

            if (doesKill) {
                break
            }
        }

        for (loc in locsToSpawn) {
            player.spawnParticle(if (doesKill) Particle.SOUL_FIRE_FLAME else Particle.SMALL_FLAME, loc, 1, 0.0, 0.0, 0.0, 0.0)
        }
    }
}