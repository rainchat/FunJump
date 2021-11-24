package com.rainchat.funjump.utils.visual;


import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.JumpBlocks;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;

public class VisualizationTaskApply implements Runnable {

    private final JumpBlocks platform;

    public VisualizationTaskApply(JumpBlocks platform) {
        this.platform = platform;
    }


    @Override
    public void run() {
        TNTPrimed tnt = (TNTPrimed) platform.getWorld().spawnEntity(platform.getCenter().add(0,3,0), EntityType.PRIMED_TNT);
        tnt.setCustomName("noExplode");
        tnt.setFuseTicks(50);
        platform.setActive(false);



        FunJump.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(
                FunJump.getInstance(),
                platform::clearArea,
                50);

        FunJump.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(
                FunJump.getInstance(),
                platform::regenArea,
                20L*9);
    }

}
