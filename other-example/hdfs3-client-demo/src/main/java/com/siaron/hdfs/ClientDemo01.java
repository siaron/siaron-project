package com.siaron.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.InputStream;

/**
 * @author xielongwang
 * @create 2019-04-114:16 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class ClientDemo01 {
    public static void main(String[] args) throws Exception {
        //模拟root用户
        System.setProperty("HADOOP_USER_NAME", "root");

        //配置项
        Configuration config = new Configuration();
        config.set("fs.defaultFS", "hdfs://node01:9000");
        config.set("dfs.client.use.datanode.hostname", "true");
        FileSystem fs = FileSystem.get(config);

        // 列出hdfs上/user/root/目录下的所有文件和目录
        FileStatus[] statuses = fs.listStatus(new Path("/user/root"));
        for (FileStatus status : statuses) {
            System.out.println("File Desc  " + status);
        }


        // 在hdfs的 /user/root 目录下创建一个文件，并写入一行文本
        FSDataOutputStream os = fs.create(new Path("/user/root/test1.log"), true);
        os.write("Hello World!".getBytes());
        os.flush();
        os.close();

        // 显示在hdfs的/ user/root 下指定文件的内容
        InputStream is = fs.open(new Path("/user/root/test1.log"));
        byte[] buff = new byte[is.available()];
        String string = null;
        int len;
        while ((len = is.read(buff)) != -1) {
            string = new String(buff, 0, len);
        }
        System.out.println(string);
    }
}