package com.gmarket.api.domain.board.subclass.usedgoodsboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsSortOption;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
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
@RequestMapping("/boards/used/posts")
public class UsedGoodsBoardController {

    private final UsedGoodsBoardService usedGoodsBoardService;

    // 중고 상품 게시글 검색 조회
    @GetMapping("/search")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> search(
            BoardStatus status,
            UsedGoodsCategory category,
            UsedGoodsStatus goodsStatus,
            String title,
            Integer min,
            Integer max,
            Integer page,
            UsedGoodsSortOption sort) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder().data(
                usedGoodsBoardService.search(
                        status,
                        category,
                        goodsStatus,
                        title,
                        min,
                        max,
                        page,
                        sort
                ))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]

    }

}
