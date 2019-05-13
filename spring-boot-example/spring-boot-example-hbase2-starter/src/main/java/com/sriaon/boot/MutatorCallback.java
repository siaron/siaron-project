package com.sriaon.boot;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * @author xielongwang
 * @create 2019-05-1313:47
 * @email xielong.wang@nvr-china.com
 * @description
 */
public interface MutatorCallback {

    /**
     * 使用mutator api to update put and delete
     * @param mutator
     * @throws Throwable
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}
