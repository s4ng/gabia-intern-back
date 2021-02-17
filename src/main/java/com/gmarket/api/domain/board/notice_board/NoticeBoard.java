package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.BoardStatus;
import com.gmarket.api.domain.board.notice_board.dto.NoticeRequestDto;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notice_board")
public class NoticeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(name="user_id", nullable = false)
    private String userId;

    @Column(name="delete_time")
    private LocalDateTime deletedTime;

    private int hit;

    @Builder
    public NoticeBoard(BoardStatus status, String title, String author, String content, String userId) {
        this.status = status;
        this.title = title;
        this.author = author;
        this.content = content;
        this.userId = userId;
    }

    public void update(NoticeRequestDto noticeRequestDto) {
        this.status = noticeRequestDto.getStatus();
        this.title = noticeRequestDto.getTitle();
        this.content = noticeRequestDto.getContent();
    }

    public void delete() {
        this.deletedTime = LocalDateTime.now();
    }
}
