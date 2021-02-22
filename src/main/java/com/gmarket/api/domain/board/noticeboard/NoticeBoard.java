package com.gmarket.api.domain.board.noticeboard;

import com.gmarket.api.domain.board.Board;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("NOTICE")
@Getter
@PrimaryKeyJoinColumn(name = "notice_board_id")
public class NoticeBoard extends Board {

    @Enumerated(EnumType.STRING)
    private  NoticeCategory noticeCategory;

    enum NoticeCategory{
        UPDATE, EVENT
    }
}
