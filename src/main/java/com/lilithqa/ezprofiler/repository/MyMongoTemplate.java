package com.lilithqa.ezprofiler.repository;

import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.scanner.AggregateInformation;
import com.lilithqa.ezprofiler.util.DocumentUtil;
import com.lilithqa.ezprofiler.util.JsonUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * @author Administrator
 */
@Component
public class MyMongoTemplate {

    @Autowired
    private MongoClient mongoClient;

    private final EzProfilerProperties properties;

    public MyMongoTemplate(EzProfilerProperties properties) {
        ServerAddress serverAddress = new ServerAddress(properties.getHost(), properties.getPort());
        ArrayList<ServerAddress> serverAddresses = new ArrayList<>();
        ArrayList<MongoCredential> credentials  = new ArrayList<>();
        serverAddresses.add(serverAddress);
        if (properties.getDbUserName() != null && properties.getDbPassword() != null) {
            MongoCredential credential = MongoCredential.createScramSha1Credential(properties.getDbUserName(),
                    properties.getDataBaseName(), properties.getDbPassword().toCharArray());
            credentials.add(credential);
        }

        this.mongoClient = new MongoClient(serverAddresses, credentials);
        this.properties = properties;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Object getInfos() {
//        MongoClient mongoClient;
//        try {
//            ServerAddress serverAddress = new ServerAddress(properties.getHost(), properties.getPort());
//            ArrayList<ServerAddress> serverAddresses = new ArrayList<>();
//            ArrayList<MongoCredential> credentials  = new ArrayList<>();
//
//            serverAddresses.add(serverAddress);
//            if (properties.getDbUserName() == null&& properties.getDbPassword() == null) {
//                MongoCredential credential = MongoCredential.createScramSha1Credential(properties.getDbUserName(),
//                        properties.getDataBaseName(), properties.getDbPassword().toCharArray());
//                credentials.add(credential);
//            }
//            mongoClient = new MongoClient(serverAddresses, credentials);
//            MongoDatabase mongoDatabase = mongoClient.getDatabase(properties.getDataBaseName());
//            MongoCollection<Document> collection = mongoDatabase.getCollection(properties.getTableName());
//            FindIterable<Document> documents = collection.find();
//            for (Document document : documents) {
//                System.out.println(document);
//            }
//            return collection;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//        }
        return null;
    }

    public void setMethodAccessInfo(AggregateInformation information) {
        MongoDatabase db = this.mongoClient.getDatabase(properties.getDataBaseName());
        MongoCollection<Document> collection = db.getCollection(properties.getTableName());
        System.out.println(information);
        collection.insertOne(Document.parse(JsonUtils.object2Json(information)));
    }
}
