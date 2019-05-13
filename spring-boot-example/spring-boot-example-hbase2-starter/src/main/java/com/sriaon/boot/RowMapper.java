package com.sriaon.boot;

import org.apache.hadoop.hbase.client.Result;

/**
 * @author xielongwang
 * @create 2019-05-1313:47
 * @email xielong.wang@nvr-china.com
 * @description
 */
@FunctionalInterface
public interface RowMapper<T> {

    T mapRow(Result result, int rowNum) throws Exception;
}