package com.rainchat.funjump.utils.general;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.JumpBlocks;
import com.rainchat.funjump.arenas.Region;
import com.rainchat.funjump.managers.ArenaManager;
import com.rainchat.funjump.managers.SelectManager;
import com.rainchat.funjump.utils.data.SelectPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class ArenaWriter {

    private static ArenaManager arenaManager;
    private static SelectManager selectManager;

    public static void createArena(Player player, String arenaName) {
        if (!player.hasPermission("fjump.arenas.create")) {
            player.sendMessage(Message.NO_PERMISSION.toString().replace("{0}", ""));
            return;
        }

        Arena arena = FunJump.getInstance().getArenaManager().createArena(arenaName);
        FunJump.getInstance().getArenaManager().saveArenaToList(arena);
        player.sendMessage(Message.CREATE_ARENA.toString());
    }

    public static void removeArena(Player player, String arenaName) {
        if (!player.hasPermission("fjump.arenas.remove")) {
            player.sendMessage(Message.NO_PERMISSION.toString().replace("{0}", ""));
            return;
        }
        Arena arena = arenaManager.getArena(arenaName);
        if (arena == null) {
            player.sendMessage(Message.NO_ARENA.toString().replace("{0}", arenaName));
            return;
        }

        arenaManager.removeArena(arena);
        player.sendMessage(Message.REMOVE_ARENA.toString().replace("{0}", arenaName));
    }

    public static void setFailArea(Player player, String arenaName) {
        if (!player.hasPermission("fjump.arenas.setfailregion")) {
            player.sendMessage(Message.NO_PERMISSION.toString().replace("{0}", ""));
            return;
        }
        Arena arena = arenaManager.getArena(arenaName);
        if (arena == null) {
            player.sendMessage(Message.NO_ARENA.toString().replace("{0}", arenaName));
            return;
        }

        SelectPlayer selectPlayer = selectManager.getVillagePlayer(player);
        if (selectPlayer.getPos1() != null && selectPlayer.getPos2() != null) {
            Region region = new Region(selectPlayer.getPos1(), selectPlayer.getPos2());
            arena.setFailRegion(region);

            player.sendMessage(ChatColor.GREEN + "Set fail region!");
        }
        FunJump.getInstance().getArenaManager().saveArenaToFile(arena);
    }

    public static void addPlatform(Player player, String arenaName) {
        if (!player.hasPermission("fjump.arenas.addplatforms")) {
            player.sendMessage(Message.NO_PERMISSION.toString().replace("{0}", ""));
            return;
        }
        Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
        if (arena == null) {
            player.sendMessage(Message.NO_ARENA.toString().replace("{0}", arenaName));
            return;
        }

        SelectPlayer selectPlayer = selectManager.getVillagePlayer(player);
        if (selectPlayer.getPos1() != null && selectPlayer.getPos2() != null) {
            arena.addPlatform(new JumpBlocks(selectPlayer.getPos1(), selectPlayer.getPos2()));
            player.sendMessage(ChatColor.GREEN + "Set platform region!");
        }

        arenaManager.saveArenaToFile(arena);
    }

    public static void visualPlatforms(Player player, String arenaName) {
        if (!player.hasPermission("fjump.arenas.platforms")) {
            player.sendMessage(Message.NO_PERMISSION.toString().replace("{0}", ""));
            return;
        }
        Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
        if (arena == null) {
            player.sendMessage(Message.NO_ARENA.toString().replace("{0}", arenaName));
        }

        for (Region jumpBlocks: arena.getPlatforms()) {
            for (BlockState blockState: jumpBlocks.getBlocks()) {
                player.sendBlockChange(blockState.getLocation(), Bukkit.createBlockData(Material.GREEN_STAINED_GLASS));
            }
        }

    }
}
