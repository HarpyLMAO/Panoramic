package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(name = "shuffle", category = "music")
public class ShuffleCommand implements Command {

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
    musicManager.getGuildAudio(event.getGuild()).getTrackScheduler().shuffle();
    textChannel
      .sendMessageEmbeds(
        new EmbedBuilder()
          .setColor(Bot.getInstance().getPanoramic().getColorColored())
          .setDescription("🔀 Shuffled Queue!")
          .build()
      )
      .queue();
  }
}
