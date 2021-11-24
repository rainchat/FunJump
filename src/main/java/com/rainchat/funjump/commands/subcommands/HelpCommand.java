package com.rainchat.funjump.commands.subcommands;

import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {


    private void showHelpMenu(Player player, String label) {
        player.sendMessage(ChatColor.BLUE + "fjump Help Menu" + ChatColor.WHITE + ":");
        player.sendMessage(ChatColor.GOLD + "/" + label + " help " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
                "Shows this help menu.");
        if(player.hasPermission("fjump.join"))
            player.sendMessage(ChatColor.GOLD + "/" + label + " join [arena] " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
                    "Join a duel if there are arenas available. You may specify an arena.");

    }

    @Override
    public void onCommand(Player player, String[] args) {
        showHelpMenu(player, "fjump");
    }

    @Override
    public String name() {
        return "help";
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
