package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class PlayCommand implements BaseCommand {

    @Override
    public void execute(CommandEvent command, TextChannel textChannel, Member author, String[] args, GuildMessageReceivedEvent event) {
        Bot.getInstance().getGuildAudioManager().loadAndPlay(command, StringUtils.join(args, ' ', 1, args.length), true);
    }

    @Override
    public String category() {
        return "music";
    }

    @Override
    public String usage() {
        return "USAGE: \n" +
                " - /play [song name]/[song/playlist link]";
    }

    @Override
    public String getName() {
        return "play";
    }
}
