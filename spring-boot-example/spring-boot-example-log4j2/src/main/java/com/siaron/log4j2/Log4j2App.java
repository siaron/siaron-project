package com.siaron.log4j2;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xielongwang
 * @create 2019-02-129:19 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Log4j2
@SpringBootApplication
public class Log4j2App {

    public static void main(String[] args) {
        SpringApplication.run(Log4j2App.class, args);
        log.info("test info");
        log.warn("test warn");
        log.error("test error");
    }

}