package com.rainchat.funjump.commands.subcommands;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.utils.general.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveCommand  extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
            if(!player.hasPermission("fjump.leave")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NoPermissionMessage")));
                return;
            }
            Arena check = FunJump.getInstance().getArenaManager().getArena(player);
            if(check == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.NotInArenaMessage")));
            }else {
                if(check.getActive()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.LeftArenaMessage")));
                    check.lose(player);
                    return;
                }
                check.removePlayer(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', FunJump.getInstance().getConfig().getString("Messages.LeftArenaMessage")));
            }
    }

    @Override
    public String name() {
        return "leave";
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
