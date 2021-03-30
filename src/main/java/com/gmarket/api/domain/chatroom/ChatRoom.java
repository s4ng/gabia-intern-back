package com.gmarket.api.domain.chatroom;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import com.gmarket.api.domain.chatroom.dto.ChatRoomDto;
import com.gmarket.api.domain.chatroom.enums.ChatRoomStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class ChatRoom extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board; // 중고 상품 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 채팅 거래 신청 유저

    private Long sellerId; // 개발 편의성과 성능을 위해 판매 회원의 식별키도 관리

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus sellerStatus; // CREATED: 거래신청 받다, CLOSED: 채팅으로 거래 완료, DELETED: 채팅 방 나가기

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus buyerStatus; // CREATED: 거래 신청, CLOSED: 채팅으로 거래 완료, DELETED: 채팅 방 나가기

    private String chatRoomName;

    private int sellerCount; // 판매자가 채팅방의 메시지를 읽지 않은 개수

    private int buyerCount; // 구매자가 채팅방의 메시지를 읽지 않은 개수

    public ChatRoom dtoToEntity(ChatRoomDto chatRoomDto){
        this.chatRoomId = chatRoomDto.getChatRoomId();
        this.sellerStatus = chatRoomDto.getSellerStatus();
        this.buyerStatus = chatRoomDto.getBuyerStatus();
        this.sellerId = chatRoomDto.getSellerId();
        this.chatRoomName = chatRoomDto.getChatRoomName();
        this.sellerCount = chatRoomDto.getSellerCount();
        this.buyerCount = chatRoomDto.getBuyerCount();
        return this;
    }

    public ChatRoom boardAndUserSetting(Board board, User user){
        this.board = board;
        this.user = user;
        return this;
    }

    public ChatRoom createStatus(){
        this.sellerStatus = ChatRoomStatus.CREATED;
        this.buyerStatus = ChatRoomStatus.CREATED;
        return this;
    }

    public void createName(){
        UsedGoodsBoard usedGoodsBoard = (UsedGoodsBoard)this.board;
        this.chatRoomName = this.board.getUser().getName() + "|" +
                user.getName() +"|"+
                board.getTitle() + "|" +
                this.sellerStatus+"|"+
                usedGoodsBoard.getImg();
    }

    public void buyerCount(){
        this.buyerCount++;
    }

    public void sellerCount(){
        this.sellerCount++;
    }

    public void buyerView(){
        this.buyerCount = 0;
    }

    public void sellerView(){
        this.sellerCount = 0;
    }

    public void sellerLeave(){
        this.sellerStatus = ChatRoomStatus.DELETED;
    }

    public void buyerLeave(){
        this.buyerStatus = ChatRoomStatus.DELETED;
    }

    public void completeDeal(){
        this.sellerStatus = ChatRoomStatus.CLOSED;
        this.buyerStatus = ChatRoomStatus.CLOSED;
    }
}
