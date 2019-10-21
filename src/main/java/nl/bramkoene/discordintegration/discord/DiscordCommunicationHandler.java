package nl.bramkoene.discordintegration.discord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.bramkoene.discordintegration.DiscordIntegration;
import nl.bramkoene.discordintegration.enums.Ranks;
import nl.bramkoene.discordintegration.sync.SyncRanks;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DiscordCommunicationHandler extends ListenerAdapter {

    DiscordIntegration plugin;
    public DiscordCommunicationHandler(DiscordIntegration plugin){
        this.plugin = plugin;
    }


    @Override
    /**
     * Does a function when message received
     * @param event
     * @return void
     */
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
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

        if (content.equals("!syncRanks")){
            if(event.getGuild().getId() == "627082535490289667"){
                Ranks rank = SyncRanks.getRankFromDiscordUser(event.getAuthor(), event.getGuild());
            }
        }

        if(content.equals("!linkmc")){
            MessageChannel channel = event.getChannel();
            event.getAuthor().openPrivateChannel().queue(new Consumer<PrivateChannel>() {
                @Override
                public void accept(PrivateChannel privateChannel) {
                    privateChannel.sendMessage("To get started enter the following command in minecraft: /linkmc " + AccountLinker.generateVerifyCode(event.getAuthor()))
                            .queue();
                }
            });
        }
    }
}
