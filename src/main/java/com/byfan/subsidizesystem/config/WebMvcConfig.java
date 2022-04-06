package com.byfan.subsidizesystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfiguration
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/6 22:03
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //重写方法
        //修改tomcat 虚拟映射
        registry.addResourceHandler("/**").
                addResourceLocations("file:src/main/resources/static","file:src/main/resources/static/verifycode");//定义相对路径 很重要
    }
}
