package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "skip", category = "music")
public class SkipCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (
      event.getMember() == null ||
      event.getMember().getVoiceState() == null ||
      !event.getMember().getVoiceState().inVoiceChannel() ||
      event.getMember().getVoiceState().getChannel() == null
    ) {
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("Skipped to the next song!")
            .build()
        )
        .queue();
      return;
    }

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
            .setDescription("Please use `/join` first!")
            .build()
        )
        .queue();
      return;
    }

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

    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription("Skipped to the next song.")
          .build()
      )
      .queue();

    Bot
      .getInstance()
      .getGuildAudioManager()
      .getGuildAudio(event.getGuild())
      .getTrackScheduler()
      .skip();
  }
}
