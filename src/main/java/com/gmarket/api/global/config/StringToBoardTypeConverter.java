package com.gmarket.api.global.config;

import com.gmarket.api.domain.board.BoardType;
import org.springframework.core.convert.converter.Converter;

public class StringToBoardTypeConverter implements Converter<String, BoardType> {

    public boolean isLower(String source){
        return source.matches("^[a-z]*$");
    }

    @Override
    public BoardType convert(String source) {
        if(!isLower(source)) throw new EnumConstantNotPresentException(BoardType.class,"url을 정확하게 작성하세요");
        return BoardType.valueOf(source.toUpperCase());
    }
}