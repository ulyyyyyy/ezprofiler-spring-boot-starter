package com.lilithqa.ezprofiler.repository;

import com.lilithqa.ezprofiler.config.EzProfilerProperties;
import com.lilithqa.ezprofiler.scanner.AggregateInformation;
import com.lilithqa.ezprofiler.util.JsonUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * 数据库链接
 *
 * @author 黑黑
 * @date 2021-03-10
 */
public class MyMongoTemplate {

    private MongoClient mongoClient;

    private final EzProfilerProperties properties;

    // 改成单例模式
    public MyMongoTemplate(EzProfilerProperties properties) {
        String uri = properties.getUri();
        this.mongoClient = new MongoClient(new MongoClientURI(uri));
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
        MongoCollection<Document> collection = db.getCollection(properties.getDbTableName());
        collection.insertOne(Document.parse(JsonUtils.object2Json(information)));
    }
}
