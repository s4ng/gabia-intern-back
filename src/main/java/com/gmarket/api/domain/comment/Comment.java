package com.gmarket.api.domain.comment;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // jpa 상속 - 조인전략
@DiscriminatorColumn(name="board_type") // 상속 타입 구분 컬럼 - DB
public abstract class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB increment 따름
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Transient  // DB Column X
    @Setter
    private BoardType boardType; // BoardType 확인을 위함

    public Comment dtoToEntity(CommentDto commentDto){
        this.commentId = commentDto.getCommentId();
        this.comment = commentDto.getComment();
        this.status = commentDto.getStatus();
        this.boardType = commentDto.getBoardType();
        return this;
    }

    public void boardAndUserSetting(Board board, User user){
        this.board = board;
        this.user = user;
    }

    public void createdStatus() {
        this.status = CommentStatus.CREATED;
    }

    public void deletedStatus() {
        this.status = CommentStatus.DELETED;
    }

    public void update(CommentDto commentDto) {
        this.status = CommentStatus.MODIFIED;
        this.boardType = commentDto.getBoardType();
        this.comment = commentDto.getComment();
    }
}
