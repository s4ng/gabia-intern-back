package com.gmarket.api.domain.comment.subclass.usedgoodscomment;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class UsedGoodsComment extends Comment {
    public UsedGoodsComment(){
        super.setBoardType(BoardType.USED);
    }
}
