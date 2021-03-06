package com.rainchat.funjump.managers;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import com.rainchat.funjump.arenas.Region;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class ArenaManager {
	
	private FunJump plugin;
	
	private int nextID = 0;
	
	private List<Arena> arenaList = new ArrayList();

	public ArenaManager(FunJump pl) {
		plugin = pl;
		try {
			loadAllArenasFromFile();	
		}catch(Exception e) {
			e.printStackTrace();
			plugin.getLogger().info("Failed to load any arenas. Perhaps you haven't made one yet?");
			plugin.getLogger().info(ChatColor.GREEN + "If this is the first time you've loaded this plugin, the above error is normal. Don't worry about it.");
		}
	}

	public List<Arena> getArenaList(){
		return arenaList;
	}
	
	/**
	 * Loads all arenas from config.yml
	 */
	public void loadAllArenasFromFile() {
		FileConfiguration c = plugin.getConfig();
		for(String arenaName : c.getConfigurationSection("Arenas").getKeys(false)) {
			Arena arena = createArena(arenaName);
			String s1Str = c.getString("Arenas." + arenaName + ".failRegion");
			String[] s1Splice = s1Str.split(",");
			World s1World = Bukkit.getWorld(s1Splice[0]);
			int minX = Integer.parseInt(s1Splice[1]);
			int minY = Integer.parseInt(s1Splice[2]);
			int minZ = Integer.parseInt(s1Splice[3]);
			int maxX = Integer.parseInt(s1Splice[4]);
			int maxY = Integer.parseInt(s1Splice[5]);
			int maxZ = Integer.parseInt(s1Splice[6]);
			arena.setFailRegion(new Region(s1World, minX,minY,minZ,maxX,maxY,maxZ));

			List<String> platforms = c.getStringList("Arenas." + arenaName + ".platforms");
			for (String platform: platforms) {
				String[] platSplice = platform.split(",");
				World platWorld = Bukkit.getWorld(platSplice[0]);
				int platMinX = Integer.parseInt(platSplice[1]);
				int platMinY = Integer.parseInt(platSplice[2]);
				int platMinZ = Integer.parseInt(platSplice[3]);
				int platMaxX = Integer.parseInt(platSplice[4]);
				int platMaxY = Integer.parseInt(platSplice[5]);
				int platMaxZ = Integer.parseInt(platSplice[6]);
				Region jumpBlocks = new Region(
						new Location(platWorld,platMinX,platMinY,platMinZ),
						new Location(platWorld,platMaxX,platMaxY,platMaxZ));
				arena.addPlatform(jumpBlocks);
			}
			arena.setSpeed(c.getDouble("Arenas." + arenaName + ".speed", c.getDouble("Settings.defaultSpeed")));

			saveArenaToList(arena);
		}
	}

	public void saveArenaToFile(Arena arena) {
		FileConfiguration c = plugin.getConfig();
		String path = "Arenas." + arena.getName();

		c.set(path + ".failRegion", arena.getFailRegion().toSave());
		List<String> platforms = new ArrayList<>();
		for (Region jumpBlocks: arena.getPlatforms()) {
			platforms.add(jumpBlocks.toSave());
		}
		c.set(path + ".platforms", platforms);

		plugin.saveConfig();
		plugin.reloadConfig();
	}
	

	public void saveArenaToList(Arena arena) {
		arenaList.add(arena);
	}

	public Arena createArena(String name) {
		Arena arena = createArena(nextID, name);
		nextID++;
		return arena;
	}

	public void removeArena(Arena arena) {
		FileConfiguration c = plugin.getConfig();
		c.set("Arenas." + arena.getName(), null);

		plugin.saveConfig();
		plugin.reloadConfig();
	}

	private Arena createArena(int id, String name) {
		Arena arena = new Arena(id, name, plugin);
		return arena;
	}

	public Arena getArena(String name) {
		for(Arena arena : arenaList) {
			if (arena.getName().equalsIgnoreCase(name)) {
				return arena;
			}
		}
		return null;
	}

	public Arena getArena(Player player) {
		for(Arena arena : arenaList) {
			if(arena.containsPlayer(player)) {
				return arena;
			}
		}
		return null;
	}

}
