package com.siaron.drools.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xielongwang
 * @create 2019-03-235:31 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@ConfigurationProperties(DroolsProperties.PREFIX)
public class DroolsProperties {
    public static final String PREFIX = "spring.drools";

}