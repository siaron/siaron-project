package com.siraon.mongo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-04-078:55 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class PropertiesConstants {

    /**
     * 在线资源地址
     * online-resource-path
     */
    public static final String ONLINE_RESOURCE_PATH = PropertiesConstantsHelper.onlineResourcePath;


}

@Component
class PropertiesConstantsHelper {

    static String onlineResourcePath;

    @Autowired
    public void setUploadProperties(UploadProperties uploadProperties) {
        onlineResourcePath = uploadProperties.getOnlineResourcePath();
    }
}