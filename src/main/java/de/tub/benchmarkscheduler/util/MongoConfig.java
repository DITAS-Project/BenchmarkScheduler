package de.tub.benchmarkscheduler.util;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "de.tub.benchmarkscheduler.data")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class MongoConfig extends AbstractMongoConfiguration {


    @Override
    protected String getDatabaseName() {
        return "mongo";
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }

}