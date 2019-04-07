package com.siraon.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import com.siraon.mongo.demo.Customer;
import com.siraon.mongo.demo.CustomerRepository;
import com.siraon.mongo.demo.PropertiesConstants;
import com.siraon.mongo.demo.UploadProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.LongStream;

/**
 * @author xielongwang
 * @create 2019-03-319:36 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {UploadProperties.class})
public class MongoApp implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        // save a couple of customers
        Customer customer1 = repository.save(new Customer("Alice", "Smith"));
        Customer customer2 = repository.save(new Customer("Bob", "Smith"));
        Customer customer3 = repository.save(new Customer("Bobffffffffffff"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }

        //根据主键
        Query query = Query.query(Criteria.where("_id").is(customer1.getId()));
        Update update = new Update();
        update.set("fn", "sssssss");
        mongoTemplate.updateMulti(query, update, "customer");


        //直接插入json
        Document doc = Document.parse(new ObjectMapper().writeValueAsString(new Customer("Alic111e", "Sm111ith")));
        mongoTemplate.insert(doc, "customer");

        Customer customerssss = new Customer("Bobccccccccccccc");
        mongoTemplate.insert(customerssss, "customer");

        //upsert --> update
        mongoTemplate.upsert(new Query(Criteria.where("firstName").is("Alic111e")),
                Update.update("firstName", "testUpdateQuery"),
                "customer");

        //upsert --> insert
        mongoTemplate.upsert(new Query(Criteria.where("firstName").is("Alic111e")),
                Update.update("firstName", "testInsertQuery"),
                "customer");


        //update from entity
        Update update1 = new Update();
        update1.set("ccca", "bbbb");
        mongoTemplate.upsert(new Query(Criteria.where("firstName").is("Alic111e")),
                update1,
                "customer");


        //update from entity
        Update update2 = new Update();
        update2.set("ccccc", "bbbddd");
        mongoTemplate.updateFirst(new Query(Criteria.where("firstName").is("Alic111e")),
                update2,
                "customer");


        //批量操作
        List<TestDto> testDtoList = new ArrayList<>();
        testDtoList.add(new TestDto("11", 11L, 1.1d));
        testDtoList.add(new TestDto("22", 22L, 2.2d));
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "customer");
        bulkOperations.insert(testDtoList);
        BulkWriteResult writeResult = bulkOperations.execute();
        System.out.println(writeResult.getInsertedCount());


        System.out.println("PropertiesConstants : " + PropertiesConstants.ONLINE_RESOURCE_PATH);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TestDto {
    private String name;
    private Long age;
    private Double ss;
}