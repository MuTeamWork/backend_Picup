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


    @Value("${file-demo.prefix-image}")
    private String prefixImage;

    @Value("${file-demo.prefix-thumbnail}")
    private String prefixThu;

    @Value("${file-demo.file-image-path}")
    private String fileImagePath;

    @Value("${file-demo.file-thumbnail-path}")
    private String fileThumbnailPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(prefixImage + "**").addResourceLocations("file:" + fileImagePath);
        registry.addResourceHandler(prefixThu + "**").addResourceLocations("file:" + fileThumbnailPath);
    }

}
