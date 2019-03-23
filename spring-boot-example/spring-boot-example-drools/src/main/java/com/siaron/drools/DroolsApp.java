package com.siaron.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xielongwang
 * @create 2019-03-235:20 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication(scanBasePackages = "com.lmax.disruptor.spring.boot", scanBasePackageClasses = DroolsApp.class)
public class DroolsApp {

    public static void main(String[] args) {
        SpringApplication.run(DroolsApp.class, args);
    }

}