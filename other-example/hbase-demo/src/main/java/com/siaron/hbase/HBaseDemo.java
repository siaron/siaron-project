package com.siaron.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xielongwang
 * @create 2019-04-1216:59
 * @email xielong.wang@nvr-china.com
 * @description https://blog.csdn.net/kongxx/article/details/79245829
 */
public class HBaseDemo {

    //表名
    private static final String TABLE_NAME = "user";

    //列族 1
    private static final String COLUMN_FAMILY_BASE = "base";
    //列族 2
    private static final String COLUMN_FAMILY_ADDRESS = "address";

    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_HOME = "home";
    private static final String COLUMN_OFFICE = "office";

    private Connection connection;

    public HBaseDemo(Connection connection) {
        this.connection = connection;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(conf);

        new HBaseDemo(connection).hBaseClientDemo();

        connection.close();
    }


    public void hBaseClientDemo() throws IOException {
        //创建表
        createTable();

        //准备数据
        prepareData();

        //多个 filter And 查询
        filterMustPassAll();

        //多个 filter or 查询
        filterMustPassOne();

        //删除表
        deleteTable();
    }

    private void deleteTable() throws IOException {
        Admin admin = connection.getAdmin();
        try {
            admin.disableTable(TableName.valueOf(TABLE_NAME));
            admin.deleteTable(TableName.valueOf(TABLE_NAME));
        } finally {
            admin.close();
        }
    }

    private void filterMustPassOne() throws IOException {
        System.out.println("---------- must pass one ----------");
        Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("base"), Bytes.toBytes("username"),
                CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("user_0")));
        Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("base"), Bytes.toBytes("username"),
                CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("user_10")));
        Filter filter3 = new SingleColumnValueFilter(Bytes.toBytes("base"), Bytes.toBytes("username"),
                CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("user_99")));

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, filter1, filter2, filter3);

        scanTable(filterList);
    }

    private void scanTable(FilterList filterList) throws IOException {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
        Scan scan = new Scan();
        scan.setFilter(filterList);

        ResultScanner resultScanner = table.getScanner(scan);
        Iterator<Result> it = resultScanner.iterator();
        while (it.hasNext()) {
            Result result = it.next();
            printRow(result);
        }
        resultScanner.close();
        table.close();
    }

    private void filterMustPassAll() throws IOException {
        System.out.println("---------- must pass all ----------");

        Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("base"), Bytes.toBytes("username"),
                CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("user_0")));

        Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("base"), Bytes.toBytes("password"),
                CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("password_0")));


        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filter1, filter2);

        scanTable(filterList);
    }

    private void printRow(Result result) {
        if (Bytes.toString(result.getRow()) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Bytes.toString(result.getRow()));
            sb.append("[");
            sb.append("base:username=").append(Bytes.toString(result.getValue(Bytes.toBytes("base"), Bytes.toBytes("username"))));
            sb.append(", base:password=").append(Bytes.toString(result.getValue(Bytes.toBytes("base"), Bytes.toBytes("password"))));
            sb.append(", address:home=").append(Bytes.toString(result.getValue(Bytes.toBytes("address"), Bytes.toBytes("home"))));
            sb.append(", address:office=").append(Bytes.toString(result.getValue(Bytes.toBytes("address"), Bytes.toBytes("office"))));
            sb.append("]");
            System.out.println(sb.toString());
        }
    }


    private void prepareData() throws IOException {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        List<Row> actions = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Put put = new Put(Bytes.toBytes("row_" + i));
            //列族 1
            put.addColumn(Bytes.toBytes(COLUMN_FAMILY_BASE), Bytes.toBytes(COLUMN_USERNAME), Bytes.toBytes("user_" + i));
            put.addColumn(Bytes.toBytes(COLUMN_FAMILY_BASE), Bytes.toBytes(COLUMN_PASSWORD), Bytes.toBytes("password_" + i));

            //列族 2
            put.addColumn(Bytes.toBytes(COLUMN_FAMILY_ADDRESS), Bytes.toBytes(COLUMN_HOME), Bytes.toBytes("home_" + i));
            put.addColumn(Bytes.toBytes(COLUMN_FAMILY_ADDRESS), Bytes.toBytes(COLUMN_OFFICE), Bytes.toBytes("office_" + i));

            actions.add(put);
        }
        Object[] results = new Object[actions.size()];

        try {
            table.batch(actions, results);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        table.close();
    }

    /**
     * 创建表
     */
    private void createTable() throws IOException {
        Admin admin = connection.getAdmin();
        try {
            TableDescriptor tds = TableDescriptorBuilder.newBuilder(TableName.valueOf(TABLE_NAME))
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(COLUMN_FAMILY_BASE.getBytes()).build())
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(COLUMN_FAMILY_ADDRESS.getBytes()).build())
                    .build();
            admin.createTable(tds);
        } finally {
            admin.close();
        }
    }
}