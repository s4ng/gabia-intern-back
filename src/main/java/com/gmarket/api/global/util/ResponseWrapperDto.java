package com.gmarket.api.global.util;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Bean 생성자
@JsonView(JsonViews.Response.class)
public class ResponseWrapperDto {
    private Object data;

    @Builder
    ResponseWrapperDto(Object data) {
        this.data = data;
    }
}
