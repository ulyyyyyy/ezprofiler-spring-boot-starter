package com.lilithqa.ezprofiler.repository;

import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.scanner.AggregateInformation;
import com.lilithqa.ezprofiler.util.DocumentUtil;
import com.lilithqa.ezprofiler.util.JsonUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 */
@Component
public class MyMongoTemplate {

    @Autowired
    private MongoClient mongoClient;

    private final EzProfilerProperties properties;

    public MyMongoTemplate(EzProfilerProperties properties) {
        this.mongoClient = new MongoClient(properties.getHost(), properties.getPort());
        this.properties = properties;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Object getInfos() {
        return null;
    }

    public void setMethodAccessInfo(AggregateInformation information) {
        MongoDatabase db = this.mongoClient.getDatabase(properties.getDataBaseName());
        MongoCollection<Document> collection = db.getCollection(properties.getTableName());
        System.out.println(information);
        collection.insertOne(Document.parse(JsonUtils.object2Json(information)));
    }
}
