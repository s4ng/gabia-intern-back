package com.gmarket.api.domain.board;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED) // jpa 상속 - 조인전략
@DiscriminatorColumn(name="board_type") // 상속 타입 구분 컬럼 - DB
public abstract class Board extends BaseTimeEntity {
    @Id // 엔티티 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB increment
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 -> Board 에서 User 정보를 가져오기 전엔 같이 로딩되지 않음
    @JoinColumn(name = "user_id") // 조인 컬럼은 필수로 명시
    private User user;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    private String title;

    private String description;

    private int viewCount;

    @Transient  // DB Column X
    private BoardType boardType; // BoardType 확인을 위함

    @Transient  // DB Column X
    private UserType userType; // UserType 확인을 위함

    public Board dtoToEntity(BoardDto boardDto){
        this.status = boardDto.getStatus();
        this.title = boardDto.getTitle();
        this.description = boardDto.getDescription();
        this.boardType = boardDto.getBoardType();
        this.userType = boardDto.getUserType();
        return this;
    }

    public void createdStatus() {
        this.status = BoardStatus.CREATED;
    }

    public void modifiedStatus(Long boardId) {
        this.boardId = boardId;
        this.status = BoardStatus.MODIFIED;
    }

    public void closedStatus() {
        this.status = BoardStatus.CLOSED;
    }

    public void deletedStatus() {
        this.status = BoardStatus.DELETED;
    }

    public void userSetting(User user){ // 연관 관계 편의 메서드
        this.user = user;
//        user.getBoardList().add(this);
//        user.getBoardList();
    }

    public void addViewCount(){
        this.viewCount++;
    }

    public void completeDeal(){
        this.title = "[판매완료]"+this.title;
    }

}
