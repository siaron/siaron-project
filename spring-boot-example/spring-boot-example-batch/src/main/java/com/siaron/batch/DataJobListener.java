package com.siaron.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author xielongwang
 * @create 2019-02-1210:33 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Component
public class DataJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}