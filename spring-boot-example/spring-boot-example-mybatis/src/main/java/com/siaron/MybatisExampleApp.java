package com.siaron;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xielongwang
 * @create 2019-01-219:04 AM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.siaron.mapper")
public class MybatisExampleApp {

    public static void main(String[] args) {
        SpringApplication.run(MybatisExampleApp.class, args);
    }
}