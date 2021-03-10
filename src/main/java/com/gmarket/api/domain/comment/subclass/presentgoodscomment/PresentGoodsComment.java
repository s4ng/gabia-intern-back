package com.gmarket.api.domain.comment.subclass.presentgoodscomment;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("PRESENT")
public class PresentGoodsComment extends Comment {

    public PresentGoodsComment(){

        super.setBoardType(BoardType.PRESENT);

    }
}
