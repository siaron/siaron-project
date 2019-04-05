package com.siraon.mongo.demo;

import org.springframework.data.annotation.Id;

/**
 * @author xielongwang
 * @create 2019-03-319:36 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class Customer {
    @Id
    public String id;
    public String firstName;
    public String lastName;

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}