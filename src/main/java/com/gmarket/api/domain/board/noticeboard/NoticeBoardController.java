package com.gmarket.api.domain.board.noticeboard;

import com.gmarket.api.domain.board.noticeboard.dto.NoticeInfoDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeRequestDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeResponseDto;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.global.util.ResponseWrapperDto;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="boards/notice/posts")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWrapperDto> create(@RequestBody NoticeRequestDto noticeRequestDto) {

        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(noticeBoardService.create(noticeRequestDto))
                .build();

        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapperDto> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {

        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(noticeBoardService.getNoticePage(page))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapperDto> findOne(@PathVariable Long id) {

        NoticeInfoDto findResult = noticeBoardService.getNoticeById(id);
        if(findResult == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                    .data(findResult)
                    .build();
            return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapperDto> update(
            @RequestBody NoticeRequestDto noticeBoardDto,
            @PathVariable Long id) {

        NoticeResponseDto noticeResponseDto = noticeBoardService.updateNotice(noticeBoardDto, id);
        if(noticeBoardDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                    .data(noticeResponseDto)
                    .build();
            return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NoticeResponseDto> delete(@PathVariable Long id) {

        NoticeResponseDto noticeResponseDto = noticeBoardService.deleteNotice(id);
        if(noticeResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(noticeResponseDto, HttpStatus.NO_CONTENT);
        }
    }
}
