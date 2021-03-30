package com.gmarket.api.domain.attentiongoods.dto;

import com.gmarket.api.domain.attentiongoods.AttentionGoods;
import com.gmarket.api.domain.attentiongoods.enums.AttentionGoodsStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AttentionGoodsDto {

    private Long attentionGoodsId;

    private Long userId;

    private Long boardId;

    private AttentionGoodsStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public AttentionGoodsDto entityToDto(AttentionGoods attentionGoods){
        this.attentionGoodsId = attentionGoods.getAttentionGoodsId();
        this.userId = attentionGoods.getUser().getUserId();
        this.boardId = attentionGoods.getBoard().getBoardId();
        this.status = attentionGoods.getStatus();
        this.createdAt = attentionGoods.getCreatedAt();
        this.modifiedAt = attentionGoods.getModifiedAt();
        return this;
    }
}
