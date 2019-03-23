package com.siaron.example;

import com.siaron.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xielongwang
 * @create 2019-03-128:20 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class HscanDemoTest extends BaseTest {

    @Autowired
    private HscanDemo hscanDemo;

    @Test
    public void hscanDemoTest() {
        hscanDemo.hscanDemo();
    }


    @Test
    public void hscanDemoTest2() {
        hscanDemo.scan();
    }
}