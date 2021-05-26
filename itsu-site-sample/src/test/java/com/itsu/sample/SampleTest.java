package com.itsu.sample;

import cn.hutool.extra.spring.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jerry Su
 * @Date 2021/5/26 22:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Test
    public void test1() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
