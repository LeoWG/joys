package com.selffun.joys4fellow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/***
 * 项目访问swagger地址：localhost:8080/joys/swagger-ui.html
 */
@SpringBootApplication
@EnableCaching
public class JoysApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JoysApplication.class, args);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(JoysApplication.class);
    }
}
