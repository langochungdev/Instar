package com.instar.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**") // bất cứ req nào bất đầu bằng uploads sẽ đc xử lí như là req truy cập file tĩnh
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
        // lấy file ở thư mục uploads
    }
}
