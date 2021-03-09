package com.gmarket.api.domain.comment.subclass.noticecomment;

import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("NOTICE")
public class NoticeComment extends Comment {

    public NoticeComment(){
        super.setBoardType(BoardType.NOTICE);
    }

}
