package com.gmarket.api.domain.comment.usedgoodscomment;

import com.gmarket.api.domain.comment.Comment;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@DiscriminatorValue("USED")
@PrimaryKeyJoinColumn(name = "used_comment_id")
public class UsedGoodsComment extends Comment {
}
