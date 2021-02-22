package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.noticeboard.dto.NoticeRequestDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardRequestDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="boards/share/posts")
public class PresentGoodsBoardController {

    private final PresentGoodsBoardService presentGoodsBoardService;

    public PresentGoodsBoardController(PresentGoodsBoardService presentGoodsBoardService) {
        this.presentGoodsBoardService = presentGoodsBoardService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWrapperDto> create(@RequestBody PresentGoodsBoardRequestDto presentGoodsRequestDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(presentGoodsBoardService.create(presentGoodsRequestDto))
                .build();

        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    }


}
