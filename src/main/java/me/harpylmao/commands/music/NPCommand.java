package me.harpylmao.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.harpylmao.Bot;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "np", category = "music")
public class NPCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    MusicManager musicManager = Bot.getInstance().getGuildAudioManager();
    if (
      musicManager
        .getGuildAudio(event.getGuild())
        .getTrackScheduler()
        .getTrackQueue()
        .size() ==
      0
    ) {
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("There are no songs playing.")
            .build()
        )
        .queue();
      return;
    }
    AudioTrack currentPlaying = musicManager
      .getGuildAudio(event.getGuild())
      .getTrackScheduler()
      .getTrackQueue()
      .get(0);
    String[] posString = new String[] {
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
      "⎯",
    };
    try {
      posString[(int) Math.floor(
          (float) currentPlaying.getPosition() /
          (float) currentPlaying.getDuration() *
          30F
        )] =
        "~~◉~~";
    } catch (Exception e) {
      e.printStackTrace();
    }

    long msPos = currentPlaying.getPosition();
    long minPos = msPos / 60000;
    msPos = msPos % 60000;
    int secPos = (int) Math.floor((float) msPos / 1000f);

    long msDur = currentPlaying.getDuration();
    long minDur = msDur / 60000;
    msDur = msDur % 60000;
    int secDur = (int) Math.floor((float) msDur / 1000f);

    EmbedBuilder builder = new EmbedBuilder()
      .setColor(Bot.getInstance().getPanoramic().getColorColored())
      .setTitle("Now Playing :musical_note:", currentPlaying.getInfo().uri)
      .setDescription(currentPlaying.getInfo().title)
      .addField("Position", String.join("", posString), false)
      .addField(
        "Progress",
        minPos +
        ":" +
        ((secPos < 10) ? "0" + secPos : secPos) +
        " / " +
        minDur +
        ":" +
        ((secDur < 10) ? "0" + secDur : secDur),
        false
      );
    textChannel.sendMessageEmbeds(builder.build()).queue();
  }
}
