package com.rainchat.funjump.arenas;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class JumpBlocks extends Region {

    private final List<Material> materials;
    private boolean isActive;

    public JumpBlocks(Location loc1, Location loc2) {
        super(loc1, loc2);
        this.materials = new ArrayList<>();
        for (BlockState blockState: getBlocks()) {
            materials.add(blockState.getType());
        }
        isActive = true;
    }

    public JumpBlocks(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        super(world, x1, y1, z1, x2, y2, z2);
        this.materials = new ArrayList<>();
        for (BlockState blockState: getBlocks()) {
            materials.add(blockState.getType());
        }
        isActive = true;
    }

    public JumpBlocks(Region region) {
        super(region.getWorld(), region.getMaxX(), region.getMaxY(), region.getMaxZ(), region.getMinX(), region.getMinY(), region.getMinZ());
        this.materials = new ArrayList<>();
        for (BlockState blockState: getBlocks()) {
            materials.add(blockState.getType());
        }
        isActive = true;
    }

    public void clearArea() {
        setActive(false);
        for (BlockState blockState: getBlocks()) {
            FallingBlock fall = blockState.getLocation().getWorld().spawnFallingBlock(blockState.getLocation().add(0.5,0.5,0.5),blockState.getBlockData());
            fall.setDropItem(false);
            double x = (blockState.getX() - getCenter().getX())*0.09;
            double y = 0.25;
            double z = (blockState.getZ() - getCenter().getZ())*0.09;
            fall.setVelocity(new Vector(x, y, z));
            blockState.getBlock().setType(Material.AIR);
        }

    }

    public void regenArea() {
        setActive(true);
        List<BlockState> blockStates = getBlocks();
        for (int i = 0; i < materials.size(); i++) {
            blockStates.get(i).getBlock().setType(materials.get(i));
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
