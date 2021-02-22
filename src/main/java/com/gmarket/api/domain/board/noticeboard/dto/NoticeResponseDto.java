package com.gmarket.api.domain.board.noticeboard.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import com.gmarket.api.domain.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
    private Long boardId;
    private Board.Status status;
    private String title;
    private String description;
    private NoticeBoard.NoticeCategory noticeCategory;
    private Long userId;
}
