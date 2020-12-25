package com.opisir.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:META-INF/resources/webjars/");
    }
}
