package com.rainchat.funjump.events;

import com.rainchat.funjump.FunJump;
import com.rainchat.funjump.arenas.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class ArenaEvents implements Listener{
	
	private FunJump plugin;
	
	public ArenaEvents(FunJump pl) {
		plugin = pl;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if(plugin.getArenaManager().getArena(player) != null) {
			Arena arena = plugin.getArenaManager().getArena(player);
			if(!arena.getActive()) {
				return;
			}
			if (arena.getFailRegion().contains(player.getLocation())) {
				arena.lose(player);
			}
		}

	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if(plugin.getArenaManager().getArena(player) != null) {
			Arena arena = plugin.getArenaManager().getArena(player);
			if(!arena.getActive()) {
				return;
			}
			arena.lose(player);
		}

	}

}
