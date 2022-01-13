package me.harpylmao.commands.music;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import me.harpylmao.Bot;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "bassboost", category = "music")
public class BassBoostCommand implements Command {

  private float[] BASS_BOOST = {
    0.2f,
    0.15f,
    0.1f,
    0.05f,
    0.0f,
    -0.05f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
    -0.1f,
  };

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
      member == null ||
      member.getVoiceState() == null ||
      !member.getVoiceState().inVoiceChannel() ||
      member.getVoiceState().getChannel() == null
    ) {
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("Please connect to a voice channel first!")
            .build()
        )
        .queue();
      return;
    }

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

    String input = args[0];

    if (Integer.parseInt(input) == 50 || Integer.parseInt(input) == 0) {
      musicManager
        .getGuildAudio(event.getGuild())
        .getAudioPlayer()
        .setFilterFactory(null);
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("Bassboost has been disabled.")
            .build()
        )
        .queue();
      return;
    }

    if (Integer.parseInt(input) >= 401 || Integer.parseInt(input) <= -1) {
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("The bassboost is out of range! [0-500]")
            .build()
        )
        .queue();
      return;
    }

    EqualizerFactory equalizer = new EqualizerFactory();

    double amount = Double.parseDouble(input);

    for (int i = 0; i < BASS_BOOST.length; i++) {
      equalizer.setGain(i, (float) (BASS_BOOST[i] + (amount / 100)));
    }

    musicManager
      .getGuildAudio(event.getGuild())
      .getAudioPlayer()
      .setFilterFactory(equalizer);

    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription("Bassboost has been applied, at the level " + input + "%.")
          .build()
      )
      .queue();
  }
}
