package com.gmarket.api.domain.comment;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import com.gmarket.api.global.util.ViewJSON;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
@DiscriminatorColumn(name = "comment_type") // 매핑 테이블 구분하는 컬럼
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue
    @JsonView(ViewJSON.Views.class)
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "register_id")
    @JsonView(ViewJSON.Views.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonView(ViewJSON.Views.class)
    private Board board;

    @JsonView(ViewJSON.Views.class)
    private String comment;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public Comment copySuper(Comment comment, CommentDto commentDto){
        comment.commentId = commentDto.getCommentId();
        comment.comment = commentDto.getComment();
        comment.status = commentDto.getStatus();
        return comment;
    }

    public void delete() { this.status = CommentStatus.DELETE; }

    public void modified() {this.status = CommentStatus.MODIFIED; }

    public void setCreate( User user, Board board){
        this.user = user;
        this.board = board;
    }
}
