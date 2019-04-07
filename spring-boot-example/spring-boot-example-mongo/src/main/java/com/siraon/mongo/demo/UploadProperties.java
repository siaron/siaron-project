package com.siraon.mongo.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xielongwang
 * @create 2019-04-078:56 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@ConfigurationProperties("dayuan.upload")
public class UploadProperties {
    private String onlineResourcePath;

    public String getOnlineResourcePath() {

        return onlineResourcePath;
    }

    public void setOnlineResourcePath(String onlineResourcePath) {
        this.onlineResourcePath = onlineResourcePath;
    }
}