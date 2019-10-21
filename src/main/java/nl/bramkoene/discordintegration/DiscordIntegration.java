package nl.bramkoene.discordintegration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import nl.bramkoene.discordintegration.commands.LinkmcCommandHandler;
import nl.bramkoene.discordintegration.discord.DiscordCommunicationHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class DiscordIntegration extends JavaPlugin {

    public JDA api;
    public ConfigManager configManager;

    @Override
    public void onEnable(){
        this.configManager = new ConfigManager(this);
        // Plugin startup logic
        try{
            setupApi();
        }catch(Exception e){
            Bukkit.getLogger().warning(e.toString());
        }

        api.addEventListener(new DiscordCommunicationHandler(this));
        Activity activity = Activity.playing("Say !linkmc to get started");
        api.getPresence().setPresence(activity, false);
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            Bukkit.getLogger().info(player.getUniqueId().toString());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    void setupApi() throws Exception{
        api = new JDABuilder(secret.token).build();
        this.getCommand("linkmc").setExecutor(new LinkmcCommandHandler(this));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public JDA getApi() {
        return api;
    }
}
