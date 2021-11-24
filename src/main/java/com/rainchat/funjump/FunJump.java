package com.rainchat.funjump;

import com.rainchat.funjump.arenas.ArenaManager;
import com.rainchat.funjump.commands.CommandManager;
import com.rainchat.funjump.commands.DuelTabCompleter;
import com.rainchat.funjump.events.ArenaEvents;
import com.rainchat.funjump.events.CuboidEvent;
import com.rainchat.funjump.events.ExplodeEvent;
import com.rainchat.funjump.utils.SelectManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FunJump extends JavaPlugin{
	
	/*
	 * Changelog:
	 * Tracks quits
	 */

	private static FunJump instance;
	
	private ArenaManager arenaManager;
	
	/**
	 * Enables the plugin
	 */
	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveConfig();
		

		SelectManager selectManager = new SelectManager();
		arenaManager = new ArenaManager(this);

		CommandManager commandManager = new CommandManager(this, selectManager);

		commandManager.setup();
		getCommand("funjump").setTabCompleter(new DuelTabCompleter());
		getServer().getPluginManager().registerEvents(new ArenaEvents(this), this);
		getServer().getPluginManager().registerEvents(new CuboidEvent(selectManager), this);
		getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
	}

	public static FunJump getInstance() {
		return instance;
	}

	/**
	 * Returns the ArenaManager
	 * @return
	 *   The ArenaManager
	 */
	public ArenaManager getArenaManager() {
		return arenaManager;
	}


}
