package com.saas.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author xielongwang
 * @create 2018-11-079:06 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication
public class CloudJobApplication {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudJobApplication.class, args);
    }

}