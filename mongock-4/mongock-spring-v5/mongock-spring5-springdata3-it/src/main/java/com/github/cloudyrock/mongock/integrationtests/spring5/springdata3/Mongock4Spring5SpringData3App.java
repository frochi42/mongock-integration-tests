package com.github.cloudyrock.mongock.integrationtests.spring5.springdata3;


import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.SpringDataMongo3Driver;
import com.github.cloudyrock.mongock.integrationtests.spring5.springdata3.changelogs.client.ClientUpdater2ChangeLog;
import com.github.cloudyrock.mongock.integrationtests.spring5.springdata3.client.ClientRepository;
import com.github.cloudyrock.mongock.integrationtests.spring5.springdata3.spring.DateToZonedDateTimeConverter;
import com.github.cloudyrock.mongock.integrationtests.spring5.springdata3.spring.ZonedDateTimeToDateConverter;
import com.github.cloudyrock.spring.v5.EnableMongock;
import com.github.cloudyrock.spring.v5.MongockSpring5;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Using @EnableMongock with minimal configuration only requires changeLog package to scan
 * in property file
 */
@EnableMongock
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = ClientRepository.class)
public class Mongock4Spring5SpringData3App {

    public final static String CLIENTS_COLLECTION_NAME = "clientCollection";

    public static void main(String[] args) {
        getSpringAppBuilder().run(args);
    }


    public static SpringApplicationBuilder getSpringAppBuilder() {
        return new SpringApplicationBuilder().sources(Mongock4Spring5SpringData3App.class);
    }

    // It requires MongoDb with a replicaSet
//    @Bean
//    @ConditionalOnProperty(name = "mongock.transactionable", havingValue = "true", matchIfMissing = true)
//    MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
//        mongoTemplate.createCollection("clientCollection");
//        return new MongoTransactionManager(mongoTemplate.getMongoDbFactory());
//    }



    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(DateToZonedDateTimeConverter.INSTANCE);
        converters.add(ZonedDateTimeToDateConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }
}
