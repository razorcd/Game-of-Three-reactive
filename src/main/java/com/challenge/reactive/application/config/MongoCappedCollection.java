package com.challenge.reactive.application.config;

import com.challenge.reactive.application.model.Action;
import com.challenge.reactive.application.model.Game;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoCollectionUtils;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import java.util.Arrays;
import java.util.List;

@Configuration
@Priority(0)
public class MongoCappedCollection {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoCappedCollection.class);

    private List<String> mongoCollectionsToCapp = Arrays.asList(
                Action.class.getSimpleName().toLowerCase(),
                Game.class.getSimpleName().toLowerCase());

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Cap mongo collection to support tailing.
     */
    @PostConstruct
    private void createCappedCollections(){

        mongoCollectionsToCapp.forEach(collectionName -> {
            if (!mongoTemplate.collectionExists(collectionName)) return;

            Document stats = mongoTemplate.getDb().runCommand(new Document("collStats", collectionName));
            if ((Boolean) stats.get("capped") == true) {
                LOGGER.debug("Collection {} is already capped.", collectionName);
            } else {
                mongoTemplate.executeCommand("{\"convertToCapped\":\""+collectionName+"\",\"size\":9128}");
                LOGGER.debug("Collection {} was capped now.", collectionName);
            }
        });
    }
}
