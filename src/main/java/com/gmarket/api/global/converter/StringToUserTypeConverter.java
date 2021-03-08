package com.gmarket.api.global.converter;

import com.gmarket.api.domain.user.enums.UserType;
import org.springframework.core.convert.converter.Converter;

public class StringToUserTypeConverter implements Converter<String, UserType> {

    public boolean isLower(String source){
        return source.matches("^[a-z]*$");
    }

    @Override
    public UserType convert(String source) {
        if(!isLower(source)) throw new IllegalStateException("url을 정확하게 입력하세요");
        return UserType.valueOf(source.toUpperCase());
    }
}