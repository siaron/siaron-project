package com.siraon.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.siraon.mongo.demo.Customer;
import com.siraon.mongo.demo.CustomerRepository;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author xielongwang
 * @create 2019-03-319:36 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication
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

        //直接插入json
        Document doc = Document.parse(new ObjectMapper().writeValueAsString(new Customer("Alic111e", "Sm111ith")));
        mongoTemplate.insert(doc, "customer");

        //根据主键
        Query query = Query.query(Criteria.where("_id").is(customer1.getId()));
        Update update = new Update();
        update.set("fn", "sssssss");
        UpdateResult result = mongoTemplate.updateMulti(query, update, "customer");
    }

}