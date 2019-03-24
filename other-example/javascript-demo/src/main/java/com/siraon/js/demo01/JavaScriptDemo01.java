package com.siraon.js.demo01;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;

/**
 * @author xielongwang
 * @create 2019-03-2412:08 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class JavaScriptDemo01 {

    private static final String PATH = JavaScriptDemo01.class.getResource("/").getPath();


    public static void main(String[] args) throws Exception {
        //demo1();
        //demo2();
        //evalFunction();
        evalFunctionJava();
    }

    private static void evalFunctionJava() throws FileNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(PATH + "js/Example3.js"));
    }

    private static void evalFunction() throws ScriptException, NoSuchMethodException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(PATH + "js/Example2.js"));
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        Object result2 = invocable.invokeFunction("fun1", new Date());
        System.out.println("result: " + result);
        System.out.println("result: " + result.getClass());
        System.out.println("result2: " + result2);
        System.out.println("result2: " + result2.getClass());
    }

    private static void demo2() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader(PATH + "js/Example.js"));
    }

    private static void demo1() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

        String name = "Runoob";
        Integer result = null;

        try {
            nashorn.eval("print('" + name + "')");
            result = (Integer) nashorn.eval("10 + 2");
        } catch (ScriptException e) {
            System.out.println("执行脚本错误: " + e.getMessage());
        }

        System.out.println(result.toString());
    }

    public static String fun2(Object name) {
        System.out.format("Hi there from Java, %s \n", name);
        return "greetings from java";
    }

}