package com.rainchat.funjump.commands;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.commands.subcommands.ArenaCommand;
import com.rainchat.funjump.commands.subcommands.HelpCommand;
import com.rainchat.funjump.commands.subcommands.JoinCommand;
import com.rainchat.funjump.commands.subcommands.LeaveCommand;
import com.rainchat.funjump.utils.SelectManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> commands = new ArrayList<>();
    private final FunJump plugin;
    private final SelectManager selectManager;

    public CommandManager(FunJump plugin, SelectManager selectManager) {
        this.plugin = plugin;
        this.selectManager = selectManager;
    }


    public String main = "funjump";

    public void setup() {
        plugin.getCommand(main).setExecutor(this);

        //Sub Commands
        this.commands.add(new ArenaCommand(selectManager));
        this.commands.add(new HelpCommand());
        this.commands.add(new LeaveCommand());
        this.commands.add(new JoinCommand());
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use commands for this plugin.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Please add arguments to your command. Type /funjump help for info");
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Invalid subcommand");
                return true;
            }

            try{
                target.onCommand(player,args);
            }catch (Exception e){
                player.sendMessage(ChatColor.RED + "An error has occurred.");

                e.printStackTrace();
            }
        }

        return true;
    }

    private SubCommand get(String name) {

        for (SubCommand sc : this.commands) {
            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }

            }
        }
        return null;
    }
}
