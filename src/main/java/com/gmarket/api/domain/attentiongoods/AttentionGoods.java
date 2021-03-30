package com.gmarket.api.domain.attentiongoods;

import com.gmarket.api.domain.attentiongoods.dto.AttentionGoodsDto;
import com.gmarket.api.domain.attentiongoods.enums.AttentionGoodsStatus;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AttentionGoods extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long attentionGoodsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private AttentionGoodsStatus status;

    public AttentionGoods dtoToEntity(AttentionGoodsDto attentionGoodsDto){
        this.attentionGoodsId = attentionGoodsDto.getAttentionGoodsId();
        return this;
    }

    public void userAndBoardSetting(User user, Board board){
        this.user = user;
        this.board = board;
    }

    public void registeredStatus(){
        this.status = AttentionGoodsStatus.REGISTERED;
    }

    public void cancelStatus(){
        this.status = AttentionGoodsStatus.CANCEL;
    }
}
