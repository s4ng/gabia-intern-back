package com.gmarket.api.global.config;

import com.gmarket.api.global.converter.StringToBoardTypeConverter;
import com.gmarket.api.global.converter.StringToCommentBoardTypeConverter;
import com.gmarket.api.global.converter.StringToUserTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToBoardTypeConverter());
        registry.addConverter(new StringToUserTypeConverter());
        registry.addConverter(new StringToCommentBoardTypeConverter());
    }

    @Override
    public  void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*").
            allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name());
    }
}
