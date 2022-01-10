package me.harpylmao.commands.tickets;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.managers.Panoramic;
import me.harpylmao.managers.ticket.Ticket;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 23
*/
public class TicketCommand implements BaseCommand {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    Panoramic panoramic = Bot.getInstance().getPanoramic();
    if (args.length == 0) command.reply(this.usage());

    switch (args[0]) {
      case "opener" -> {
        Ticket.createTicketOpener(textChannel);
      }
      case "logs" -> {
        panoramic.setTicketLogsChannelId(textChannel.getId());
        Bot.getInstance().savePanoramic(panoramic);
      }
    }
  }

  @Override
  public String usage() {
    return """
                ***Usage:***
                    - p.ticket opener
                    - p.ticket logs
                """;
  }

  @Override
  public String getName() {
    return "ticket";
  }

  @Override
  public String category() {
    return "main";
  }
}
