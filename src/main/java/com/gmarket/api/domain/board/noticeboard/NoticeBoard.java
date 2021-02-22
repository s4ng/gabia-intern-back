package com.gmarket.api.domain.board.noticeboard;

import com.gmarket.api.domain.board.Board;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NOTICE")
@Table(name = "notice_board")
@PrimaryKeyJoinColumn(name = "notice_board_id")
public class NoticeBoard extends Board {

    @Enumerated(EnumType.STRING)
    private  NoticeCategory noticeCategory;

    public enum NoticeCategory{
        UPDATE, EVENT
    }
}
