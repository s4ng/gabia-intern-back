/**
 * Todo
 * TimeStamp 타입 바꾸기
 * 변수명 바꾸기
 */
package com.gmarket.api.domain.board.notice_board;

import com.sun.istack.NotNull;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="notice_board")
public class NoticeBoard {

    public enum Status { CREATE, CLOSE, DELETE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    private String title;

    @Setter
    private String author;

    @Setter
    @Column(columnDefinition= "TEXT", nullable = false)
    private String content;

    @Column(name="user_id")
    @Setter
    private String userId;

    @Column(name="create_time")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private LocalDateTime createTime;

    @Column(name="modify_time")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private LocalDateTime modifyTime;

    @Setter
    @Column(name="delete_time")
    private LocalDateTime deleteTime;

    @Setter
    private int hit;

    public NoticeBoard() {
    }
}
