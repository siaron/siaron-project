package com.siaron.batch;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xielongwang
 * @create 2017-12-14 下午2:06
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataJobListener dataJobListener;

    @Autowired
    private Environment environment;

    /**
     * 作业仓库
     */
    @Bean
    public JobRepository jobRepository(HikariDataSource dataSource, DataSourceTransactionManager dataSourceTransactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(dataSourceTransactionManager);
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.POSTGRES.name());
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * JobBuilderFactory
     *
     * @param jobRepository JobRepository
     * @return JobBuilderFactory
     */
    @Bean
    JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
        return new JobBuilderFactory(jobRepository);
    }

    /**
     * StepBuilderFactory
     *
     * @param jobRepository                jobRepository
     * @param dataSourceTransactionManager dataSourceTransactionManager
     * @return stepBuilderFactory
     */
    @Bean
    StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, DataSourceTransactionManager dataSourceTransactionManager) {
        return new StepBuilderFactory(jobRepository, dataSourceTransactionManager);
    }

    /**
     * 作业调度器
     */
    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }

    /**
     * 作业注册器
     */
    @Bean
    public MapJobRegistry mapJobRegistry() {
        return new MapJobRegistry();
    }


    /*** JobRegistryBeanPostProcessor
     *
     * @param mapJobRegistry MapJobRegistry
     * @return JobRegistryBeanPostProcessor
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(MapJobRegistry mapJobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(mapJobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    /**
     * 作业线程池
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(environment.getRequiredProperty("spring.batch.task.thread", Integer.class));
        threadPoolTaskExecutor.setKeepAliveSeconds(30000);
        threadPoolTaskExecutor.setMaxPoolSize(150);
        threadPoolTaskExecutor.setQueueCapacity(1024);
        return threadPoolTaskExecutor;
    }

    @Bean
    public Job job(Flow flow) {
        return jobBuilderFactory.get("customerJob")
                .listener(dataJobListener)
                .incrementer(new RunIdIncrementer())
                .start(flow)
                .end()
                .build();
    }

    /**
     * 导入流程
     *
     * @param step1 步骤1
     * @return flow
     */
    @Bean
    public Flow flow(Step step1) {
        return new FlowBuilder<Flow>("flow").start(step1).build();
    }

    /**
     * 步骤2的具体事宜
     *
     * @param itemReader     jdbcPagingItemReader
     * @param step1Processor step1Processor
     * @param myFileWriter   myFileWriter
     * @return Step
     */
    @Bean
    protected Step step1(MyBatisPagingItemReader itemReader, Step1Processor step1Processor, MyFileWriter myFileWriter, ThreadPoolTaskExecutor taskExecutor) {
        StepBuilder stepBuilder = stepBuilderFactory.get("step1");
        SimpleStepBuilder simpleStepBuilder = stepBuilder.chunk(1000);
        simpleStepBuilder.allowStartIfComplete(false);
        simpleStepBuilder.throttleLimit(10);
        simpleStepBuilder.reader(itemReader);
        simpleStepBuilder.processor(step1Processor);
        simpleStepBuilder.writer(myFileWriter);
        simpleStepBuilder.taskExecutor(taskExecutor);
        return simpleStepBuilder.build();
    }

    @Bean
    @StepScope
    public MyBatisPagingItemReader itemReader(SqlSessionFactory backupSqlSessionFactory, @Value(value = "#{jobParameters[startDate]}") Date startDate, @Value(value = "#{jobParameters[endDate]}") Date endDate) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setQueryId("org.gpscloud.batch.mapper.CustomerMapper.selectDeviceDataPage");
        myBatisPagingItemReader.setSqlSessionFactory(backupSqlSessionFactory);
        myBatisPagingItemReader.setPageSize(1000);
        Map<String, Date> parameterValues = new HashMap<String, Date>(20);
        parameterValues.put("startDate", startDate);
        parameterValues.put("endDate", endDate);
        myBatisPagingItemReader.setParameterValues(parameterValues);
        return myBatisPagingItemReader;
    }

    @Bean
    Step1Processor step2Processor() {
        return new Step1Processor();
    }

    @Bean
    MyFileWriter myFileWriter() {
        return new MyFileWriter();
    }

}
