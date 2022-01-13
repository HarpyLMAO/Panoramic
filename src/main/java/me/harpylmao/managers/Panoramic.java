package me.harpylmao.managers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.*;
import java.beans.ConstructorProperties;
import lombok.Getter;
import lombok.Setter;
import me.harpylmao.Bot;
import me.harpylmao.managers.model.Model;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 20
*/
@Getter
@Setter
public class Panoramic implements Model {

  private final String id;

  private String color;

  private String ticketOpenerId;
  private String ticketLogsChannelId;

  public Panoramic(String id) {
    this.id = id;
  }

  @ConstructorProperties({ "id", "color", "ticketOpenerId", "ticketLogsChannelId" })
  public Panoramic(
    String id,
    String color,
    String ticketOpenerId,
    String ticketLogsChannelId
  ) {
    this(id);
    this.color = color;
    this.ticketOpenerId = ticketOpenerId;
    this.ticketLogsChannelId = ticketLogsChannelId;
  }

  @JsonIgnore
  public Color getColorColored() {
    return Bot.getInstance().getColor(color);
  }

  @Override
  public String getId() {
    return id;
  }

  public static class PanoramicListeners extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {}
  }
}
