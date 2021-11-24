package com.rainchat.funjump.events;

import com.rainchat.funjump.utils.SelectManager;
import com.rainchat.funjump.utils.data.SelectPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

public class CuboidEvent implements Listener {

    private SelectManager selectManager;

    public CuboidEvent(SelectManager selectManager) {
        this.selectManager = selectManager;
    }

    @EventHandler
    public void onClickStick(PlayerInteractEvent event) {

        if (event.getItem() == null) {
            return;
        }
        if (!event.getItem().getType().equals(Material.FEATHER)) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();
        Action action = event.getAction();

        SelectPlayer selectPlayer = selectManager.getVillagePlayer(player);

        Location targetLocation = getTargetBlock(event.getPlayer(),32).getLocation();

        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
            selectPlayer.setPos1(targetLocation);
            player.sendBlockChange(targetLocation, Bukkit.createBlockData(Material.PURPLE_TERRACOTTA));
        }
        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            selectPlayer.setPos2(targetLocation);
            player.sendBlockChange(targetLocation, Bukkit.createBlockData(Material.LIME_TERRACOTTA));
        }
    }


    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
