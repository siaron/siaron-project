package com.siaron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xielongwang
 * @create 2019-01-119:05 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@EnableScheduling
@SpringBootApplication
public class DisruptorApp {

    public static void main(String[] args) {
        SpringApplication.run(DisruptorApp.class, args);
    }
}