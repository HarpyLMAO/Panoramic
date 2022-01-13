package me.harpylmao.commands.economy;

import java.util.Random;
import me.harpylmao.Bot;
import me.harpylmao.commands.command.CommandManager;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.managers.users.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(
  name = "work",
  category = "economy",
  usage = """
                USAGE:
                    - p.work | 5 hours cooldown
                """,
  cooldown = 18_000_000
)
public class WorkCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (args.length == 0) {
      User user = Bot
        .getInstance()
        .getUserManager()
        .getUserObjectRepository()
        .find(member.getId());
      if (user == null) return;

      Random random = new Random();
      int salary = random.nextInt(800, 2000);

      event
        .getMessage()
        .replyEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription(
              "You worked a part time, your salary received is " + salary + " 💵"
            )
            .build()
        )
        .queue();

      user.setEconomy(user.getEconomy() + salary);
      Bot.getInstance().getUserManager().getUserObjectRepository().save(user);
    } else {
      command.reply(CommandManager.INSTANCE.getParamsMap().get(this).usage());
    }
  }
}
