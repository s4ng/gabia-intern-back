package com.gmarket.api.domain.comment.noticecomnet;

import com.gmarket.api.domain.comment.Comment;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@DiscriminatorValue("USED")
@PrimaryKeyJoinColumn(name = "notice_comment_id")
public class NoticeComment extends Comment {
}
