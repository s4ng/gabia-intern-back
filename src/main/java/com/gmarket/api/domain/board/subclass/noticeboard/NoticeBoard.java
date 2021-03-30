package com.gmarket.api.domain.board.subclass.noticeboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.subclass.noticeboard.enums.NoticeCategory;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("NOTICE")
public class NoticeBoard extends Board {

    private NoticeCategory noticeCategory;

    public NoticeBoard dtoToNoticeBoard(NoticeBoardDto noticeBoardDto){
        super.dtoToEntity(noticeBoardDto);
        this.noticeCategory = noticeBoardDto.getNoticeCategory();
        return this;
    }

}
