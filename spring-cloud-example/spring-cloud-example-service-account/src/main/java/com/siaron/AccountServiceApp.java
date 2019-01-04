package com.siaron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xielongwang
 * @create 2019-01-0310:51 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
public class AccountServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApp.class, args);
    }
}