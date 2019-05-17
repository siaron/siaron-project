package com.siraon.service.impl;

import com.siraon.bean.Person;
import com.siraon.enums.LoadDataToDbEnum;
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

    private static final long ERROR_CODE = 500;

    private static final String NAMESPACE = "person_db";
    private static final String TABLE_NAME = "person";
    private static final String FULL_TABLE_NAME = NAMESPACE + ":" + TABLE_NAME;

    private static final String INFO_COLUMN_FAMILY = "base";
    private static final String OTHER_COLUMN_FAMILY = "other";

    private static final String NAME = "name";
    private static final String AGE = "age";

    private static final String ADDRESS = "address";

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

        return null;
    }

    @Override
    public Person queryPerson(String rowKey) {

        return null;
    }

    @Override
    public Person updatePerson(String rowKey) {

        return null;
    }

    @Override
    public Person delPerson(String rowKey) {

        return null;
    }


}