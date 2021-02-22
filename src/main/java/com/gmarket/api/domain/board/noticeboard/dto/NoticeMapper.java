package com.gmarket.api.domain.board.noticeboard.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

    NoticeBoard noticeRequestDtoToNoticeBoard(NoticeRequestDto noticeRequestDto);

    @InheritInverseConfiguration
    NoticeResponseDto noticeBoardToNoticeResponseDto(NoticeBoard noticeBoard);

    @InheritInverseConfiguration
    NoticeInfoDto noticeBoardToNoticeInfoDto(NoticeBoard noticeBoard);
}
