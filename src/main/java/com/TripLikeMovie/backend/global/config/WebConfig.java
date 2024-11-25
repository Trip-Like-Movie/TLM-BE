package com.TripLikeMovie.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 매핑 설정
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:/Users/kimjongchan/TripLikeMovie/TLM-BE/uploads/"); // 실제 파일이 있는 경로
    }
}
