package com.gmarket.api.domain.chatmessage;

import com.gmarket.api.domain.chatmessage.dto.ChatMessageDto;
import com.gmarket.api.domain.chatmessage.enums.ChatMessageType;
import com.gmarket.api.domain.chatroom.ChatRoom;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType; // 메시지 타입 ENTER, TALK, YET;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 어떤 채팅 방 인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 메시지 보내는 사람이 누군지?

    private String message; // 메시지

    public ChatMessage dtoToEntity(ChatMessageDto chatMessageDto){
        this.chatMessageId = chatMessageDto.getChatMessageId();
        this.chatMessageType = chatMessageDto.getChatMessageType();
        this.message = chatMessageDto.getMessage();
        return this;
    }

    public ChatMessage senderSetting(User user){
        this.user = user;
        return this;
    }

    public ChatMessage chatRoomSetting(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        return this;
    }

    public ChatMessage messageSetting(String message){
        this.chatMessageType = ChatMessageType.TALK;
        this.message = message;
        return this;
    }
}
