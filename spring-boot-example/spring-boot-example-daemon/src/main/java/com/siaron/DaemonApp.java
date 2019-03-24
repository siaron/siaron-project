package com.siaron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

/**
 * @author xielongwang
 * @create 2019-01-0512:14 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@SpringBootApplication
public class DaemonApp {


    public static void main(String[] args) throws InterruptedException {
        SpringApplication app = new SpringApplication(DaemonApp.class);
        ConfigurableApplicationContext context = app.run(args);
        //注册清理资源的钩子,当jvm退出时,执行的操作
        context.registerShutdownHook();
        //允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行
        final CountDownLatch closeLatch = context.getBean(CountDownLatch.class);
        //当jvm关闭时.将count值减1
        Runtime.getRuntime().addShutdownHook(new Thread(closeLatch::countDown));
        //等待
        closeLatch.await();
    }

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }
}