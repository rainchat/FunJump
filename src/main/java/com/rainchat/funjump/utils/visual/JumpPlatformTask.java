package com.rainchat.funjump.utils.visual;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.JumpBlocks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JumpPlatformTask extends BukkitRunnable {

    private Arena arena;
    private List<JumpBlocks> allPlatforms;
    private int tiks = 0;
    private int amount = 0;
    private double speed;

    public JumpPlatformTask(Arena arena) {
        this.arena = arena;
        this.allPlatforms = arena.getPlatforms();
        this.speed = arena.getSpeed();
    }

    @Override
    public void run() {
        tiks += 5;
        if (tiks >= 80 / speed) {
            if (amount%15 == 0) {
                speed = speed + 0.1;
            }
            amount++;
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
            //arena.setScore(arena.getScore()+35);
            FunJump.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FunJump.getInstance(), new VisualizationTaskApply(activePlatforms.get(index)), 0L);
        }
    }
}
