package com.siaron.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.joda.time.DateTime;

import java.io.*;

/**
 * @author xielongwang
 * @create 2019-04-1110:56 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class ClientDemo02 {

    private static FileSystem FS;

    private static final String ROOT_DIR = "/user/xielongwang";

    private static final String CREATE_DIR = ROOT_DIR + "/testDir";

    private static final String CREATE_FILE = CREATE_DIR + "/testFile.log";

    private static final String CREATE_RENAME_FILE = CREATE_DIR + "/testFileRename.log";


    public static void main(String[] args) throws Exception {
        try {
            //初始化配置
            initConfig();

            //1. 创建文件夹
            createFileDir();

            //2. 创建文件
            createFile();

            //3. 查看文件信息
            lookFileInfo();

            //4. 读取文件内容
            lookFileIContent();

            //5. 重命名文件
            renameFile();

            //6. 删除文件
            //deleteFile();

            //7. 删除文件夹
            //deleteFileDir();

            //8. 文件追加的写入内容
            writeFileContentAppend();

            //9. 上传文件
            uploadFile();


            //10. 下载文件
            downloadFile();

            uploadFile2();

            downloadFile();

            //12. 移动文件
            moveFile();

            //13. 查看文件列表
            findListFile();


            //14. 带进度上传
            uploadProcessFile();
        } finally {
            if (FS != null) {
                FS.close();
            }
        }
    }

    private static void uploadProcessFile() throws Exception {
        //获取本地文件输入流
        File file = new File("/Users/xielongwang/Desktop/HBase.pdf");
        final float fileSize = file.length() / 65536;
        System.out.println("文件大小: " + fileSize);

        //获取到/test/hello.txt的输出流
        FSDataOutputStream out = FS.create(new Path("/HBase.pdf"), new Progressable() {
            long fileCount = 0;

            @Override
            public void progress() {
                fileCount++;
                System.out.println("总进度：" + (fileCount/fileSize)*100 + " %");
            }
        });

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        //拷贝
        int len = 0;
        while ((len = in.read()) != -1) {
            //此时也可以用:IOUtils.copyBytes(in,out,conf);
            out.write(len);
        }
        in.close();
        out.close();

    }


    private static void uploadFile2() throws IOException {
        FS.copyFromLocalFile(new Path("/Users/xielongwang/Desktop/nvr-connector-benchmark-cli.log"), new Path("/user/root"));
        FS.moveFromLocalFile(new Path("/Users/xielongwang/Desktop/nvr-connector-benchmark-cli.log"), new Path("/user/root/input"));
    }

    /**
     * 文件列表
     */
    private static void findListFile() throws IOException {
        FileStatus[] statuses = FS.listStatus(new Path("/user/xielongwang/testDir"));
        for (FileStatus status : statuses) {
            System.out.println("Files:  " + status);
        }
    }

    /**
     * 移动文件
     */
    private static void moveFile() throws IOException {
        FS.rename(new Path("/user/xielongwang/uploadFile/nvr-connector-benchmark-cli.log"), new Path("/user/root/nvr-connector-benchmark-cli2222.log"));
    }

    /**
     * 下载文件
     */
    private static void downloadFile() throws IOException {
        //设置下载地址和目标地址
        FS.copyToLocalFile(new Path("/user/xielongwang/uploadFile/nvr-connector-benchmark-cli.log"), new Path("/Users/xielongwang/Desktop/nvr-connector-benchmark-cli.log"));
    }

    /**
     * 上传文件
     */
    private static void uploadFile() throws Exception {
        if (FS.exists(new Path("/user/xielongwang/uploadFile/nvr-connector-benchmark-cli.log"))) {
            return;
        }


        File inFile = new File("/Users/xielongwang/Desktop/nvr-connector-benchmark-cli.log");
        //目标路径
        Path outFile = new Path("/user/xielongwang/uploadFile/nvr-connector-benchmark-cli.log");
        FileInputStream inStream = null;
        FSDataOutputStream outStream = null;
        try {
            //打开输入流
            inStream = new FileInputStream(inFile);

            //打开输出流
            outStream = FS.create(outFile);

            //copy 上去
            IOUtils.copyBytes(inStream, outStream, 4096, false);
        } finally {
            IOUtils.closeStream(inStream);
            IOUtils.closeStream(outStream);
        }
    }

    /**
     * 文件写入内容
     */
    private static void writeFileContentAppend() throws IOException {
        FS.mkdirs(new Path(CREATE_DIR));

        FSDataOutputStream fos = FS.create(new Path(CREATE_RENAME_FILE), true);
        fos.write("This is text!".getBytes());
        fos.close();

        //要追加的文件流，inpath为文件
        InputStream in = new
                BufferedInputStream(new FileInputStream("/Users/xielongwang/Desktop/a.log"));

        //重复写入
        OutputStream out = FS.append(new Path(CREATE_RENAME_FILE));
        IOUtils.copyBytes(in, out, 4096, true);

        //关闭文件刘
        in.close();
        out.close();
    }

    /**
     * 删除文件
     */
    private static void deleteFile() throws IOException {
        //删除文件
        FS.deleteOnExit(new Path(CREATE_RENAME_FILE));
        System.out.println("是否删除文件成功:" + FS.exists(new Path(CREATE_RENAME_FILE)));
        System.out.println();

    }

    /**
     * 删除文件夹
     */
    private static void deleteFileDir() throws IOException {
        //删除文件夹
        FS.deleteOnExit(new Path(CREATE_DIR));
        System.out.println("是否删除文件件成功:" + FS.exists(new Path(CREATE_DIR)));
        System.out.println();
    }

    /**
     * 重命名文件
     */
    private static void renameFile() throws IOException {
        FS.rename(new Path(CREATE_FILE), new Path(CREATE_RENAME_FILE));
        System.out.println("是否重命名成功:" + FS.exists(new Path(CREATE_RENAME_FILE)));
        System.out.println();
    }

    /**
     * 读取文件内容
     */
    private static void lookFileIContent() throws IOException {
        FSDataInputStream fsdis = FS.open(new Path(CREATE_FILE));
        BufferedReader br = new BufferedReader(new InputStreamReader(fsdis));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("文件内容为: " + line);
        }
        System.out.println();

        br.close();
        fsdis.close();
    }

    /**
     * 查看文件信息
     */
    private static void lookFileInfo() throws IOException {
        FileStatus fileStatus = FS.getFileStatus(new Path(CREATE_FILE));

        System.out.println("创建时间:" + new DateTime(fileStatus.getAccessTime()));
        System.out.println("块大小:" + fileStatus.getBlockSize() / 1024 / 1024);
        System.out.println("副本数:" + fileStatus.getReplication());
        System.out.println("是否是一个文件" + fileStatus.isFile());
        System.out.println();
    }

    /**
     * 创建一个文件
     */
    private static void createFile() throws IOException {
        if (FS.exists(new Path(CREATE_FILE))) {
            System.out.println("文件已存在!");
        } else {
            System.out.println("文件不存在! 创建文件");
            FSDataOutputStream fos = FS.create(new Path(CREATE_FILE), true);
            fos.write("写入文件内容11111111111111111111111111111111111111111111111111111111111111111111111!".getBytes());
            fos.close();
        }

        //文件是否创建成功
        System.out.println("是否为一个文件: " + (FS.getFileStatus(new Path(CREATE_FILE)).isFile() ? "是" : "否"));
        System.out.println();
    }

    /**
     * 创建文件夹
     */
    private static void createFileDir() throws Exception {
        //文件夹是否存在
        if (FS.exists(new Path(CREATE_DIR))) {
            System.out.println("文件已存在!");
        } else {
            System.out.println("文件不存在! 创建文件夹");
            boolean isCreate = FS.mkdirs(new Path(CREATE_DIR));
            System.out.println("创建文件" + (isCreate ? "成功" : "失败"));
        }

        //是否为目录
        System.out.println("文件是否为一个目录: " + (FS.getFileStatus(new Path(CREATE_DIR)).isDirectory() ? "是" : "否"));
        System.out.println();
    }

    /**
     * 初始化废纸
     *
     * @throws IOException IOException
     */
    private static void initConfig() throws IOException {
        //模拟用户 root 用户
        System.setProperty("HADOOP_USER_NAME", "root");

        //hdfs 连接配置
        Configuration config = new Configuration();
        config.set("fs.defaultFS", "hdfs://node01:9000");
        config.set("dfs.client.use.datanode.hostname", "true");
        config.setBoolean("dfs.support.append", true);
        config.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        config.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");

        //获取分布式文件系统对象
        FS = FileSystem.get(config);
    }
}