package com.rainchat.funjump.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DuelTabCompleter implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> arguments = new ArrayList();
		List<String> completions = new ArrayList<>();
		if(args.length == 1) {
			arguments.add("help");
			arguments.add("join");
			arguments.add("arenas");
			arguments.add("leave");
			StringUtil.copyPartialMatches(args[0], arguments, completions);
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("arenas")) {
				arguments.add("create");
				arguments.add("setfail");
				arguments.add("remove");
				arguments.add("platforms");
				arguments.add("addplatform");
				StringUtil.copyPartialMatches(args[1], arguments, completions);
			}
		}

		Collections.sort(completions);
		return completions;
	}

}
