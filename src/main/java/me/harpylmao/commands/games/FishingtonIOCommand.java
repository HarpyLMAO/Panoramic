package me.harpylmao.commands.games;

import me.harpylmao.Bot;
import me.harpylmao.commands.command.interfaces.Command;
import me.harpylmao.commands.command.interfaces.CommandParams;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandParams(
  name = "fishington",
  category = "fun",
  usage = """
                **USAGE:**
                    - p.fishington
                """
)
public class FishingtonIOCommand implements Command {

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
        .setTargetApplication("814288819477020702")
        .queue(invite -> {
          Utils
            .sendGameMessage(invite.getUrl(), "Fishington.io", textChannel)
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
}
