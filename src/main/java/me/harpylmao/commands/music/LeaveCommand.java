package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "leave", category = "music")
public class LeaveCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (
      (event.getMember() == null) ||
      (event.getMember().getVoiceState() == null) ||
      !event.getMember().getVoiceState().inVoiceChannel() ||
      (event.getMember().getVoiceState().getChannel() == null)
    ) {
      textChannel
        .sendMessageEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription("You are not in a voice channel!")
            .build()
        )
        .queue();
      return;
    }

    Bot
      .getInstance()
      .getGuildAudioManager()
      .leaveVoiceChannel(event.getGuild());
    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription("Left voice channel!")
          .build()
      )
      .queue();
  }
}
