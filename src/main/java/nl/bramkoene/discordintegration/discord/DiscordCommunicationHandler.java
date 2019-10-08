package nl.bramkoene.discordintegration.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.bramkoene.discordintegration.DiscordIntegration;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Consumer;

public class DiscordCommunicationHandler extends ListenerAdapter {

    DiscordIntegration plugin;
    public DiscordCommunicationHandler(DiscordIntegration plugin){
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        Bukkit.getLogger().info(event.getAuthor().getAsMention());
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }

        if(content.equals("!linkmc")){
            MessageChannel channel = event.getChannel();
            event.getAuthor().openPrivateChannel().queue(new Consumer<PrivateChannel>() {
                @Override
                public void accept(PrivateChannel privateChannel) {
                    privateChannel.sendMessage("To get started enter your uuid. This can be found at: https://mcuuid.net Please enter the full UUID")
                            .queue();
                }
            });
        }



        try{
            UUID uuid = UUID.fromString(content);
            Bukkit.getPlayer(uuid);
            plugin.getConfigManager().getPlayers().set("ConfirmedPlayers." + uuid.toString(), event.getAuthor().getId());
            event.getChannel().sendMessage("Thank you. This is a valid uuid and your account is now linked").queue();
        } catch (IllegalArgumentException exception){
            //handle the case where string is not valid UUID
            event.getChannel().sendMessage("This is not a valid uuid please try again").queue();
        }
    }
}
