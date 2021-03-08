package com.gmarket.api.domain.pricesuggestion;

import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.pricesuggestion.dto.PriceSuggestionDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/suggestion")
public class PriceSuggestionController {

    private final PriceSuggestionService priceSuggestionService;

    // 가격 제시 저장
    @PostMapping
    public ResponseEntity<ResponseWrapperDto> save(@RequestBody PriceSuggestionDto priceSuggestionDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(priceSuggestionService.save(priceSuggestionDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    가격 제시 저장 PostMapping api 예시 -> domain.com/suggestion

    가격 제시 저장 RequestBody 예시
{
    "user_id" : "2",
    "board_id" : "1",
    "suggestion_price" : 50000
}

    가격 제시 저장 ResponseBody 예시
{
    "data": {
        "price_suggestion_id": 1,
        "user_id": 2,
        "board_id": 1,
        "suggestion_price": 50000,
        "status": "SUGGESTION",
        "created_at": "2021-03-08T20:21:37.4162048",
        "modified_at": "2021-03-08T20:21:37.4162048"
    }
}
*/


}