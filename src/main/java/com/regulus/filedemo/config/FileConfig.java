package com.regulus.filedemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-14
 */

@Configuration
public class FileConfig implements WebMvcConfigurer {


    @Value("${file-demo.prefix}")
    private String prefix;

    @Value("${file-demo.file-path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(prefix + "**").addResourceLocations("file:" + filePath);
    }
}
