package com.siaron;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @author xielongwang
 * @create 2019-01-199:19 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class ZkClientManagerTest {

    public void  zkClientTest(){
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000, 5000, new MyZkSerializer());
        //zkClient.addAuthInfo("super", "admin".getBytes());
        String path = zkClient.create("/test5", "test", CreateMode.PERSISTENT);
        System.out.println("Create path" + path);
    }
}