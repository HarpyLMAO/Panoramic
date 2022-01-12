package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.audio.TrackScheduler;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "resume", category = "music")
public class ResumeCommand implements Command {

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

    TrackScheduler scheduler = musicManager
      .getGuildAudio(event.getGuild())
      .getTrackScheduler();
    scheduler.setPaused(false);
    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription(":arrow_forward: Unpaused the Player!")
          .build()
      )
      .queue();
  }
}
