package com.gmarket.api.domain.board;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeRequestDto;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
@DiscriminatorColumn(name = "board_type") // 매핑 테이블 구분하는 컬럼
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private int viewCount;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATE,CLOSE,DELETE
    }

    public void update(NoticeRequestDto noticeRequestDto) {
        this.status = noticeRequestDto.getStatus();
        this.title = noticeRequestDto.getTitle();
        this.description = noticeRequestDto.getDescription();
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    }
