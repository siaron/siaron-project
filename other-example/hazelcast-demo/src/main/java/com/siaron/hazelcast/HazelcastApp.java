package com.siaron.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static com.hazelcast.nio.IOUtil.closeResource;

/**
 * @author xielongwang
 * @create 2019-03-2410:50 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class HazelcastApp {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        String printPort = System.getProperty("print.port");
        if (printPort != null) {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter("ports" + File.separator + printPort, "UTF-8");
                printWriter.println(hz.getCluster().getLocalMember().getAddress().getPort());
            } finally {
                closeResource(printWriter);
            }
        }
    }
}