package com.siaron;

import lombok.extern.slf4j.Slf4j;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import static org.springframework.shell.jline.InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED;

/**
 * @author xielongwang
 * @create 2019-01-0512:14 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@SpringBootApplication
public class ExecutableApp {

    public static void main(String[] args) {
        SpringApplication.run(ExecutableApp.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("nvr-cloud-benchmark-shell:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }

    @Component
    @ConditionalOnProperty(name = SPRING_SHELL_INTERACTIVE_ENABLED, havingValue = "true")
    class BenchmarkApplicationStartup extends ContextStartedEvent {
        public BenchmarkApplicationStartup(ApplicationContext source) {
            super(source);
            System.out.println("Benchmark Cli Starting ....");
        }
    }
}