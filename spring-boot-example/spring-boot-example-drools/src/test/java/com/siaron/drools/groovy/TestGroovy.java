package com.siaron.drools.groovy;

import groovy.lang.GroovyShell;
import org.junit.Test;

/**
 * @author xielongwang
 * @create 2019-03-239:42 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class TestGroovy {

    @Test
    public void testGroovy() {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate("println 'My First Groovy shell.'");
    }
}