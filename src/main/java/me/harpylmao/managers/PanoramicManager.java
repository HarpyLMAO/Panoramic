package me.harpylmao.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.harpylmao.managers.repository.MongoRepositoryModel;
import me.harpylmao.managers.repository.ObjectRepository;

/*
    Author: HarpyLMAO.
    All rights reserved.
    Project: Panoramic
    30/12/2021 20
*/
@Getter
public class PanoramicManager {

    private final ObjectRepository<Panoramic> panoramicObjectRepository;

    public PanoramicManager(MongoDatabase mongoDatabase) {
        MongoCollection<Panoramic> collection = mongoDatabase.getCollection(
            "panoramic",
                Panoramic.class
        );
        this.panoramicObjectRepository = new MongoRepositoryModel<>(collection);
    }
}
