package com.gmarket.api.domain.chattingmessage;

import com.gmarket.api.domain.chatting.Chatting;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChattingMessage {

    @Id @GeneratedValue
    private long chattingMessageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_id")
    private Chatting chattingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User senderId;

    private String chattingMessage;

    private boolean readOrNot;

    private LocalDateTime createAt;
}
