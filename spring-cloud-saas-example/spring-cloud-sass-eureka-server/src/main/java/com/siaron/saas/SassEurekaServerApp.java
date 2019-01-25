package com.siaron.saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xielongwang
 * @create 2019-01-039:53 PM
 * @description
 */
@SpringBootApplication
@EnableEurekaServer
public class SassEurekaServerApp {

    public static void main(String[] args) {
        SpringApplication.run(SassEurekaServerApp.class, args);
    }

}