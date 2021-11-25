package com.rainchat.funjump.arenas;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.utils.visual.JumpPlatformTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
	private List<Region> jumpBlocks;

	private Region failRegion;
	private JumpPlatformTask task;


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
			status.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.WinMessage")
					.replaceAll("#arg", getScore()+"")));
			active = false;

			task.cancel();
			return;
		}
		status.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.LoseMessage")
				.replaceAll("#arg", getScore()+"")));
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

	public void start() {
		if (players.size() != 1) {
			return;
		}
		setScore(0);

		for(Player p : players) {

			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.PrepareToJump")));

			Random random = new Random();
			int index = random.nextInt(jumpBlocks.size());

			p.teleport(jumpBlocks.get(index).getCenter().add(0,1,0));
		}


		Arena arena = this;
		new BukkitRunnable() {
			int timer = 10;

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
					task.runTaskTimer(FunJump.getInstance(), 0,5);
					cancel();
				}



				timer--;
			}
		}.runTaskTimer(FunJump.getInstance(), 0, 20);


	}

	public boolean containsPlayer(Player player) {
		for(Player p : players) {
			try {
				if(p.getName().equals(player.getName())) {
					return true;
				}
			}catch(NullPointerException e) {
				// null is not the player
			}
		}
		return false;
	}

	public boolean sendToAllPlayers(String message) {
		for(Player p : players) {
			p.sendMessage(message);
		}
		return false;
	}

	public boolean addPlayer(Player player) {
		players.add(player);
		return true;
	}

	public void addPlatform(Region region) {
		jumpBlocks.add(region);
	}
	

	public boolean removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
			return true;
		}
		return false;
	}
	

	public boolean canStart() {
		return true;
	}
	

	public boolean getActive() {
		return active;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Region getFailRegion() {
		return failRegion;
	}

	public List<Region> getPlatforms() {
		return jumpBlocks;
	}

	public void setFailRegion(Region region) {
		this.failRegion = region;

	}



}
