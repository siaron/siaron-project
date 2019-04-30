package com.siaron.drools.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author xielongwang
 * @create 2019-04-2918:29
 * @email xielong.wang@nvr-china.com
 * @description 方式二, 通过kmodule.xml记载
 * <p>
 * 一个module下面可以配多个kbase，一个kbase可以配多个drl文件（如下边的配置方式），一个drl文件里可以配多个规则,使用 kmodule.xml
 */
@Configuration
public class DroolsConfig2 {

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieContainer classpathKieContainer() throws IOException {
        return getKieServices().newKieClasspathContainer();
    }

    @Bean
    public KieBase kieBase2() throws IOException {
        return classpathKieContainer().getKieBase("rules");
    }

    @Bean
    public KModuleBeanFactoryPostProcessor kiePostProcessor2() {
        return new KModuleBeanFactoryPostProcessor();
    }

    @Bean
    public KieSession demoRule() throws IOException {
        return classpathKieContainer().newKieSession("demo_rule");
    }

    @Bean
    public KieSession demo2Rule() throws IOException {
        return classpathKieContainer().newKieSession("demo_rule2");
    }
}