package com.grizzyrp.twitchlink;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    private static final TwitchLink plugin = (TwitchLink) Bukkit.getPluginManager().getPlugin("TwitchLink");

    @EventHandler
    public void onEntry(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (plugin.files.getPlayers().getString(p.getName()+".status") != null){
            plugin.files.getPlayers().set(p.getName()+".status",false);
        }
    }

    @EventHandler
    public void onExit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (plugin.files.getPlayers().getString(p.getName()+".status") != null){
            //Live ended console message
            plugin.files.getPlayers().set(p.getName()+".status",false);
        }
    }
}
