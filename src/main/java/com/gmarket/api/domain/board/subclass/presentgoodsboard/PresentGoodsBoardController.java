package com.gmarket.api.domain.board.subclass.presentgoodsboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsCategory;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsStatus;
import com.gmarket.api.global.util.JsonViews;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/boards/present/posts")
public class PresentGoodsBoardController {

    private final PresentGoodsBoardService presentGoodsBoardService;

    // 중고 상품 게시글 검색 조회
    @GetMapping("/search")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> search(
            BoardStatus status,
            PresentGoodsCategory category,
            PresentGoodsStatus goodsStatus,
            String title,
            Integer page) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder().data(
                presentGoodsBoardService.search(
                        status,
                        category,
                        goodsStatus,
                        title,
                        page
                ))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]

    }
}
