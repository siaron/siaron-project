package com.siaron.batch;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * @author xielongwang
 * @create 2019-02-1210:14 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@SpringBootApplication
public class BatchExampleApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BatchExampleApp.class, args);
    }

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    @Override
    public void run(String... args) throws Exception {
        log.info(" lx realtime  data start ==[{}]" + System.currentTimeMillis());
        //一天的 00：00：00
        Date startDate = new DateTime().minusDays(1).millisOfDay().withMinimumValue().toDate();
        //一天的 23：59：59
        Date endDate = new DateTime().minusDays(1).millisOfDay().withMaximumValue().toDate();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("startDate", startDate);
        jobParametersBuilder.addDate("endDate", endDate);
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
        log.info("lx realtime  data  end ==[{}]" + System.currentTimeMillis());
    }
}