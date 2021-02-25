package com.gmarket.api.domain.board.noticeboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.NoticeCategory;
import com.gmarket.api.global.util.ViewJSON;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("NOTICE") // DB board 구분 column 값
@PrimaryKeyJoinColumn(name = "notice_board_id") // DB foreign key name
public class NoticeBoard extends Board {

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Views.class)
    private NoticeCategory noticeCategory;

    public NoticeBoard copySub(NoticeBoard noticeBoard, BoardDto boardDto){
        noticeBoard.noticeCategory = boardDto.getNoticeCategory();
        return noticeBoard;
    }
}
