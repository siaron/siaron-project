package com.siraon.service.impl;

import com.siraon.service.ICRUDService;
import com.sriaon.boot.HbaseTemplate;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author xielongwang
 * @create 2019-05-1313:14
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Service
public class CRUDServiceImpl implements ICRUDService {

    @Autowired
    HbaseTemplate hbaseTemplate;

    @Override
    public String test() {
        Connection connection = hbaseTemplate.getConnection();
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}