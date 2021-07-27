package com.itsu.sample;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.util.JWTUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Jerry Su
 * @Date 2021/5/26 22:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test1() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        redisTemplate.opsForValue().set("abc", list);

        List<String> list2 = (List<String>) redisTemplate.opsForValue().get("abc");
        list2.forEach(System.out::println);
    }

    @Test
    public void test3() {
        Set<String> set = new HashSet<>();
        set.add(JWTUtil.sign("abc","password"));
        set.add(JWTUtil.sign("abc","password"));
        set.add(JWTUtil.sign("abc","password"));
        redisTemplate.opsForValue().set("abc", set, 5, TimeUnit.MINUTES);

        Set<String> set2 = (Set<String>) redisTemplate.opsForValue().get("abc");
        set2.forEach(System.out::println);
    }


}
