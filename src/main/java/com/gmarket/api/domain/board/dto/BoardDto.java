package com.gmarket.api.domain.board.dto;

import com.gmarket.api.domain.board.enums.GoodsCategory;
import com.gmarket.api.domain.board.enums.GoodsStatus;
import com.gmarket.api.domain.board.enums.NoticeCategory;
import com.gmarket.api.domain.board.enums.BoardStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private long boardId;
    private String userId;
    private String title;
    private int viewCount;
    private String description;
    private BoardStatus status;

    private NoticeCategory noticeCategory;

    private GoodsCategory goodsCategory;
    private GoodsStatus goodsStatus;
    private int sellPrice;

    private LocalDateTime raffleCloseAt;
}
