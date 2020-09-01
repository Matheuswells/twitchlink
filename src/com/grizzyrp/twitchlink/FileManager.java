package com.grizzyrp.twitchlink;

import org.bukkit.Bukkit;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("ALL")
public class FileManager {

    private File file = null;
    private FileConfiguration fileConfig = null;
    private static final TwitchLink plugin = (TwitchLink) Bukkit.getPluginManager().getPlugin("TwitchLink");

    public FileManager() {
        File filePlayers = new File(plugin.getDataFolder(), "players.yml");
        if (!filePlayers.exists()) {
            plugin.saveResource("players.yml", false);
        }
    }

    public FileConfiguration getPlayers(){
        if(this.fileConfig == null){
            this.file = new File(plugin.getDataFolder(),"players.yml");
            this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileConfig;
    }

    public void savePlayers(){
        try{
            this.getPlayers().save(this.file);
           } catch (Exception ignored){}
    }

    public void reloadPlayers(){

        if (this.file == null){
            this.file = new File(plugin.getDataFolder(), "players.yml");
        }

        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfig.setDefaults(defaults);
    }
}
