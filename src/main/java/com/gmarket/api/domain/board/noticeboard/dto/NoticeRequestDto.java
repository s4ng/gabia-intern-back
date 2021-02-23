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
public class NoticeRequestDto {
    private NoticeBoard.NoticeCategory noticeCategory;
    private Board.Status status;
    private String title;
    private String description;
    private Long userId;
    private int viewCount;
}
