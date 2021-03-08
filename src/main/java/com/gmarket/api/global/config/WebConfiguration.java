package com.gmarket.api.global.config;

import com.gmarket.api.global.converter.StringToBoardTypeConverter;
import com.gmarket.api.global.converter.StringToBoardTypeConverter2;
import com.gmarket.api.global.converter.StringToUserTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToBoardTypeConverter());
        registry.addConverter(new StringToUserTypeConverter());
        registry.addConverter(new StringToBoardTypeConverter2());
    }

    @Override
    public  void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }
}