package com.gmarket.api.domain.comment.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.comment.Comment;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentDto { // 댓글의 경우 Sub Entity Class 내용이 일치하여 Sub Dto Class 생략

    private BoardType boardType;

    private Long commentId;

    private Long boardId;

    private Long userId;

    private String comment;

    private CommentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CommentDto  entityToDto(Comment comment){
        this.boardType = comment.getBoardType();
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoard().getBoardId();
        this.userId = comment.getUser().getUserId();
        this.comment = comment.getComment();
        this.status = comment.getStatus();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        return this;
    }
}
