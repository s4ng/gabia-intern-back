package com.gmarket.api.domain.chatroom.dto;

import com.gmarket.api.domain.chatroom.ChatRoom;
import com.gmarket.api.domain.chatroom.enums.ChatRoomStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private Long boardId;

    private Long userId;

    private Long sellerId;

    private ChatRoomStatus sellerStatus;

    private ChatRoomStatus buyerStatus;

    private String chatRoomName;

    private LocalDateTime createdAt;

    private int sellerCount; // 판매자가 채팅방의 메시지를 읽지 않은 상태의 개수

    private int buyerCount; // 구매자가 채팅방의 메시지를 읽지 않은 상태의 개수

    public ChatRoomDto entityToChatRoomDto(ChatRoom chatRoom){
        this.chatRoomId = chatRoom.getChatRoomId();
        this.boardId = chatRoom.getBoard().getBoardId();
        this.userId = chatRoom.getUser().getUserId();
        this.sellerStatus = chatRoom.getSellerStatus();
        this.sellerId = chatRoom.getSellerId();
        this.buyerStatus = chatRoom.getBuyerStatus();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.createdAt = chatRoom.getCreatedAt();
        this.sellerCount = chatRoom.getSellerCount();
        this.buyerCount = chatRoom.getBuyerCount();
        return  this;
    }
}
