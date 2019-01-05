package com.siaron.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author xielongwang
 * @create 2019-01-0512:26 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Slf4j
@ShellComponent
public class SimpleShell {

    @ShellMethod(key = "simple", value = "simple shell example")
    public int awpSendSinge(int a, int b) {
        return a + b;
    }
}