package com.rainchat.funjump;

import com.rainchat.funjump.managers.ArenaManager;
import com.rainchat.funjump.commands.CommandManager;
import com.rainchat.funjump.commands.TabCommands;
import com.rainchat.funjump.events.ArenaEvents;
import com.rainchat.funjump.events.CuboidEvent;
import com.rainchat.funjump.events.ExplodeEvent;
import com.rainchat.funjump.managers.SelectManager;
import com.rainchat.funjump.utils.general.Message;
import com.rainchat.funjump.utils.storage.YAML;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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

		registerMessages(new YAML(this, "messages"));

		SelectManager selectManager = new SelectManager();
		arenaManager = new ArenaManager(this);

		CommandManager commandManager = new CommandManager(this);

		commandManager.setup();
		getCommand("funjump").setTabCompleter(new TabCommands());


		getLogger().info("Registered " + registerListeners(
				new ArenaEvents(this),
				new CuboidEvent(selectManager),
				new CuboidEvent(selectManager),
				new ExplodeEvent()
		) + " listener(s).");
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


	private int registerListeners(Listener... listeners) {
		AtomicInteger atomicInteger = new AtomicInteger(0);
		Arrays.asList(listeners).forEach(listener -> {
			atomicInteger.getAndAdd(1);
			getServer().getPluginManager().registerEvents(listener, this);
		});
		return atomicInteger.get();
	}

	private void registerMessages(YAML yaml) {
		yaml.setup();
		Message.setConfiguration(yaml.getFileConfiguration());

		int index = 0;
		for (Message message : Message.values()) {
			if (message.getList() != null) {
				yaml.getFileConfiguration().set(message.getPath(), message.getList());
			} else {
				index += 1;
				yaml.getFileConfiguration().set(message.getPath(), message.getDef());
			}
		}
		yaml.save();
		getLogger().info("Registered " + index + " message(s).");
	}

}
