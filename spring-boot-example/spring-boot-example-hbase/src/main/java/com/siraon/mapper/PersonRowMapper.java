package com.siraon.mapper;

import com.siraon.bean.Person;
import com.sriaon.boot.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import static com.siraon.service.impl.CRUDServiceImpl.*;

/**
 * @author xielongwang
 * @create 2019-05-1710:39
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(Result result, int rowNum) throws Exception {
        Person.BaseInfo baseInfo = new Person.BaseInfo(new String(result.getValue(INFO_COLUMN_FAMILY.getBytes(), NAME.getBytes())),
                Bytes.toLong(result.getValue(INFO_COLUMN_FAMILY.getBytes(), AGE.getBytes())));

        Person.OtherInfo otherInfo = new Person.OtherInfo(new String(result.getValue(OTHER_COLUMN_FAMILY.getBytes(), ADDRESS.getBytes())));

        return Person
                .builder()
                .base(baseInfo)
                .other(otherInfo)
                .build();
    }
}