package me.harpylmao.utils;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaginatedMenu {

  private PaginatedMenu paginatedMenu;

  private String name;
  private boolean selectable;
  private EventWaiter eventWaiter;
  private List<PageItem> items = new ArrayList<>();

  public PaginatedMenu() {
    this.paginatedMenu = this;
  }

  public PaginatedMenu setName(String name) {
    this.paginatedMenu.setName(name);
    return this;
  }

  public PaginatedMenu setSelectable(boolean selectable) {
    this.paginatedMenu.setSelectable(selectable);
    return this;
  }

  public PaginatedMenu setEventWaiter(EventWaiter eventWaiter) {
    this.paginatedMenu.setEventWaiter(eventWaiter);
    return this;
  }

  public PaginatedMenu addItems(PageItem item) {
    this.paginatedMenu.getItems().add(item);
    return this;
  }

  public EmbedBuilder build() {
    if (name != null || eventWaiter != null || items.size() != 0) {
      EmbedBuilder embedBuilder = new EmbedBuilder()
              .setTitle(name);
      return embedBuilder;
    } else {
      System.out.println("An error while creating paginated menu");
      return null;
    }
  }

  @Getter
  @Setter
  public static class PageItem {

    private PageItem pageItem;

    private String name;
    private String description;

    public PageItem() {
      this.pageItem = this;
    }

    public PageItem setName(String name) {
      this.pageItem.setName(name);
      return this;
    }

    public PageItem setDescription(String description) {
      this.pageItem.setDescription(name);
      return this;
    }

    public PageItem build() {
      if (name != null || description != null) {
        return this;
      } else {
        System.out.println("An error ocurred while creating the page item");
        return null;
      }
    }
  }
}
