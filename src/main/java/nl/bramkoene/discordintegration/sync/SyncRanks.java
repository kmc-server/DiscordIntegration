package nl.bramkoene.discordintegration.sync;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import nl.bramkoene.discordintegration.DiscordIntegration;
import nl.bramkoene.discordintegration.enums.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Bram
 * @version 1.0.0
 */
public class SyncRanks {
    /**
     * Checks the ranks of the user and then return an enum that corresponds with the correct rank
     * @param user The user of which you want to get the rank
     * @param guild The {@link Guild} of the server
     * @return {@link Ranks} ranks enum
     */
    public static Ranks getRankFromDiscordUser(User user, Guild guild){
        List<Role> roles = guild.getMember(user).getRoles();
        for (Role role: roles) {
            switch (role.toString()){
                case "RECTOR":
                    return Ranks.RECTOR;
                case "TEAMLEIDER":
                    return Ranks.TEAMLEIDER;
                case "DOCENT":
                    return Ranks.DOCENT;
                case "STAGAIR":
                    return Ranks.STAGAIR;
                case "VRIJWILLIGER":
                    return Ranks.VRIJWILLIGER;
            }
        }
        return Ranks.KMCER;
    }

    /**
     * Adds a certain rank to the player in minecraft
     * @param rank the rank enum to give to the player
     * @param plugin the plugin instance needed
     * @param player A player instance of the player to which to add the rank
     */
    public static void giveUserRank(@NotNull Ranks rank, @NotNull DiscordIntegration plugin, @NotNull Player player){
        String command = "/lp user " + player.getName() + "parent add " + rank.label;
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
