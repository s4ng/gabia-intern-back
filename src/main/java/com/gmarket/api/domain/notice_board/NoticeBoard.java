package com.gmarket.api.domain.notice_board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="notice_board")
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Byte status;
    private String title;
    private String author;
    @Lob
    private String content;
    private String user_id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp create_time;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp modify_time;
    private Timestamp delete_time;
    private int hit;
}
