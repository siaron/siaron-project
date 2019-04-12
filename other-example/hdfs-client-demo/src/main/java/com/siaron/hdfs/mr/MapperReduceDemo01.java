package com.siaron.hdfs.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author xielongwang
 * @create 2019-04-1111:22 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class MapperReduceDemo01 {

    private static final String PATH = MapperReduceDemo01.class.getResource("/").getPath();

    private static final String HDFS_ADD = "hdfs://node01:9000";

    private static final String INPUT = "/user/xielongwang/input";

    private static final String OUTPUT = "/user/xielongwang/output";

    public static void main(String[] args) throws Exception {
        //https://my.oschina.net/itblog/blog/275294

        //模拟用户 root 用户
        System.setProperty("HADOOP_USER_NAME", "root");

        //hdfs 连接配置
        Configuration config = new Configuration();
        config.set("fs.defaultFS", HDFS_ADD);
        config.set("dfs.client.use.datanode.hostname", "true");
        config.setBoolean("dfs.support.append", true);
        config.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        config.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");

        Job job = Job.getInstance(config, "dmeo");

        //设置主类
        job.setJarByClass(MapperReduceDemo01.class);

        //设置mapper 类, 和Reduce 类
        job.setMapperClass(TempMapper.class);
        job.setReducerClass(TempReducer.class);


        //设置最后输出结果的Key和Value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileSystem fs = FileSystem.get(config);
        //输入文件路径
        Path inputPath = new Path(INPUT);
        if (fs.exists(inputPath)) {
            //上传一个文件
            fs.copyFromLocalFile(new Path(PATH + "/inputExample.txt"), inputPath);

            //添加
            FileInputFormat.addInputPath(job, inputPath);
        }

        //输出路径
        Path outPath = new Path(OUTPUT);
        fs.delete(outPath, true);

        FileOutputFormat.setOutputPath(job, outPath);

        //执行job，直到完成
        job.waitForCompletion(true);
        System.out.println("Finished");

        //下载输出文件
        fs.copyToLocalFile(new Path(OUTPUT + "/part-r-00000"), new Path(PATH + "/output.txt"));
    }

}