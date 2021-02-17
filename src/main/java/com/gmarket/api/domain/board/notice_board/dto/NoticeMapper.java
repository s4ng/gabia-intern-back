package com.gmarket.api.domain.board.notice_board.dto;

import com.gmarket.api.domain.board.notice_board.NoticeBoard;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

    NoticeBoard noticeRequestDtoToNoticeBoard(NoticeRequestDto noticeRequestDto);

    @ObjectFactory
    NoticeResponseDto noticeBoardToNoticeResponseDto(NoticeBoard noticeBoard);

    @ObjectFactory
    NoticeInfoDto noticeBoardToNoticeInfoDto(NoticeBoard noticeBoard);
}
