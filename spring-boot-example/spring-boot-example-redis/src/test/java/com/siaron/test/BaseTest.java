package com.siaron.test;

import com.siaron.SpringBootRedisApp;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author xielongwang
 * @create 2018-11-2810:21 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootRedisApp.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application.yml"})
public class BaseTest {

}