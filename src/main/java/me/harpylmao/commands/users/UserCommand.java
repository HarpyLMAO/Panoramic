package me.harpylmao.commands.users;

import java.text.SimpleDateFormat;
import java.util.Date;
import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.BaseCommand;
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
    31/12/2021 23
*/
public class UserCommand implements BaseCommand {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();

    if (args.length == 0) {
      User user = Bot
        .getInstance()
        .getUserManager()
        .getUserObjectRepository()
        .find(member.getId());

      if (user == null) return;

      EmbedBuilder ownProfileEmbed = new EmbedBuilder()
        .setColor(Bot.getInstance().getPanoramic().getColorColored())
        .setAuthor(
          member.getUser().getName() + "'s profile.",
          null,
          member.getEffectiveAvatarUrl()
        )
        .addField("Economy ~~$~~", user.getEconomy() + " ~~$~~", true)
        .addField("Cars", "**soon**", true)
        .addField("Level", "**soon**", true)
        .setFooter(formatter.format(date));
      event.getMessage().reply(ownProfileEmbed.build()).queue();
    } else {
      User targetUser = Bot
        .getInstance()
        .getUserManager()
        .getUserObjectRepository()
        .find(args[0].replace("<@!", "").replace(">", ""));
      Member targetMember = command
        .getGuild()
        .retrieveMemberById(args[0].replace("<@!", "").replace(">", ""))
        .complete();

      if (targetUser == null) return;

      EmbedBuilder targetProfileEmbed = new EmbedBuilder()
        .setColor(Bot.getInstance().getPanoramic().getColorColored())
        .setAuthor(
          targetMember.getUser().getName() + "'s profile.",
          null,
          targetMember.getEffectiveAvatarUrl()
        )
        .addField("Economy ~~$~~", targetUser.getEconomy() + " ~~$~~", true)
        .addField("Cars", "**soon**", true)
        .addField("Level", "**soon**", true)
        .setFooter(formatter.format(date));
      event.getMessage().reply(targetProfileEmbed.build()).queue();
    }
  }

  @Override
  public String usage() {
    return """
                **Usage:**
                    - p.user | for see your user profile
                    - p.user <user tag> | for see other user profile
                """;
  }

  @Override
  public String getName() {
    return "user";
  }

  @Override
  public String category() {
    return "main";
  }
}
