package me.harpylmao.commands.bet;

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

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    01/01/2022 20
*/

@CommandParams(
  name = "roulette",
  category = "bet",
  usage = """
                **Usage:**
                    - p.roulette <amount> | If your amount is 10 you have the probability to duplicate or lost all.
                """,
  cooldown = 18_000_000
)
public class RouletteCommand implements Command {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (args.length == 0) {
      command.reply(CommandManager.INSTANCE.getParamsMap().get(this).usage());
      return;
    }

    int amount = Integer.parseInt(args[0]);

    Random random = new Random();
    boolean win = random.nextBoolean();

    User user = Bot
      .getInstance()
      .getUserManager()
      .getUserObjectRepository()
      .find(member.getId());

    if (win) {
      int winnedAmount = Math.multiplyExact(amount, 2);
      user.setEconomy(user.getEconomy() + winnedAmount);
      event
        .getMessage()
        .reply(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription(
              "You have bet on roulette and you have won double! (" +
              winnedAmount +
              ")"
            )
            .build()
        )
        .queue();
    } else {
      user.setEconomy(user.getEconomy() - amount);
      event
        .getMessage()
        .reply(
          new EmbedBuilder()
            .setColor(Bot.getInstance().getPanoramic().getColorColored())
            .setDescription(
              "You have bet on roulette and have lost with " +
              (amount * 2) +
              " less money. :("
            )
            .build()
        )
        .queue();
    }
  }
}
