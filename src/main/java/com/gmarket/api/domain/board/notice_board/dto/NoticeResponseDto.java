package com.gmarket.api.domain.board.notice_board.dto;

import com.gmarket.api.domain.board.BoardStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
    private Long id;
    private BoardStatus status;
    private String title;
    private String author;
    private String content;
    private String userId;
}
