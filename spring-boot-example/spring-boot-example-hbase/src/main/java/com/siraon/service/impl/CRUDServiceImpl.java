package com.siraon.service.impl;

import com.siraon.bean.Person;
import com.siraon.enums.LoadDataToDbEnum;
import com.siraon.mapper.PersonRowMapper;
import com.siraon.service.ICRUDService;
import com.siraon.support.GlobalException;
import com.sriaon.boot.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author xielongwang
 * @create 2019-05-1313:14
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Service
public class CRUDServiceImpl implements ICRUDService {

    public static final long ERROR_CODE = 500;

    public static final String NAMESPACE = "person_db";
    public static final String TABLE_NAME = "person";
    public static final String FULL_TABLE_NAME = NAMESPACE + ":" + TABLE_NAME;

    public static final String INFO_COLUMN_FAMILY = "base";
    public static final String OTHER_COLUMN_FAMILY = "other";

    public static final String NAME = "name";
    public static final String AGE = "age";

    public static final String ADDRESS = "address";

    @Autowired
    HbaseTemplate hbaseTemplate;

    @Override
    public String testConnect() {
        String HBaseVersion = null;
        try {
            Connection connection = hbaseTemplate.getConnection();
            byte[] versionedBytes = connection.getAdmin().getMaster().getVersionedBytes();
            HBaseVersion = new String(versionedBytes);
            log.info("HBase Version {}", HBaseVersion);
        } catch (Exception e) {
            log.error("HBase Connection Error", e);
            throw new GlobalException(ERROR_CODE, e.getMessage());
        }
        return HBaseVersion;
    }


    @Override
    public String createPersonTable() {
        try {
            Connection connection = hbaseTemplate.getConnection();
            Admin admin = connection.getAdmin();

            //创建NameSpace
            admin.createNamespace(NamespaceDescriptor.create(NAMESPACE).build());

            //创建表
            TableDescriptor tableDescriptor = TableDescriptorBuilder
                    .newBuilder(TableName.valueOf(FULL_TABLE_NAME))
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.of(INFO_COLUMN_FAMILY))
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.of(OTHER_COLUMN_FAMILY))
                    .build();

            admin.createTable(tableDescriptor);
        } catch (Exception e) {
            log.error("HBase Connection Error", e);
            throw new GlobalException(ERROR_CODE, e.getMessage());
        }
        return FULL_TABLE_NAME;
    }

    @Override
    public String loadDataToHBase(LoadDataToDbEnum loadDataToDbEnum) {
        try {
            Table table = hbaseTemplate.getConnection().getTable(TableName.valueOf(FULL_TABLE_NAME));
            DecimalFormat decimalFormat = new DecimalFormat("0000000000");
            List<Put> putList = new ArrayList<>();

            LongStream.rangeClosed(1, 10000).forEach(index -> {
                Put put = new Put(decimalFormat.format(index).getBytes());

                put.addColumn(INFO_COLUMN_FAMILY.getBytes(), NAME.getBytes(), ("yoyo" + index).getBytes());
                put.addColumn(INFO_COLUMN_FAMILY.getBytes(), AGE.getBytes(), Bytes.toBytes(100 + index));

                put.addColumn(OTHER_COLUMN_FAMILY.getBytes(), ADDRESS.getBytes(), ("济南高新区" + (index + 100) + "号").getBytes());

                putList.add(put);
                if (putList.size() % 2000 == 0) {
                    try {
                        table.put(putList);
                    } catch (IOException e) {
                        log.error("HBase Batch Insert Data Error", e);
                        throw new GlobalException(ERROR_CODE, e.getMessage());
                    }
                    putList.clear();
                }
            });

            table.put(putList);

            table.close();

            putList.clear();

            return "ok";
        } catch (Exception e) {
            log.error("HBase Connection Error", e);
            throw new GlobalException(ERROR_CODE, e.getMessage());
        }
    }

    @Override
    public List<Person> queryPersonList(String startRowKey, String stopRowKey) {
        return hbaseTemplate.find(FULL_TABLE_NAME,
                new Scan()
                        .withStartRow(startRowKey.getBytes())
                        .withStopRow(stopRowKey.getBytes()),
                new PersonRowMapper());
    }

    @Override
    public Person queryPerson(String rowKey) {
        return hbaseTemplate.get(FULL_TABLE_NAME, rowKey, new PersonRowMapper());
    }

    /**
     * @param rowKey rowkey
     * @return {@link Person}
     */
    @Override
    public Person updatePerson(String rowKey) {
        Put put = new Put(rowKey.getBytes());
        put.addColumn(INFO_COLUMN_FAMILY.getBytes(),AGE.getBytes(), Bytes.toBytes(11));
        hbaseTemplate.saveOrUpdate(FULL_TABLE_NAME, put);
        return queryPerson(rowKey);
    }

    @Override
    public void delPerson(String rowKey) {
        Mutation delete = new Delete(rowKey.getBytes());
        hbaseTemplate.saveOrUpdate(FULL_TABLE_NAME, delete);
    }


    @Override
    public void append(String rowKey) {
        Append append = new Append(rowKey.getBytes());
        append.addColumn(INFO_COLUMN_FAMILY.getBytes(), AGE.getBytes(), Bytes.toBytes(12));
        hbaseTemplate.saveOrUpdate(FULL_TABLE_NAME, append);
    }

    @Override
    public void increment(String rowKey) {
        Increment increment = new Increment(rowKey.getBytes());
        increment.addColumn(INFO_COLUMN_FAMILY.getBytes(), AGE.getBytes(), 13);
        hbaseTemplate.saveOrUpdate(FULL_TABLE_NAME, increment);
    }


    /*
    Mutation
        Put 插入
        Delete 删除
        Append 单行上执行追加操作.
        Increment 单行上执行增量操作
     */

}