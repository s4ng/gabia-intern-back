package com.gmarket.api.domain.chatmessage.dto;

import com.gmarket.api.domain.chatmessage.ChatMessage;
import com.gmarket.api.domain.chatmessage.enums.ChatMessageType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageDto {
    private Long chatMessageId;

    private ChatMessageType chatMessageType; // 메시지 타입 ENTER, TALK, YET;

    private Long chatRoomId; // 어떤 채팅 방 인지

    private Long userId; // 메시지 보내는 사람이 누군지?

    private String message; // 메시지

    public ChatMessageDto entityToChatMessageDto(ChatMessage chatMessage){

        this.chatMessageId = chatMessage.getChatMessageId();
        this.chatMessageType = chatMessage.getChatMessageType();
        this.chatRoomId = chatMessage.getChatMessageId();
        this.userId = chatMessage.getUser().getUserId();
        this.message = chatMessage.getMessage();

        return this;
    }
}
