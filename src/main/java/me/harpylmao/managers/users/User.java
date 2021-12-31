package me.harpylmao.managers.users;

import lombok.Getter;
import lombok.Setter;
import me.harpylmao.Bot;
import me.harpylmao.managers.model.Model;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.beans.ConstructorProperties;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    31/12/2021 19
*/

@Getter
@Setter
public class User implements Model {

    private final String id;

    private int economy;

    public User(String id) {
        this.id = id;
    }

    @ConstructorProperties({
            "id",
            "economy"
    })
    public User(
            String id,
            int economy
    ) {
        this(id);
        this.economy = economy;
    }

    @Override
    public String getId() {
        return id;
    }

    public static class UserListener extends ListenerAdapter {

        @Override
        public void onReady(ReadyEvent event) {
            event.getJDA().getUsers().forEach(users -> {
                event.getJDA().retrieveUserById(users.getId()).queue(discordUser -> {
                    User user = Bot.getInstance().getUserManager().getUserObjectRepository().find(discordUser.getId());

                    if (user == null) {
                        user = new User(discordUser.getId());
                        Bot.getInstance().saveUser(user);
                        System.out.println("user created: " + discordUser.getName());
                    }
                });
            });
        }

        @Override
        public void onGuildMemberJoin(GuildMemberJoinEvent event) {
            User user = Bot.getInstance().getUserManager().getUserObjectRepository().find(event.getMember().getId());

            if (user == null) {
                user = new User(event.getMember().getId());
                Bot.getInstance().saveUser(user);
                System.out.println("user created: " + event.getMember().getUser().getName());
            }
        }
    }
}
