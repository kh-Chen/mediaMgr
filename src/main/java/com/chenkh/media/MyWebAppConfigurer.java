package com.chenkh.media;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/E/**").addResourceLocations("file:E:/");
        registry.addResourceHandler("/G/**").addResourceLocations("file:G:/");
        super.addResourceHandlers(registry);
    }
}
