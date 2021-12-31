package me.harpylmao.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.harpylmao.Bot;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.audio.TrackScheduler;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BackCommand implements BaseCommand {

    @Override
    public void execute(CommandEvent command, TextChannel textChannel, Member member, String[] args, GuildMessageReceivedEvent event) {
        MusicManager musicManager = Bot.getInstance().getGuildAudioManager();
        if (event.getMember() == null || event.getMember().getVoiceState() == null || !event.getMember().getVoiceState().inVoiceChannel() || event.getMember().getVoiceState().getChannel() == null) {
            textChannel.sendMessageEmbeds(new EmbedBuilder().setColor(Bot.getInstance().getPanoramic().getColorColored()).setDescription("You are not in a voice channel!").build()).queue();
            return;
        }

        TrackScheduler trackScheduler = musicManager.getGuildAudio(event.getGuild()).getTrackScheduler();

        AudioTrack track = trackScheduler.getAudioPlayer().getPlayingTrack();
        AudioTrack lastSong = trackScheduler.getLastSong();

        if (lastSong == null) {
            if (trackScheduler.getTrackQueue().isEmpty()) {
                textChannel.sendMessageEmbeds(new EmbedBuilder().setColor(Bot.getInstance().getPanoramic().getColorColored()).setDescription("There are no old or new song to be played.").build()).queue();
            } else {
                textChannel.sendMessageEmbeds(new EmbedBuilder().setColor(Bot.getInstance().getPanoramic().getColorColored()).setDescription("There is no song to go back to!").build()).queue();
            }
        } else {
            trackScheduler.getAudioPlayer().stopTrack();
            trackScheduler.getTrackQueue().add(0, track);
            trackScheduler.getAudioPlayer().playTrack(lastSong.makeClone());
            trackScheduler.setLastSong(null);
            trackScheduler.setLastBoolean(false);

            textChannel.sendMessageEmbeds(new EmbedBuilder().setColor(Bot.getInstance().getPanoramic().getColorColored()).setDescription("Now playing last song.").build()).queue();
        }
    }

    @Override
    public String usage() {
        return "/back";
    }

    @Override
    public String getName() {
        return "back";
    }

    @Override
    public String category() {
        return "music";
    }
}
