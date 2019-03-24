package com.siraon.js.demo02;

import com.siraon.js.demo01.JavaScriptDemo01;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author xielongwang
 * @create 2019-03-2412:35 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class ScriptObjectMirrorDemo {

    private static final String PATH = JavaScriptDemo01.class.getResource("/").getPath();
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");


    public static void main(String[] args) throws Exception {
        //demo01();
        //demo02();
        //demo03();
        //demo04();
        //demo05();
        demo06();
    }

    private static void demo06() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "expand/Underscore.js"));
    }

    private static void demo05() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "expand/Runnable.js"));
    }

    private static void demo04() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "expand/Lambda.js"));
    }

    private static void demo03() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "expand/Foreach.js"));
    }

    private static void demo02() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "expand/Expand.js"));
    }

    private static void demo01() throws FileNotFoundException, ScriptException {
        engine.eval(new FileReader(PATH + "mirror/MirrorExample.js"));
    }


    public static void fun4(ScriptObjectMirror person) {
        System.out.println("Full Name is: " + person.callMember("getFullName"));
    }
}