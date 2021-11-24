package com.rainchat.funjump.utils;

import com.rainchat.funjump.utils.data.SelectPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectManager {

    private final Map<UUID, SelectPlayer> selectPlayer = new HashMap<>();

    public void removeVillagePlayer(Player player) {
        selectPlayer.remove(player.getUniqueId());
    }

    public SelectPlayer getVillagePlayer(Player player) {
        return selectPlayer.computeIfAbsent(player.getUniqueId(), k -> new SelectPlayer(player));
    }
}
