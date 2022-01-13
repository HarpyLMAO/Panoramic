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
  name = "youtube",
  category = "fun",
  aliases = { "yt" },
  usage = """
                **USAGE:**
                    - p.youtube | watch youtube videos together
                """
)
public class YoutubeWatchTogetherCommand implements Command {

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
        .setTargetApplication("880218394199220334")
        .queue(invite -> {
          Utils
            .sendGameMessage(invite.getUrl(), "Youtube Together", textChannel)
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
