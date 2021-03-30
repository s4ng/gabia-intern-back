package com.gmarket.api.domain.attentiongoods;

import com.gmarket.api.domain.attentiongoods.dto.AttentionGoodsDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/attention-goods")
public class AttentionGoodsController {

    private final AttentionGoodsService attentionGoodsService;

    // 관심 상품 저장
    @PostMapping
    public ResponseEntity<ResponseWrapperDto> save(@RequestBody AttentionGoodsDto attentionGoodsDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(attentionGoodsService.save(attentionGoodsDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }


    // 관심 상품 삭제
    @DeleteMapping
    public ResponseEntity<ResponseWrapperDto> delete(Long attentionGoodsId) {
        attentionGoodsService.delete(attentionGoodsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : [No Content]
    }


    // 관심 상품 리스트
    @GetMapping
    public ResponseEntity<ResponseWrapperDto> list(Long userId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(attentionGoodsService.list(userId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }

}
