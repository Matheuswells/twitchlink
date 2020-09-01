package com.grizzyrp.twitchlink;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("ALL")
public class TwitchLink extends JavaPlugin {

    public String pre = "§d[TwitchLink] ";
    public FileManager files;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerEvents();
        registerCommands();
        files = new FileManager();
        getServer().getConsoleSender().sendMessage(pre + ChatColor.GREEN+" TwitchLink has been enabled ");

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(pre + ChatColor.RED+"TwitchLink has been disabled");
    }

    public void registerCommands(){
        getCommand("live").setExecutor(new Commands());
        getCommand("twitchsave").setExecutor(new Commands());
        getCommand("twitchsaveto").setExecutor(new Commands());
        getCommand("twitchdel").setExecutor(new Commands());
        getCommand("twitchdelto").setExecutor(new Commands());
    }

    public void registerEvents(){
        //Make a for here
        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }
}