package com.gmarket.api.global.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseWrapperDto {
    private Object data;

    @Builder
    ResponseWrapperDto(Object data) {
        this.data = data;
    }
}
