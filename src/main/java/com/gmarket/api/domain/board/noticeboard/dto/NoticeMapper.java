package com.gmarket.api.domain.board.noticeboard.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

    NoticeBoard noticeRequestDtoToNoticeBoard(NoticeRequestDto noticeRequestDto);

    default NoticeResponseDto noticeBoardToNoticeResponseDto(NoticeBoard noticeBoard) {
        NoticeResponseDto responseDto = NoticeResponseDto.builder()
                .boardId(noticeBoard.getBoardId())
                .status(noticeBoard.getStatus())
                .title(noticeBoard.getTitle())
                .description(noticeBoard.getDescription())
                .noticeCategory(noticeBoard.getNoticeCategory())
                .userId(noticeBoard.getUser().getUserId())
                .build();

        return responseDto;
    }

    @InheritInverseConfiguration
    NoticeInfoDto noticeBoardToNoticeInfoDto(NoticeBoard noticeBoard);
}
