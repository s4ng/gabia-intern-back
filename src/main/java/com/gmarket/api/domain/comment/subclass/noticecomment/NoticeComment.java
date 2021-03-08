package com.gmarket.api.domain.comment.subclass.noticecomment;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class NoticeComment extends Comment {

    public NoticeComment(){
        super.setBoardType(BoardType.NOTICE);
    }

}
