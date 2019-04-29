package com.siaron.hdfs.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/**
 * @author xielongwang
 * @create 2019-04-1212:25 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class TempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    /**
     * 四个泛型类型分别代表：
     * KeyIn        Mapper的输入数据的Key，这里是每行文字的起始位置（0,11,...）
     * ValueIn      Mapper的输入数据的Value，这里是每行文字
     * KeyOut       Mapper的输出数据的Key，这里是每行文字中的“年份”
     * ValueOut     Mapper的输出数据的Value，这里是每行文字中的“气温”
     */
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // 打印样本: Before Mapper: 0, 2000010115
        System.out.print("Before Mapper: " + key + ", " + value);
        String line = value.toString();
        String year = line.substring(0, 4);
        int temperature = Integer.parseInt(line.substring(8));
        context.write(new Text(year), new IntWritable(temperature));
        // 打印样本: After Mapper:2000, 15
        System.out.println(
                "======" +
                        "After Mapper:" + new Text(year) + ", " + new IntWritable(temperature));
    }
}