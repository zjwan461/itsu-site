package com.itsu.sample;

import com.itsu.core.vo.sys.RefreshTokenType;
import com.itsu.site.framework.config.annotation.EnableApiExceptionHandler;
import com.itsu.site.framework.config.annotation.EnableGlobalParamCheck;
import com.itsu.site.framework.config.annotation.EnableRefreshToken;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jerry Su
 * @Date 2021/5/24 14:49
 */
@SpringBootApplication
@MapperScan(basePackages = "com.itsu.sample.mapper")
@EnableGlobalParamCheck
@EnableRefreshToken(type = RefreshTokenType.REDIS)
@EnableApiExceptionHandler
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
