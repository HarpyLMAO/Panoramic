package me.harpylmao.commands.music;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

@CommandParams(name = "play", category = "music")
public class PlayCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member author,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    Bot
      .getInstance()
      .getGuildAudioManager()
      .loadAndPlay(command, StringUtils.join(args, ' ', 1, args.length), true);
  }
}
