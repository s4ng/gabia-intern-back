package com.gmarket.api.domain.board;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import com.gmarket.api.global.util.ViewJSON;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@JsonView(ViewJSON.Views.class)
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
@DiscriminatorColumn(name = "board_type") // 매핑 테이블 구분하는 컬럼
public abstract class Board extends BaseTimeEntity {
    // Board 테이블이 실존하는 DB 테이블임에도 Board 테이블에만 값을 넣을 경우는 없다고 생각하여 추상클래스로 선언

    @Id
    @GeneratedValue
    private long boardId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "write_id")
    private User user;

    private String title;

    private int viewCount;

    private String description;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    public void delete() {
        this.status = BoardStatus.DELETE;
    }

    public void modified() {this.status = BoardStatus.MODIFIED; }

    public void addViewCount() {
        this.viewCount += 1;
    }

 public Board copySuper(BoardDto boardDto){
   this.boardId = boardDto.getBoardId();
   this.title = boardDto.getTitle();
   this.description = boardDto.getDescription();
   this.status = boardDto.getStatus();
   return this;
}
}
