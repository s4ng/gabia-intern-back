package com.gmarket.api.domain.chatroom;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.chatroom.enums.ChatRoomStatus;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    public ChatRoom findByUserAndBoard(User user, Board board);

    public ChatRoom  findByUserAndBoardAndSellerStatusAndBuyerStatus(User user, Board board,  ChatRoomStatus chatRoomStatus1,  ChatRoomStatus chatRoomStatus2);

    public List<ChatRoom> findByUserOrSellerIdOrderByChatRoomIdDesc(User user, Long userId);

    @Modifying
    @Query("SELECT m FROM ChatRoom m WHERE (m.user = ?1 AND m.buyerStatus <> ?2) OR ( m.sellerId = ?3 AND m.sellerStatus <> ?4)")
    public List<ChatRoom> loginUserChatListOrderByModifiedAtAsc(User user, ChatRoomStatus userStatus,
                                            Long sellerId,  ChatRoomStatus sellerStatus );
}
