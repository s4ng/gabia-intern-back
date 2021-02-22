package com.gmarket.api.domain.comment.presentgoodscomment;

import com.gmarket.api.domain.comment.Comment;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@DiscriminatorValue("PRESENT")
@PrimaryKeyJoinColumn(name = "present_comment_id")
public class PresentGoodsComment extends Comment {
}
