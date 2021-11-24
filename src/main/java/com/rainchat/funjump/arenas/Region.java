package com.rainchat.funjump.arenas;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.ArrayList;

public class Region {
    private final World world;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;

    public Region(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }

    public Region(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.world = world;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);
    }

    public World getWorld() {
        return world;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public boolean contains(Region region) {
        return region.getWorld().equals(world) &&
                region.getMinX() >= minX && region.getMaxX() <= maxX &&
                region.getMinY() >= minY && region.getMaxY() <= maxY &&
                region.getMinZ() >= minZ && region.getMaxZ() <= maxZ;
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean overlaps(Region region) {
        return region.getWorld().equals(world) &&
                !(region.getMinX() > maxX || region.getMinY() > maxY || region.getMinZ() > maxZ ||
                        minZ > region.getMaxX() || minY > region.getMaxY() || minZ > region.getMaxZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Region)) {
            return false;
        }
        final Region other = (Region) obj;
        return world.equals(other.world)
                && minX == other.minX
                && minY == other.minY
                && minZ == other.minZ
                && maxX == other.maxX
                && maxY == other.maxY
                && maxZ == other.maxZ;
    }

    public Location getCenter() {
        int minX = getMinX();
        int minY = getMinY();
        int minZ = getMinZ();
        int x1 = getMaxX() + 1;
        int y1 = getMaxY() + 1;
        int z1 = getMaxZ() + 1;

        return new Location(world, minX + (x1 - minX) / 2.0D, minY + (y1 - minY) / 2.0D, minZ + (z1 - minZ) / 2.0D);
    }

    public ArrayList<BlockState> getBlocks() {
        ArrayList<BlockState> blocks = new ArrayList<BlockState>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockState block = world.getBlockAt(x, y, z).getState();
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public String toSave() {
        return world.getName() +
                "," + minX +
                "," + minY +
                "," + minZ +
                "," + maxX +
                "," + maxY +
                "," + maxZ;
    }

    @Override
    public String toString() {
        return "Cuboid[world:" + world.getName() +
                ", minX:" + minX +
                ", minY:" + minY +
                ", minZ:" + minZ +
                ", maxX:" + maxX +
                ", maxY:" + maxY +
                ", maxZ:" + maxZ + "]";
    }
}