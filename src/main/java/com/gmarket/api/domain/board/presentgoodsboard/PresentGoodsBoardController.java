package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardInfoDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardRequestDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardResponseDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<ResponseWrapperDto> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {

        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(presentGoodsBoardService.getPage(page))
                .build();

        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    }

    @GetMapping(value= "/{id}")
    public ResponseEntity<ResponseWrapperDto> findOne(@PathVariable Long id) {

        PresentGoodsBoardInfoDto infoDto = presentGoodsBoardService.getById(id);
        if(infoDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ResponseWrapperDto wrapperDto = ResponseWrapperDto.builder()
                    .data(infoDto)
                    .build();

            return new ResponseEntity<>(wrapperDto, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapperDto> update(
            @RequestBody PresentGoodsBoardRequestDto requestDto,
            @PathVariable Long id) {

        PresentGoodsBoardResponseDto responseDto = presentGoodsBoardService.update(requestDto, id);
        if(responseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                    .data(responseDto)
                    .build();

            return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PresentGoodsBoardResponseDto> delete(@PathVariable Long id) {

        PresentGoodsBoardResponseDto responseDto = presentGoodsBoardService.delete(id);
        if(responseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
