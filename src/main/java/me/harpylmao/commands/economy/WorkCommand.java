package me.harpylmao.commands.economy;

import java.util.Random;
import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.managers.users.User;
import me.harpylmao.utils.Cooldown;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WorkCommand implements BaseCommand {

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

      if (!user.getWorkCooldown().hasExpired()) {
        command.reply(
          "You have a cooldown, expires in " +
          user.getWorkCooldown().getRemaining() +
          "s."
        );
        return;
      }

      user.setWorkCooldown(new Cooldown(18_000_000));

      Random random = new Random();
      int salary = random.nextInt(800, 2000);

      event
        .getMessage()
        .replyEmbeds(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription(
              "You worked a part time, your salary received is " +
              salary +
              " 💵"
            )
            .build()
        )
        .queue();
      Bot.getInstance().getUserManager().getUserObjectRepository().save(user);
    } else {
      command.reply(this.usage());
    }
  }

  @Override
  public String category() {
    return "economy";
  }

  @Override
  public String getName() {
    return "work";
  }

  @Override
  public String usage() {
    return """
                USAGE:
                    - p.work | 5 hours cooldown
                """;
  }
}
