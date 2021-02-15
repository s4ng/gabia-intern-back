package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.global.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeBoardMapper extends EntityMapper<NoticeBoardDto, NoticeBoard> {
        NoticeBoardMapper INSTANCE = Mappers.getMapper(NoticeBoardMapper.class);
}
