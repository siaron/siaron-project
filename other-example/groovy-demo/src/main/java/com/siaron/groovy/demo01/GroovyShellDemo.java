package com.siaron.groovy.demo01;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xielongwang
 * @create 2019-03-239:48 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class GroovyShellDemo {

    private static final String PATH = GroovyShellDemo.class.getResource("/").getPath();

    public static void main(String[] args) throws Exception {
        //demo1();
        demo2();
    }

    private static void demo2() throws IOException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param1", "param-test1");
        Binding binding = new Binding(param);
        binding.setProperty("param2", "param-test2");
        GroovyShell groovyShell = new GroovyShell(binding);
        Object result = groovyShell.evaluate(new File(PATH + "script/demo01.groovy"));
        System.out.println("result: " + result);
    }

    public static void demo1() {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate("println 'My First Groovy shell.'");
    }
}