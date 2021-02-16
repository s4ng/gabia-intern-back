package com.gmarket.api.global.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    Object result;

    @Builder
    ResponseDto(Object result) {
        this.result = result;
    }
}
