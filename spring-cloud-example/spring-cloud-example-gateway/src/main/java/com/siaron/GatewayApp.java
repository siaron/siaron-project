package com.siaron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author xielongwang
 * @create 2019-01-0310:21 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}