package com.siaron.drools.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xielongwang
 * @create 2019-04-2921:10
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@Configuration
public class JavaDefineDrools {


    // 获取 drools 实现的 KieServices 实例
    @Bean
    public KieServices javaKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem javaFileSystem(KieServices kieServices) {
        // 创建一个 KieFileSystem
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // 创建一个 KieResources 对象
        KieResources resources = kieServices.getResources();
        // 1. 先创建 KieModuleModel, 类似于xml中的 kmodule 节点
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        // 2. 再创建 KieBaseModel, 类似于xml中的 kbase节点, name=kbase-rules, package=rules
        KieBaseModel baseModel = kieModuleModel
                .newKieBaseModel("java-define-rules")
                .addPackage("com.rules3");

        // 3. 再创建 KieSessionModel, 类似于xml中的 ksession 节点, name=ksession-rules
        baseModel.newKieSessionModel("ksession-rules");

        // 4. 生产一个xml文件，就是kmodule.xml文件
        String xml = kieModuleModel.toXML();
        // 打印出来看看内容
        log.info("xml:{} ", xml);
        // 5. 将这个xml文件写入到KieFileSystem中
        kieFileSystem.writeKModuleXML(xml);
        // 6. 然后将规则文件等写入到 KieFileSystem 中
        // fileSystem.write("src/main/resources/rules/test.drl", resources.newClassPathResource("rules/test.drl"));
        kieFileSystem.write(resources.newClassPathResource("com/rules3/JavaDefine.drl")); // 跟上面等效
        // 7. 最后通过 KieBuilder 进行构建就将该 kmodule 加入到 KieRepository 中, 这样就将自定义的kmodule加入到引擎中了
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();  // 编译

        return kieFileSystem;
    }

    @Bean
    public KieContainer javaKieContainer(KieServices kieServices) {
        // 下面就可以向原来一样使用了
        // 得到 KieContainer
        return kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }

    @Bean
    KieSession javaKieSession(KieContainer kieContainer) {
        // 通过 kContainer 获取 kmodule.xml 中定义的 ksession
        return kieContainer.newKieSession("ksession-rules");
    }
}