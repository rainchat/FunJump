package com.rainchat.funjump.arenas;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.utils.visual.JumpPlatformTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Arena {
	
	private FunJump plugin;
	private int id;
	private String name;

	private int score = 0;

	private double speed = 1;
	private int minPlayers;
	private int maxPlayers;


	private boolean active = false;

	private List<Player> players;
	private List<JumpBlocks> jumpBlocks;

	private Region failRegion;
	private JumpPlatformTask task;

	/**
	 * Constructs a new Arena with the given ID and name
	 * @param id
	 *   The arena's ID
	 * @param name
	 *   The arena's name
	 */
	public Arena(int id, String name, FunJump pl) {
		this.id = id;
		this.name = name;
		plugin = pl;
		players = new ArrayList<>();
		jumpBlocks = new ArrayList<>();
	}


	public double getSpeed() {
		return speed;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void end(Player status) {
		if (players.size() == 0) {
			status.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.WinMessage")));
			active = false;

			task.cancel();
			return;
		}
		status.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LoseMessage")));
	}

	public void lose(Player loser) {
		try {
			removePlayer(loser);
		}catch(NullPointerException e) {
			// loser disconnected, they didn't die. As such, they are not here to be sent a message.
		}
		loser.teleport(loser.getWorld().getSpawnLocation());
		end(loser);
	}
	
	/**
	 * Starts a duel if both spawns are set.
	 */
	public void start() {
		if (players.size() != 1) {
			return;
		}

		for(Player p : players) {

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.PrepareToJump")));

			Random random = new Random();
			int index = random.nextInt(jumpBlocks.size());

			p.teleport(jumpBlocks.get(index).getCenter().add(0,1,0));
		}


		Arena arena = this;
		new BukkitRunnable() {
			int timer = 15;

			@Override
			public void run() {

				for(Player p : players) {
					p.sendTitle(
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.PrepareToStart")),
							ChatColor.translateAlternateColorCodes('&',"&a" + timer));

				}

				if (timer <= 0) {
					active = true;
					task = new JumpPlatformTask(arena);
					task.runTaskTimer(FunJump.getInstance(), 40,15);
					cancel();
				}



				timer--;
			}
		}.runTaskTimer(FunJump.getInstance(), 0, 20);


	}
	
	/**
	 * Checks if this arena contains the given player
	 * @param player
	 *   The player to check for
	 * @return
	 *   Whether or not the player is in this arena
	 */
	public boolean containsPlayer(Player player) {
		for(Player p : players) {
			try {
				if(p.getName().equals(player.getName())) {
					return true;
				}
			}catch(NullPointerException e) {
				// null is not the player
				continue;
			}
		}
		return false;
	}
	
	/**
	 * Adds a player to the arena. True if successful.
	 * @param player
	 *   The player to add
	 * @return
	 *   Whether or not the player was added
	 */
	public boolean addPlayer(Player player) {
		players.add(player);
		return true;
	}

	public void addPlatform(JumpBlocks region) {
		jumpBlocks.add(region);
	}
	
	/**
	 * Removes a player from the arena. True if successful.
	 * @param player
	 *   The player to remove
	 * @return
	 *   Whether or not the player was removed
	 */
	public boolean removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether or not the arena has 2 players (AKA, if it can start a duel)
	 * @return
	 *   Whether or not the duel can start
	 */
	public boolean canStart() {
		return true;
	}
	
	/**
	 * Returns active status of the arena
	 * @return
	 *   Active status of the arena
	 */
	public boolean getActive() {
		return active;
	}
	
	/**
	 * Returns the arena's ID
	 * @return
	 *   The arena's ID
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the arena's name
	 * @return
	 *   The arena's name
	 */
	public String getName() {
		return name;
	}

	public Region getFailRegion() {
		return failRegion;
	}

	public List<JumpBlocks> getPlatforms() {
		return jumpBlocks;
	}

	public void setFailRegion(Region region) {
		this.failRegion = region;

	}



}
