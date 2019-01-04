package org.siaron.jpa;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xielongwang
 * @create 2018-11-189:17 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SiaronJpaExampleApp.class})
@TestPropertySource("classpath:application-test.yml")
public class BaseTest {
}