package com.itsu.sample.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.itsu.core.component.mvc.SpringMvcHelper;
import com.itsu.core.component.security.AccessStrategy;
import com.itsu.core.component.security.StrategyEnum;
import com.itsu.sample.mapper.TestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Jerry Su
 * @Date 2021/5/24 14:48
 */
@RestController
@RequestMapping("/api")
@AccessStrategy(StrategyEnum.ANY)
public class DemoController {

    @Resource
    private TestMapper mapper;

    @Resource
    private SpringMvcHelper springMvcHelper;


    @GetMapping("/list")
    public String list(HttpServletRequest request) throws Exception {
        HandlerMethod handlerMethod = springMvcHelper.getHandlerMethod(request);
        if (handlerMethod != null) {
            Object bean = handlerMethod.getBean();
            System.out.println(bean.getClass());
            System.out.println(handlerMethod.getBeanType());
            GetMapping annotation = handlerMethod.getMethodAnnotation(GetMapping.class);
            System.out.println(annotation);
        }

//        hm.getann
        return handlerMethod.toString();
    }

    @GetMapping("/demo")
    public String idx() {
        return "my db version is " + mapper.selectDBVersion();
    }

    @GetMapping("/listBean")
    public String[] listBean() {
        return SpringUtil.getApplicationContext().getBeanDefinitionNames();
    }
}
