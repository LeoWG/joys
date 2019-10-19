package com.selffun.joys4fellow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config extends WebMvcConfigurationSupport{

    //apiInfo对象主要是设置我们api文档的标题，描述，访问的地址，创建者等信息
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("留言板接口")
                .description("Powered by Leo")
                .termsOfServiceUrl("https://github.com/LeoWG")
                .contact("Leo")
                .version("1.0")
                .build();
    }

    //docket容器设置我们的文档基础信息，api包的位置，以及路径的匹配规则（包含四种：全匹配，不匹配，正则匹配和ant匹配）
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.selffun.joys4fellow.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 防止@EnableMvc把默认的静态资源路径覆盖了，手动设置的方式
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("----进了addResourceHandlers");
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
