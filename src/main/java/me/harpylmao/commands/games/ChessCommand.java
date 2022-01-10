package me.harpylmao.commands.games;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class ChessCommand implements BaseCommand {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    if (args.length == 0) {
      VoiceChannel voiceChannel = member.getVoiceState().getChannel();

      if (voiceChannel == null) {
        command.reply("You must be in a channel");
        return;
      }

      voiceChannel
        .createInvite()
        .setTemporary(false)
        .setTargetApplication("832012774040141894")
        .queue(invite -> {
          Utils
            .sendGameMessage(invite.getUrl(), "Chess", textChannel)
            .queue(message -> {
              Bot
                .getInstance()
                .getEventWaiter()
                .waitForEvent(
                  ButtonClickEvent.class,
                  buttonClickEvent -> {
                    return buttonClickEvent
                      .getMessageId()
                      .equalsIgnoreCase(message.getId());
                  },
                  buttonClickEvent -> {
                    buttonClickEvent.getInteraction().deferReply().complete();
                  }
                );
            });
        });
    }
  }

  @Override
  public String usage() {
    return """
                **USAGE:**
                    - p.chess
                """;
  }

  @Override
  public String getName() {
    return "chess";
  }

  @Override
  public String category() {
    return "fun";
  }
}
