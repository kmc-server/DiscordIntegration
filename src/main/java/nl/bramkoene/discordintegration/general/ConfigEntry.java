package nl.bramkoene.discordintegration.general;

import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class ConfigEntry {
    String discordID;
    UUID minecraftID;
    @Contract(pure = true)
    public ConfigEntry(){}

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public UUID getMinecraftID() {
        return minecraftID;
    }

    public void setMinecraftID(UUID minecraftID) {
        this.minecraftID = minecraftID;
    }
}
