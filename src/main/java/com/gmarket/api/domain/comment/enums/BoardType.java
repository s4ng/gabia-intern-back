package com.gmarket.api.domain.comment.enums;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.subclass.noticecomment.NoticeComment;
import com.gmarket.api.domain.comment.subclass.presentgoodscomment.PresentGoodsComment;
import com.gmarket.api.domain.comment.subclass.usedgoodscomment.UsedGoodsComment;
import lombok.Getter;

@Getter
public enum BoardType {
    NOTICE, USED, PRESENT;

    public static Comment boardTypeToSubClass(BoardType boardType){
        switch (boardType){
            case NOTICE:
                return new NoticeComment();
            case USED:
                return new UsedGoodsComment();
            case PRESENT:
                return new PresentGoodsComment();
            default:
                throw new IllegalStateException("정확한 게시판 타입을 입력하세요");
        }
    }
}
