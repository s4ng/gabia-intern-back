/**
 * Todo
 * TimeStamp 타입 바꾸기
 * 변수명 바꾸기
 */
package com.gmarket.api.domain.notice_board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@Table(name="notice_board")
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Byte status;

    @Setter
    private String title;

    private String author;

    @Lob
    @Setter
    private String content;

    private String user_id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp create_time;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp modify_time;

    @Setter
    private Timestamp delete_time;

    private int hit;

    public NoticeBoard() {
    }
}
