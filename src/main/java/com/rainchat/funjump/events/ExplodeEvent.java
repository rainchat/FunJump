package com.rainchat.funjump.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;


/**
 * Disable tnt explosions
 * and Disable tnt push tnt
 */

public class ExplodeEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(ExplosionPrimeEvent event) {
        if (event.getEntity().getCustomName() == null) return;
        if (event.getEntity().getCustomName().equalsIgnoreCase("noExplode")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (event.getEntity().getCustomName() == null) return;
        if (event.getEntity().getCustomName().equalsIgnoreCase("noExplode")) {
            event.setCancelled(true);
        }
    }
}
