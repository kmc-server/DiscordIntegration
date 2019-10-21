package nl.bramkoene.discordintegration.commands;

import nl.bramkoene.discordintegration.DiscordIntegration;
import nl.bramkoene.discordintegration.discord.AccountLinker;
import nl.bramkoene.discordintegration.general.ConfigEntry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LinkmcCommandHandler implements CommandExecutor {
    public DiscordIntegration plugin;
    @Contract(pure = true)
    public LinkmcCommandHandler(DiscordIntegration pl){
        this.plugin = pl;
    }
    @Override
    /**
     * Executes the onCommmand for /linkmc
     *
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)) return false;

        if(args.length <= 0){
            sender.sendMessage("To use this command type !linkmc in discord to the link bot");
            return false;
        }

        if(AccountLinker.validVerifyCode(args[0])){
            sender.sendMessage("Account linked!");
            // Successful account linking!
            String DiscordID = AccountLinker.getUserFromCode(args[0]).getId();
            UUID minecraftID = ((Player) sender).getUniqueId();
            ConfigEntry entry = new ConfigEntry();
            entry.setDiscordID(DiscordID);
            entry.setMinecraftID(minecraftID);

            List<ConfigEntry> entries = new ArrayList<>();
            if(plugin.getConfigManager().getPlayers().contains("linkedAccounts")) {
                List<ConfigEntry> entries1 = (List<ConfigEntry>) plugin.getConfigManager().getCollectors().getList("linkedAccounts");
                entries.addAll(entries1);
            }
            entries.add(entry);
            plugin.getConfigManager().getCollectors().set("linkedAccounts", entries);
            plugin.getConfigManager().saveCollectors();
        }

        return true;
    }
}
