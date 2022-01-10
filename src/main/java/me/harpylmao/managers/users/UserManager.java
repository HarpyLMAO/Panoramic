package me.harpylmao.managers.users;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.harpylmao.managers.repository.MongoRepositoryModel;
import me.harpylmao.managers.repository.ObjectRepository;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    31/12/2021 19
*/

@Getter
public class UserManager {

  private final ObjectRepository<User> userObjectRepository;

  public UserManager(MongoDatabase mongoDatabase) {
    MongoCollection<User> collection = mongoDatabase.getCollection(
      "users",
      User.class
    );
    this.userObjectRepository = new MongoRepositoryModel<>(collection);
  }
}
