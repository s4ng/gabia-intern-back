package com.gmarket.api.global.converter;

import com.gmarket.api.domain.board.enums.BoardType;
import org.springframework.core.convert.converter.Converter;

public class StringToBoardTypeConverter implements Converter<String, BoardType> {

    public boolean isLower(String source){
        return source.matches("^[a-z]*$");
    }

    @Override
    public BoardType convert(String source) {
        if(!isLower(source)) throw new IllegalStateException("url을 정확하게 입력하세요");
        return BoardType.valueOf(source.toUpperCase());
    }
}