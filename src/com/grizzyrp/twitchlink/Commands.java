package com.grizzyrp.twitchlink;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class Commands implements CommandExecutor {

    private static final TwitchLink plugin = (TwitchLink) Bukkit.getPluginManager().getPlugin("TwitchLink");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.pre + ChatColor.RED + "Console not allowed, please try use it like a player");
            return true;
        }

        Player p = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("live")){

            if(!p.hasPermission("twitchlink.live")){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("no-permission"));
                return true;
            }
            if(args.length == 0){
                assert plugin != null;
                if(plugin.files.getPlayers().getString(p.getName()+".link") != null){
                    String link = plugin.files.getPlayers().getString(p.getName()+".link");
                    if(!plugin.files.getPlayers().getBoolean(p.getName()+".status")){
                        plugin.files.getPlayers().set(p.getName()+".status",true);
                        plugin.files.savePlayers();
                        plugin.files.reloadPlayers();

                        p.sendTitle(plugin.getConfig().getString("start-live-title").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'),
                                plugin.getConfig().getString("start-live-subtitle").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'), 1,70,1);

                        plugin.getServer().broadcastMessage(plugin.pre + plugin.getConfig().getString("startMessage")
                                .replace("%player%",p.getName())
                                .replace("%link%",link));
                    }else{
                        plugin.files.getPlayers().set(p.getName()+".status",false);
                        plugin.files.savePlayers();
                        plugin.files.reloadPlayers();
                        p.sendTitle(plugin.getConfig().getString("end-live-title").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'),
                                plugin.getConfig().getString("end-live-subtitle").replace("%player%",p.getName())
                                        .replace("%link%",link).replace('&','§'), 1,70,1);

                    }
                    return true;
                }else{
                    p.sendMessage(plugin.pre + ChatColor.RED + "You do not save any streaming link yet, try /twitch save <TwitchUsername>");
                }
            }
        }

        if(cmd.getName().equalsIgnoreCase("twitchsave")){
            if(!p.hasPermission("twitchlink.save.own")){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("no-permission"));
                return true;
            }
            if(args.length == 0){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("wrong-arguments").replace('&','§'));
            }
            if(args.length == 1){
                    plugin.files.getPlayers().set(p.getName()+".link","twitch.tv/link".replace("link",args[0]));
                    plugin.files.getPlayers().set(p.getName()+".status",false);
                    plugin.files.savePlayers();
                    plugin.files.reloadPlayers();

                    p.sendMessage(plugin.pre + plugin.getConfig().getString("on-save-message")
                            .replace("%player%",p.getName())
                            .replace("%username%",args[0])
                            .replace('&','§'));
            } else {
                p.sendMessage(plugin.pre + plugin.getConfig().getString("wrong-arguments").replace('&','§'));
            }
        }

        if(cmd.getName().equalsIgnoreCase("twitchsaveto")){
            if(!p.hasPermission("twitchlink.save.any")){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("no-permission"));
                return true;
            }
            if(args.length == 2){
                plugin.files.getPlayers().set(args[0]+".link","twitch.tv/link".replace("link",args[1]));
                plugin.files.getPlayers().set(args[0]+".status",false);
                plugin.files.savePlayers();
                plugin.files.reloadPlayers();
                p.sendMessage(plugin.pre + plugin.getConfig().getString("3on-save-message")
                        .replace("%player%", p.getName())
                        .replace("%username%", args[1]).replace("%player2%", args[0])
                        .replace('&', '§'));
            } else {
                p.sendMessage(plugin.pre + plugin.getConfig().getString("wrong-arguments").replace('&','§'));
            }
        }

        if(cmd.getName().equalsIgnoreCase("twitchdel")){
            if(!p.hasPermission("twitchlink.del.own")){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("no-permission"));
                return true;
            }

            plugin.files.getPlayers().set(p.getName(), null);
            plugin.files.savePlayers();
            plugin.files.reloadPlayers();

            p.sendMessage(plugin.pre + plugin.getConfig().getString("own-del-message")
                    .replace("%player%",p.getName())
                    .replace('&','§'));
        }

        if(cmd.getName().equalsIgnoreCase("twitchdelto")){
            if(!p.hasPermission("twitchlink.del.any")){
                p.sendMessage(plugin.pre + plugin.getConfig().getString("no-permission"));
                return true;
            }
            if(args.length == 1){
                plugin.files.getPlayers().set(args[0], null);
                plugin.files.savePlayers();
                plugin.files.reloadPlayers();

                p.sendMessage(plugin.pre + plugin.getConfig().getString("any-del-message")
                        .replace("%player%",p.getName()).replace("%player2%", args[0])
                        .replace('&','§'));
            } else {
                p.sendMessage(plugin.pre + plugin.getConfig().getString("wrong-arguments").replace('&','§'));
            }
        }

        return false;
    }
}
