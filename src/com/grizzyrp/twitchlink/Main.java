package com.grizzyrp.twitchlink;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin implements CommandExecutor {
    private String pre = "§d[TwitchLink] ";
    private File file = null;
    private FileConfiguration fileConfig = null;
    HashMap<Integer, String> players;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerCommands();
        getServer().getConsoleSender().sendMessage(pre + ChatColor.GREEN+" TwitchLink has been enabled ");
        File filePlayers = new File(getDataFolder(), "players.yml");
        if (!filePlayers.exists()){
            saveResource("players.yml", false);
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(pre + ChatColor.RED+"TwitchLink has been disabled");

    }

    public void registerCommands(){
        //Make a for here
        getCommand("twitch").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(pre + ChatColor.RED + "Console not allowed, please try use it like a player");
            return true;
        }

        Player p = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("twitch")){
            if(!p.hasPermission("twitchlink.save")){
                p.sendMessage(this.pre + getConfig().getString("no-permission"));
                return true;
            }
            if(args.length == 0){
                if(getPlayers().getString(p.getName()+".link") != null){
                    String link = getPlayers().getString(p.getName()+".link");
                    if(!getPlayers().getBoolean(p.getName()+".status")){
                        getPlayers().set(p.getName()+".status",true);
                        savePlayers();
                        reloadPlayers();

                        p.sendTitle(getConfig().getString("start-live-title").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'),
                                getConfig().getString("start-live-subtitle").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'), 1,70,1);

                        getServer().broadcastMessage(this.pre + getConfig().getString("startMessage")
                                .replace("%player%",p.getName())
                                .replace("%link%",link));
                    }else{
                        getPlayers().set(p.getName()+".status",false);
                        savePlayers();
                        reloadPlayers();
                        p.sendTitle(getConfig().getString("end-live-title").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'),
                                getConfig().getString("end-live-subtitle").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'), 1,70,1);

                    }
                    return true;
                }else{
                    p.sendMessage(this.pre + ChatColor.RED + "You do not save any streaming link yet, try /twitch save <TwitchUsername>");
                }
            }


            if(args.length == 1){
                p.sendMessage(this.pre + getConfig().getString("wrong-arguments").replace('&','§'));
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("save")){
                    getPlayers().set(p.getName()+".link","twitch.tv/link".replace("link",args[1]));
                    getPlayers().set(p.getName()+".status",false);
                    savePlayers();
                    reloadPlayers();

                    p.sendMessage(this.pre + getConfig().getString("on-save-message")
                            .replace("%player%",p.getName())
                            .replace("%username%",args[1])
                            .replace('&','§'));
                }
            }
           /* if(args.length == 3){
                if(!p.hasPermission("twitchlink.admin")){
                if(args[0].equalsIgnoreCase("save")) {

                    getPlayers().set(args[1]+".link","twitch.tv/link".replace("link",args[2]));
                    getPlayers().set(args[1]+".status",false);
                    savePlayers();
                    reloadPlayers();
                    p.sendMessage(this.pre + getConfig().getString("3on-save-message")
                            .replace("%player%", p.getName())
                            .replace("%username%", args[2]).replace("%player2%", args[1])
                            .replace('&', '§'));

                }}
            }*/

        }
        return false;
    }

    public FileConfiguration getPlayers(){
        if(this.fileConfig == null){
            this.file = new File(getDataFolder(),"players.yml");
            this.fileConfig = YamlConfiguration.loadConfiguration(this.file);

        }
        return this.fileConfig;
    }
    public void savePlayers(){
        try{
            getPlayers().save(this.file);
        }catch (Exception e){

        }
    }

    public void reloadPlayers(){
        if (this.file == null){
            this.file = new File(getDataFolder(), "players.yml");
        }
        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);

        if(this.fileConfig != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
            this.fileConfig.setDefaults(defaults);
        }
    }

}