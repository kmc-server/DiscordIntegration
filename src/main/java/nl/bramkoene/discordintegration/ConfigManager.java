package nl.bramkoene.discordintegration;

import nl.bramkoene.discordintegration.DiscordIntegration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    DiscordIntegration plugin;

    public ConfigManager(DiscordIntegration pl){
        this.plugin = pl;
        setup();
    }

    public FileConfiguration playerscfg;
    public File playersfile;

    public FileConfiguration collectorscfg;
    public File collectorsfile;

    public void setup(){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }

        playersfile = new File(plugin.getDataFolder(), "players.yml");
        collectorsfile = new File(plugin.getDataFolder(), "collectors.yml");

        if(!playersfile.exists()){
            try{
                playersfile.createNewFile();
            }catch (IOException e){
                plugin.getLogger().warning(ChatColor.RED + "Could not create players config file");
            }
        }

        if(!collectorsfile.exists()){
            try{
                collectorsfile.createNewFile();
            }catch (IOException e){
                plugin.getLogger().warning(ChatColor.RED + "Could not create collectors config file");
            }
        }

        collectorscfg = YamlConfiguration.loadConfiguration(collectorsfile);
        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
        plugin.getLogger().info(ChatColor.GREEN + "Config Created");
    }

    public FileConfiguration getPlayers(){
        return playerscfg;
    }

    public void savePlayers(){
        try{
            playerscfg.save(playersfile);
        }catch (IOException e){
            plugin.getLogger().warning(ChatColor.RED + "PlayersFile Could not be saved!!!");
        }
    }

    public void reloadPlayers(){
        playerscfg = YamlConfiguration.loadConfiguration(playersfile);
    }

    public FileConfiguration getCollectors(){
        return collectorscfg;
    }

    public void saveCollectors(){
        try{
            collectorscfg.save(collectorsfile);
        }catch (IOException e){
            plugin.getLogger().warning(ChatColor.RED + "CollectorsFile coud not be saved!!!");
        }
    }

    public void reloadCollectors(){
        collectorscfg = YamlConfiguration.loadConfiguration(collectorsfile);
    }
}
