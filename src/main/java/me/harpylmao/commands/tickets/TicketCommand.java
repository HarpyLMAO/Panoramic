package me.harpylmao.commands.tickets;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.CommandManager;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.managers.Panoramic;
import me.harpylmao.managers.ticket.Ticket;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 23
*/

@CommandParams(
  name = "ticket",
  category = "music",
  usage = """
                ***Usage:***
                    - p.ticket opener
                    - p.ticket logs
                """,
  permissions = { Permission.ADMINISTRATOR }
)
public class TicketCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    Panoramic panoramic = Bot.getInstance().getPanoramic();
    if (args.length == 0) command.reply(
      CommandManager.INSTANCE.getParamsMap().get(this).usage()
    );

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
}
