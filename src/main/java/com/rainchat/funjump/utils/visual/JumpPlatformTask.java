package com.rainchat.funjump.utils.visual;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.JumpBlocks;
import com.rainchat.funjump.arenas.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JumpPlatformTask extends BukkitRunnable {

    private Arena arena;
    private List<JumpBlocks> allPlatforms;
    private int tiks = 0;
    private int amount = 0;
    private int tntCount = 1;
    private double speed;

    public JumpPlatformTask(Arena arena) {
        this.arena = arena;
        this.allPlatforms = new ArrayList<>();
        for (Region region: arena.getPlatforms()) {
            this.allPlatforms.add(new JumpBlocks(region));
        }
        this.speed = arena.getSpeed();
    }

    @Override
    public void run() {
        tiks += 5;
        if (tiks >= 30 / speed*tntCount) {
            tiks = 0;
            if (10 > 30 / speed*tntCount) {
                tntCount++;
            }

            if (amount%20 == 0) {
                speed = speed + 0.3;
                arena.sendToAllPlayers(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.SpeedMessage")
                        .replaceAll("#arg", speed+"")));
            }

            for (int i = 0; i < tntCount; i++) {
                List<JumpBlocks> activePlatforms = new ArrayList<>();
                for (JumpBlocks jumpBlocks : allPlatforms) {
                    if (jumpBlocks.isActive()) {
                        activePlatforms.add(jumpBlocks);
                    }
                }
                if (activePlatforms.size() <= 1) {
                    return;
                }
                Random random = new Random();
                int index = random.nextInt(activePlatforms.size());


                tntTrow(activePlatforms.get(index));
                amount++;
                arena.setScore(arena.getScore()+getRandomNumber(6,12));
            }


        }
    }

    public void tntTrow(JumpBlocks platform) {
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

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
