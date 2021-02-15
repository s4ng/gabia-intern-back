package com.gmarket.api.domain.board.notice_board;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardDto {
    private NoticeBoard.Status status;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createTime;
    private String userId;
}
