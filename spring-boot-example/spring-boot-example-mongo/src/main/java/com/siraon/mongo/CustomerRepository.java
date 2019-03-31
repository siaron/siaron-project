package com.siraon.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author xielongwang
 * @create 2019-03-319:37 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);

}