package com.gmarket.api.domain.board.notice_board.dto;

import com.gmarket.api.domain.board.BoardStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NoticeInfoDto {
    private Long id;
    private BoardStatus status;
    private String title;
    private String author;
    private String content;
    private String userId;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private int hit;
}
