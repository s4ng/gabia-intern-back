package com.gmarket.api.domain.board.noticeboard.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import com.gmarket.api.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfoDto {
    private Long boardId;
    private Board.Status status;
    private NoticeBoard.NoticeCategory noticeCategory;
    private String title;
    private String description;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
}
