package me.harpylmao.managers.ticket;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 21
*/

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.harpylmao.managers.repository.MongoRepositoryModel;
import me.harpylmao.managers.repository.ObjectRepository;

@Getter
public class TicketManager {

  private ObjectRepository<Ticket> ticketObjectRepository;

  public TicketManager(MongoDatabase mongoDatabase) {
    MongoCollection<Ticket> collection = mongoDatabase.getCollection(
      "tickets",
      Ticket.class
    );
    this.ticketObjectRepository = new MongoRepositoryModel<>(collection);
  }
}
