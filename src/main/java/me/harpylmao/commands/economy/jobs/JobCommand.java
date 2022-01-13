package me.harpylmao.commands.economy.jobs;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.utils.PaginatedMenu;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

@CommandParams(
        name = "jobs",
        category = "economy",
        usage = """
                  **USAGE:**
                    - p.jobs
                """
)
public class JobCommand implements Command {

  @Override
  public void execute(CommandEvent command, TextChannel textChannel, Member member, String[] args, GuildMessageReceivedEvent event) {
    if (args.length == 0) {
      PaginatedMenu paginatedMenu = new PaginatedMenu()
              .setName("Hola")
              .setEventWaiter(Bot.getInstance().getEventWaiter())
              .addItems(
                      new PaginatedMenu.PageItem()
                              .setName("Item Hola")
                              .setDescription("Item Hola Desc")
                              .build()
              )
              .addItems(
                      new PaginatedMenu.PageItem()
                              .setName("Item Adios")
                              .setDescription("Item Adios Desc")
                              .build()
              );

      MessageAction messageAction = textChannel.sendMessageEmbeds(paginatedMenu.build().build());
      messageAction.setActionRow(Button.success("button:back", ""));
    }
  }
}
