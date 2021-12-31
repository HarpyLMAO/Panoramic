package me.harpylmao.commands.economy;

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
public class EconomyCommand implements BaseCommand {
    @Override
    public void execute(CommandEvent command, TextChannel textChannel, Member member, String[] args, GuildMessageReceivedEvent event) {
        if (args.length == 0) {
            command.reply(this.usage());
            return;
        }

        switch (args[0]) {
            case "add" -> {
                try {
                    if (args.length == 3) {
                        User user = Bot.getInstance().getUserManager().getUserObjectRepository().find(args[1].replace("<@!", "").replace(">", ""));
                        int amount = Integer.parseInt(args[2], 20);

                        if (user == null) return;

                        Member targetMember = command.getGuild().retrieveMemberById(user.getId()).complete();

                        user.setEconomy(user.getEconomy() + amount);
                        event.getMessage().reply(new EmbedBuilder()
                                        .setColor(Bot.getInstance().getPanoramic().getColorColored())
                                        .setDescription("Added " + amount + " ~~$~~ to " + targetMember.getUser().getName() + "'s bank.")
                                        .build())
                                .queue();

                        Bot.getInstance().saveUser(user);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String usage() {
        return """
                **Usage:**
                    - p.eco add <user tag> <amount>
                    - p.eco reset <user tag>
                    - p.eco remove <user tag> <amount>
                """;
    }

    @Override
    public String getName() {
        return "eco";
    }

    @Override
    public String category() {
        return "economy";
    }
}
