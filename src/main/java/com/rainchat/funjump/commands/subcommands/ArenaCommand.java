package com.rainchat.funjump.commands.subcommands;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.JumpBlocks;
import com.rainchat.funjump.arenas.Region;
import com.rainchat.funjump.commands.SubCommand;
import com.rainchat.funjump.utils.SelectManager;
import com.rainchat.funjump.utils.data.SelectPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class ArenaCommand extends SubCommand {

    private final SelectManager selectManager;

    public ArenaCommand(SelectManager selectManager) {
        this.selectManager = selectManager;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("create")) {
                if (!player.hasPermission("fjump.arenas.create")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
                    return;
                }
                String arenaName = args[2];
                Arena arena = FunJump.getInstance().getArenaManager().createArena(arenaName);
                FunJump.getInstance().getArenaManager().saveArenaToList(arena);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.CreateArenaMessage")));
            } else if (args[1].equalsIgnoreCase("setfail")) {
                if (!player.hasPermission("fjump.arenas.setfailregion")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
                    return;
                }
                String arenaName = args[2];
                Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
                if (arena == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
                    return;
                }
                SelectPlayer selectPlayer = selectManager.getVillagePlayer(player);
                if (selectPlayer.getPos1() != null && selectPlayer.getPos2() != null) {
                    Region region = new Region(selectPlayer.getPos1(), selectPlayer.getPos2());
                    arena.setFailRegion(region);

                    player.sendMessage(ChatColor.GREEN + "Set fail region!");
                }
                FunJump.getInstance().getArenaManager().saveArenaToFile(arena);
            } else if (args[1].equalsIgnoreCase("addplatform")) {
                if (!player.hasPermission("fjump.arenas.setplatform")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
                    return;
                }
                String arenaName = args[2];
                Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
                if (arena == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
                    return;
                }
                SelectPlayer selectPlayer = selectManager.getVillagePlayer(player);
                if (selectPlayer.getPos1() != null && selectPlayer.getPos2() != null) {
                    JumpBlocks region = new JumpBlocks(selectPlayer.getPos1(), selectPlayer.getPos2());
                    arena.addPlatform(region);

                    player.sendMessage(ChatColor.GREEN + "Set platform region!");

                }
                FunJump.getInstance().getArenaManager().saveArenaToFile(arena);
            } else if (args[1].equalsIgnoreCase("platforms")) {
                if (!player.hasPermission("fjump.arenas.platforms")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
                    return;
                }
                String arenaName = args[2];
                Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
                if (arena == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
                    return;
                }

                for (Region jumpBlocks: arena.getPlatforms()) {
                    for (BlockState blockState: jumpBlocks.getBlocks()) {
                        player.sendBlockChange(blockState.getLocation(), Bukkit.createBlockData(Material.GREEN_STAINED_GLASS));
                    }
                }
                FunJump.getInstance().getArenaManager().saveArenaToFile(arena);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.IncorrectArgsMessage")));
            }
        }
    }

    @Override
    public String name() {
        return "arenas";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

}
