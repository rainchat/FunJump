package com.rainchat.funjump.commands.subcommands;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.utils.general.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 1) {
            if (!player.hasPermission("fjump.join")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
            }
            Arena check = FunJump.getInstance().getArenaManager().getArena(player);
            if (check != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.AlreadyInArenaMessage")));
                return;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.AttemptJoinMessage")));
            for (Arena arena : FunJump.getInstance().getArenaManager().getArenaList()) {
                if (!arena.getActive()) {
                    arena.addPlayer(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.JoinedMessage")).replace("<arena>", arena.getName()));
                    if (arena.canStart()) {
                        arena.start();
                    }
                    return;
                }
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoArenaAvailableMessage")));
        } else if (args.length == 2) {
            Arena check = FunJump.getInstance().getArenaManager().getArena(player);
            if (check != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.AlreadyInArenaMessage")));
                return;
            }
            String arenaName = args[1];
            Arena arena = FunJump.getInstance().getArenaManager().getArena(arenaName);
            if (arena == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
            } else {
                if (arena.getActive()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.ThatArenaIsFullMessage")));
                } else {
                    arena.addPlayer(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.JoinedMessage")).replace("<arena>", arena.getName()));
                    if (arena.canStart()) {
                        arena.start();
                    }
                }
            }
        }
    }

    @Override
    public String name() {
        return "join";
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
