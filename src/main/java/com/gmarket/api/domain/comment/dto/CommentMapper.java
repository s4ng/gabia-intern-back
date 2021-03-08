package com.gmarket.api.domain.comment.dto;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.noticecomnet.NoticeComment;
import com.gmarket.api.domain.comment.presentgoodscomment.PresentGoodsComment;
import com.gmarket.api.domain.comment.usedgoodscomment.UsedGoodsComment;

public class CommentMapper {
    public static Comment dtoToEntity(BoardType boardType, CommentDto commentDto){
        if ( commentDto == null ) {
            return null;
        }
        else if(boardType.equals(BoardType.notice)){
            NoticeComment noticeComment = new NoticeComment();
            noticeComment.copySuper(noticeComment, commentDto);
            return noticeComment;
        }
        else if(boardType.equals(BoardType.used)){
            UsedGoodsComment usedGoodsComment = new UsedGoodsComment();
            usedGoodsComment.copySuper(usedGoodsComment, commentDto);
            return usedGoodsComment;
        }
        else {
            PresentGoodsComment presentGoodsComment = new PresentGoodsComment();
            presentGoodsComment.copySuper(presentGoodsComment, commentDto);
            return presentGoodsComment;
        }
    }
}
