package me.harpylmao.managers.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.ConstructorProperties;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import me.harpylmao.Bot;
import me.harpylmao.managers.Panoramic;
import me.harpylmao.managers.model.Model;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 20
*/

@Getter
@Setter
public class Ticket implements Model {

  @Getter
  private static List<Ticket> tickets = new ArrayList<>();

  private final String id;

  public Ticket() {
    this.id = UUID.randomUUID().toString();

    tickets.add(this);
    Bot.getInstance().getTicketManager().getTicketObjectRepository().save(this);
  }

  private static void createTicket(Member ticketOwner, Guild guild) {
    Panoramic panoramic = Bot.getInstance().getPanoramic();
    if (
      guild
        .getCategories()
        .stream()
        .filter(category -> category.getName().equalsIgnoreCase("tickets"))
        .findFirst()
        .orElse(null) ==
      null
    ) {
      guild.createCategory("tickets").queue();
    }

    Category category = guild
      .getCategories()
      .stream()
      .filter(category1 -> category1.getName().equalsIgnoreCase("tickets"))
      .findFirst()
      .orElse(null);
    if (category == null) return;

    category
      .createTextChannel(
        ticketOwner.getUser().getName() + "-" + getRandomTicketNumber()
      )
      .setTopic("ticket")
      .queue(textChannel -> {
        textChannel
          .createPermissionOverride(ticketOwner)
          .setAllow(
            Permission.VIEW_CHANNEL,
            Permission.MESSAGE_WRITE,
            Permission.MESSAGE_READ,
            Permission.MESSAGE_HISTORY,
            Permission.MESSAGE_EMBED_LINKS,
            Permission.MESSAGE_ATTACH_FILES,
            Permission.MESSAGE_ADD_REACTION,
            Permission.MESSAGE_EXT_EMOJI
          )
          .queue();

        new File(
          ticketOwner.getUser().getName() +
          ticketOwner.getUser().getDiscriminator() +
          ".txt"
        );
        MessageAction closeMessage = textChannel.sendMessage(
          new EmbedBuilder()
            .setColor(panoramic.getColorColored())
            .setDescription(
              """
                                                Cuando tu ticket este
                                                resuelto y te hayan ayudado
                                                puedes cerrar tu ticket usando
                                                el botón rojo.
                                            """
            )
            .build()
        );
        Button closeButton = Button
          .danger("closeticket:button", "Close ticket")
          .withEmoji(Emoji.fromUnicode("❌"));
        closeMessage
          .setActionRow(closeButton)
          .queue(message -> {
            Bot
              .getInstance()
              .getEventWaiter()
              .waitForEvent(
                ButtonClickEvent.class,
                event -> {
                  if (
                    Objects.requireNonNull(event.getMember()).getUser().isBot()
                  ) return false;
                  return event.getMessageId().equalsIgnoreCase(message.getId());
                },
                event -> {
                  event.getInteraction().deferEdit().complete();
                  message.getTextChannel().delete().queue();

                  File file = new File(
                    event.getUser().getName() +
                    event.getUser().getDiscriminator() +
                    ".txt"
                  );

                  if (file.exists()) {
                    TextChannel logsChannel = Objects
                      .requireNonNull(event.getGuild())
                      .getTextChannelById(panoramic.getTicketLogsChannelId());
                    if (logsChannel == null) return;
                    RestAction<Message> messageRestAction = logsChannel.sendFile(
                      file,
                      event.getMember().getUser().getName() + ".txt"
                    );
                    messageRestAction.queue();
                    Runnable deleteFile = file::delete;

                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
                    service.schedule(deleteFile, 5, TimeUnit.SECONDS);
                    service.shutdown();
                  }
                }
              );
          });
      });
  }

  public static void createTicketOpener(TextChannel textChannel) {
    Panoramic panoramic = Bot.getInstance().getPanoramic();

    MessageAction messageAction = textChannel.sendMessage(
      new EmbedBuilder()
        .setTitle("Support tickets")
        .setColor(panoramic.getColorColored())
        .setDescription(
          """
                                                Para crear un ticket
                                                reacciona al boton verde del mensaje.
                                                          
                                                Por favor no le hagas tag a ningun miembro
                                                del staff. En ese caso tomaremos medidas.
                                        """
        )
        .build()
    );

    Button button = Button
      .success("ticket:button", "Create ticket")
      .withEmoji(Emoji.fromUnicode("🎟"));
    messageAction
      .setActionRow(button)
      .queue(message -> {
        panoramic.setTicketOpenerId(message.getId());
        Bot.getInstance().savePanoramic(panoramic);
      });
  }

  @JsonIgnore
  private static String getRandomTicketNumber() {
    Random random = new Random();
    int number = random.nextInt(999999);
    return String.format("%04d", number);
  }

  @Override
  public String getId() {
    return id;
  }

  public static class TicketEvents extends ListenerAdapter {

    private final Panoramic panoramic;

    public TicketEvents() {
      this.panoramic = Bot.getInstance().getPanoramic();
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
      if (
        event.getMessageId().equalsIgnoreCase(panoramic.getTicketOpenerId())
      ) {
        event.getInteraction().deferEdit().complete();
        createTicket(
          event.getMember(),
          Objects.requireNonNull(event.getGuild())
        );
      }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
      if (event.getMessage().isWebhookMessage()) return;
      Member member = event.getMember();
      if (member.getUser().isBot()) return;
      if (event.getMessage().getTextChannel().getTopic() != null) {
        if (
          event.getMessage().getTextChannel().getTopic().startsWith("ticket")
        ) {
          if (
            event
              .getMessage()
              .getTextChannel()
              .getName()
              .startsWith(
                member.getUser().getName().toLowerCase().replace(" ", "-")
              )
          ) {
            try {
              File log = new File(
                member.getUser().getName() +
                member.getUser().getDiscriminator() +
                ".txt"
              );
              if (!log.exists()) {
                System.out.println("We had to make a new file.");
                log.createNewFile();
              }
              PrintWriter out = new PrintWriter(new FileWriter(log, true));
              out
                .append(member.getUser().getName())
                .append(": ")
                .append(event.getMessage().getContentRaw())
                .append("\n");
              out.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          } else {
            System.out.println("puta que lo has hecho mal");
          }
        }
      }
    }
  }
}
