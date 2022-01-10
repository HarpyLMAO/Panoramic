package me.harpylmao.commands.command.interfaces;

import java.util.Collections;
import java.util.List;
import me.harpylmao.commands.command.objects.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface BaseCommand {
  void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  );

  default String usage() {
    return "N/A";
  }

  default List<String> aliases() {
    return Collections.emptyList();
  }

  String getName();

  default String category() {
    return "General";
  }
}
