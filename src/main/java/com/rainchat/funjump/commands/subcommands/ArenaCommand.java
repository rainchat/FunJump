package com.rainchat.funjump.commands.subcommands;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.JumpBlocks;
import com.rainchat.funjump.arenas.Region;
import com.rainchat.funjump.utils.general.ArenaWriter;
import com.rainchat.funjump.utils.general.Message;
import com.rainchat.funjump.utils.general.SubCommand;
import com.rainchat.funjump.managers.SelectManager;
import com.rainchat.funjump.utils.data.SelectPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class ArenaCommand extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("create")) {
                ArenaWriter.createArena(player, args[2]);
            } else if (args[1].equalsIgnoreCase("setfail")) {
                ArenaWriter.setFailArea(player, args[2]);

            } else if (args[1].equalsIgnoreCase("addplatform")) {
                ArenaWriter.addPlatform(player, args[2]);

            } else if (args[1].equalsIgnoreCase("platforms")) {
                ArenaWriter.visualPlatforms(player, args[2]);

            } else if (args[1].equalsIgnoreCase("remove")) {
                ArenaWriter.removeArena(player,args[2]);
            } else {
                player.sendMessage(Message.HELP.toString());
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
