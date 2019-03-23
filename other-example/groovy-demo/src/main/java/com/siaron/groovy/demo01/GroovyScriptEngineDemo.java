package com.siaron.groovy.demo01;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

/**
 * @author xielongwang
 * @create 2019-03-2310:17 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class GroovyScriptEngineDemo {

    private static final String PATH = GroovyShellDemo.class.getResource("/").getPath();


    public static void main(String[] args) throws Exception {
        GroovyScriptEngine engine = new GroovyScriptEngine(PATH + "script/");

        Binding binding = new Binding();
        binding.setVariable("name", "juxinli");

        Object result1 = engine.run("demo03_1.groovy", binding);
        System.out.println(result1);
        Object result2 = engine.run("demo03_2.groovy", binding);
        System.out.println(result2);
        Object result3 = engine.run("demo03_3.groovy", binding);
        System.out.println(result3);
    }
}