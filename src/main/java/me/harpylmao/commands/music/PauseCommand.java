package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.audio.TrackScheduler;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PauseCommand implements BaseCommand {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (
      Bot
        .getInstance()
        .getGuildAudioManager()
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
    TrackScheduler scheduler = Bot
      .getInstance()
      .getGuildAudioManager()
      .getGuildAudio(event.getGuild())
      .getTrackScheduler();
    scheduler.setPaused(true);
    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription(":pause_button: Paused the Player!")
          .build()
      )
      .queue();
  }

  @Override
  public String category() {
    return "music";
  }

  @Override
  public String usage() {
    return "USAGE: \n" + " - /pause";
  }

  @Override
  public String getName() {
    return "pause";
  }
}
