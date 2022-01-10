package me.harpylmao.commands.command.defaults;

import java.util.Locale;
import java.util.stream.Collectors;
import lombok.Getter;
import me.harpylmao.Bot;
import me.harpylmao.commands.command.CommandManager;
import me.harpylmao.commands.command.interfaces.BaseCommand;
import me.harpylmao.commands.command.objects.CommandEvent;
import me.harpylmao.managers.Panoramic;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

@Getter
public class DefaultHelpCommand implements BaseCommand {

  @Override
  public void execute(
    CommandEvent command,
    TextChannel textChannel,
    Member member,
    String[] args,
    GuildMessageReceivedEvent event
  ) {
    Panoramic panoramic = Bot.getInstance().getPanoramic();
    EmbedBuilder embedBuilder = new EmbedBuilder()
      .setColor(panoramic.getColorColored())
      .setTitle("Panoramic Help Menus")
      .setDescription(
        "\n" +
        "**Main help:** If you want know all main commands\n" +
        "please select _Main help_ in the options.\n" +
        "\n" +
        "**Music help:** If you want know all about music bot\n" +
        "choose in the menu _Music help_.\n" +
        "\n" +
        "**Cars help:** If you want know about races & cars select\n" +
        "_Cars help_ in the options of the menu.\n"
      )
      .setFooter(
        "Information requested by " + member.getUser().getName(),
        member.getUser().getEffectiveAvatarUrl()
      );
    textChannel
      .sendMessageEmbeds(embedBuilder.build())
      .setActionRow(getSelectionMenu())
      .queue();
  }

  private static SelectionMenu getSelectionMenu() {
    return SelectionMenu
      .create("menu:help")
      .setPlaceholder("Choose what help you need")
      .setRequiredRange(1, 1)
      .addOption("Main help", "main", "Shows you all main help of the bot.")
      .addOption(
        "Music help",
        "music",
        "Shows you all commands of the music bot."
      )
      .addOption(
        "Cars help",
        "cars",
        "Shows you all commands about the cars and races."
      )
      .build();
  }

  @Override
  public String usage() {
    return "usage";
  }

  @Override
  public String getName() {
    return "help";
  }

  public static class HelpCommandListeners extends ListenerAdapter {

    @Override
    public void onSelectionMenu(SelectionMenuEvent event) {
      Panoramic panoramic = Bot.getInstance().getPanoramic();
      if (event.getSelectionMenu().getId().equalsIgnoreCase("menu:help")) {
        event.getInteraction().deferEdit().complete();
        switch (event.getValues().get(0).toLowerCase(Locale.ROOT)) {
          case "main" -> {
            if (this.mainCommands().isEmpty()) return;
            MessageAction mainMessage = event
              .getTextChannel()
              .sendMessageEmbeds(
                new EmbedBuilder()
                  .setColor(panoramic.getColorColored())
                  .setDescription(this.mainCommands())
                  .build()
              );

            mainMessage.setActionRow(getSelectionMenu()).queue();
          }
          case "music" -> {
            if (this.musicCommands().isEmpty()) return;
            MessageAction musicMessage = event
              .getTextChannel()
              .sendMessageEmbeds(
                new EmbedBuilder()
                  .setColor(panoramic.getColorColored())
                  .setDescription(this.musicCommands())
                  .build()
              );

            musicMessage.setActionRow(getSelectionMenu()).queue();
          }
          case "cars" -> {
            if (this.carsCommands().isEmpty()) return;
            MessageAction carsMessage = event
              .getTextChannel()
              .sendMessageEmbeds(
                new EmbedBuilder()
                  .setColor(panoramic.getColorColored())
                  .setDescription(this.carsCommands())
                  .build()
              );

            carsMessage.setActionRow(getSelectionMenu()).queue();
          }
        }
      }
    }

    private String mainCommands() {
      return CommandManager.INSTANCE
        .getCommands()
        .stream()
        .filter(baseCommand -> baseCommand.category().equalsIgnoreCase("main"))
        .map(s -> " ➥ `" + s.getName() + "`")
        .collect(Collectors.joining("\n"));
    }

    private String musicCommands() {
      return CommandManager.INSTANCE
        .getCommands()
        .stream()
        .filter(baseCommand -> baseCommand.category().equalsIgnoreCase("music"))
        .map(s -> " ➥ `" + s.getName() + "`")
        .collect(Collectors.joining("\n"));
    }

    private String carsCommands() {
      return CommandManager.INSTANCE
        .getCommands()
        .stream()
        .filter(baseCommand -> baseCommand.category().equalsIgnoreCase("cars"))
        .map(s -> " ➥ `" + s.getName() + "`")
        .collect(Collectors.joining("\n"));
    }
  }
}
