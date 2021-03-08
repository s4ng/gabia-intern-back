package com.gmarket.api.domain.comment.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long commentId;
    private User userId;
    private Board boardId;
    private String comment;
    private CommentStatus status;
}
