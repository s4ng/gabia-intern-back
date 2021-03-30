package com.gmarket.api.domain.alertkeyword;

import com.gmarket.api.domain.alertkeyword.dto.AlertKeywordDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/alert-keyword")
public class AlertKeywordController {

    private final AlertKeywordService alertKeywordService;

    // 알림 키워드 저장
    @PostMapping
    public ResponseEntity<ResponseWrapperDto> save(@RequestBody AlertKeywordDto alertKeywordDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(alertKeywordService.save(alertKeywordDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    알림 키워드 저장 PutMapping api 예시 -> domain.com/alert-keyword

    알림 키워드 저장 RequestBody 예시
{
    "user_id": 1,
    "keyword":"문화상품권"
}

    알림 키워드 저장 ResponseBody 예시
{
    "data": {
        "alert_keyword_id": 1,
        "user_id": 1,
        "keyword":"문화상품권",
        "status": "CREATED",
        "created_at": "2021-03-09T16:23:24.54659",
        "modified_at": "2021-03-09T16:23:24.54659"
    }
}
*/

    // 알림 키워드 리스트
    @GetMapping
    public ResponseEntity<ResponseWrapperDto> list(Long userId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(alertKeywordService.list(userId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
    //
/*
    알림 키워드 리스트 GetMapping api 예시 -> domain.com/alert-keyword?userId=1

    알림 키워드 리스트 RequestBody 예시
{
    "data": [
        {
            "alert_keyword_id": 1,
            "user_id": 1,
            "keyword": "문화상품권",
            "status": "CREATED",
            "created_at": "2021-03-09T16:23:24.54659",
            "modified_at": "2021-03-09T16:23:24.54659"
        },
        {
            "alert_keyword_id": 2,
            "user_id": 1,
            "keyword": "문화상품권1",
            "status": "CREATED",
            "created_at": "2021-03-09T16:23:28.25659",
            "modified_at": "2021-03-09T16:23:28.25659"
        },
        {
            "alert_keyword_id": 3,
            "user_id": 1,
            "keyword": "문화상품권2",
            "status": "CREATED",
            "created_at": "2021-03-09T16:23:30.453452",
            "modified_at": "2021-03-09T16:23:30.453452"
        }
    ]
}
*/

    // 알림 키워드 삭제
    @DeleteMapping
    public ResponseEntity<ResponseWrapperDto> delete(Long id) {
        alertKeywordService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : [No Content]
    }
    // 알림 키워드 삭제 DeleteMapping api 예시 -> domain.com/alert-keyword?id=1
}