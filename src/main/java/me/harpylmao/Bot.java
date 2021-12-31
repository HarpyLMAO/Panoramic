package me.harpylmao;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 20
*/

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import lombok.Getter;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.commands.command.CommandManager;
import me.harpylmao.commands.command.defaults.DefaultHelpCommand;
import me.harpylmao.managers.users.User;
import me.harpylmao.managers.users.UserManager;
import me.harpylmao.managers.Panoramic;
import me.harpylmao.managers.PanoramicManager;
import me.harpylmao.managers.mongo.MongoConnector;
import me.harpylmao.managers.ticket.Ticket;
import me.harpylmao.managers.ticket.TicketManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;

@Getter
public class Bot {

    @Getter private static Bot instance;

    private final String panoramicId = "GTUb5gG9*sPJ+!?dbN##eaC-_?-$gC2$CJ%#fqdw";

    private final MongoConnector mongoConnector;
    private final PanoramicManager panoramicManager;
    private final UserManager userManager;
    private final TicketManager ticketManager;
    private final MusicManager guildAudioManager;

    private final EventWaiter eventWaiter;

    private final Panoramic panoramic;

    private Bot() throws LoginException, InterruptedException {
        instance = this;

        this.mongoConnector = new MongoConnector(this);
            mongoConnector.load();
        this.panoramicManager = new PanoramicManager(this.mongoConnector.getMongoDatabase());
            this.panoramic = this.panoramicManager.getPanoramicObjectRepository().find(this.getPanoramicId());
        this.ticketManager = new TicketManager(this.mongoConnector.getMongoDatabase());
        this.userManager = new UserManager(this.mongoConnector.getMongoDatabase());
        this.guildAudioManager = new MusicManager(this);

        JDABuilder jdaBuilder = JDABuilder.createDefault("OTE3ODYzODEwOTMxNTExMzM3.Ya-5SQ.Abiog6-O3Lbbb3MzPeHZZ4_FQmQ")
                .setActivity(Activity.playing("Follandome a la puta de tu madre :)"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .setRawEventsEnabled(true)
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_WEBHOOKS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS
                )
                .setMemberCachePolicy(MemberCachePolicy.ALL);

        this.eventWaiter = new EventWaiter();

        Panoramic panoramic = this.getPanoramic();

        if (panoramic == null) {
            panoramic = new Panoramic(this.panoramicId);
            this.savePanoramic(panoramic);
        }

        jdaBuilder.addEventListeners(
                eventWaiter,
                new Ticket.TicketEvents(),
                new MusicManager.MusicListeners(this),
                new DefaultHelpCommand.HelpCommandListeners(),
                new User.UserListener()
        );

        JDA jda = jdaBuilder.build().awaitReady();

        CommandManager commandManager = new CommandManager(jda, "p.");
        commandManager.registerCommands();
        commandManager.init();
    }

    public void savePanoramic(Panoramic panoramic) {
        this.panoramicManager.getPanoramicObjectRepository().save(panoramic);
    }

    public void saveUser(User user) {
        this.userManager.getUserObjectRepository().save(user);
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
    }

    public Color getColor(String string) {
        if (string == null) {
            float[] rgbColor = Color.RGBtoHSB(253, 225, 126, null);
            return Color.getHSBColor(rgbColor[0], rgbColor[1], rgbColor[2]);
        }
        if ("blue".equals(string))
            return Color.BLUE;
        if ("cyan".equals(string))
            return Color.CYAN;
        if ("green".equals(string))
            return Color.GREEN;
        if ("magenta".equals(string))
            return Color.MAGENTA;
        if ("orange".equals(string))
            return Color.ORANGE;
        if ("pink".equals(string))
            return Color.PINK;
        if ("black".equals(string))
            return Color.BLACK;
        if ("white".equals(string))
            return Color.WHITE;
        if ("grey".equals(string))
            return Color.GRAY;
        if ("yellow".equals(string))
            return Color.YELLOW;
        if ("light_grey".equals(string))
            return Color.LIGHT_GRAY;
        if ("red".equals(string))
            return Color.RED;
        return null;
    }
}
