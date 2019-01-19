package com.siaron;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

/**
 * @author xielongwang
 * @create 2019-01-192:00 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class ZkTest {
    ClassPathResource cpr;

    @BeforeClass
    public void before() {
        cpr = new ClassPathResource("/zkData");
    }

    @Test
    public void zkTest() throws NoSuchAlgorithmException {
        String m = DigestAuthenticationProvider.generateDigest("super:admin");
        System.out.println("密码:" + m);
    }

    @Test
    public void zkStart() throws IOException, InterruptedException {
        NIOServerCnxnFactory factory = new NIOServerCnxnFactory();
        factory.configure(new InetSocketAddress("127.0.0.1", 3000), 0);
        File zkFile = new File(cpr.getFile().getAbsolutePath());
        ZooKeeperServer zookeeper = new ZooKeeperServer(zkFile, zkFile, 3000);
        factory.startup(zookeeper);
    }
}