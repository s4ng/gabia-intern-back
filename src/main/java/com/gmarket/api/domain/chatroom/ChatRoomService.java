package com.gmarket.api.domain.chatroom;


import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.chatmessage.ChatMessage;
import com.gmarket.api.domain.chatmessage.ChatMessageRepository;
import com.gmarket.api.domain.chatmessage.dto.ChatMessageDto;
import com.gmarket.api.domain.chatroom.dto.ChatRoomDto;
import com.gmarket.api.domain.chatroom.enums.ChatRoomStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final BoardRepositoryInterface boardRepository;

    private final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatRoomDto createChatRoom(ChatRoomDto chatRoomDto){

        User user = userRepository.findById(chatRoomDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        Board board = boardRepository.findById(chatRoomDto.getBoardId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 게시글 입니다" ));

        if(board.getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException( "삭제 된 게시글 입니다" );
        }

        if(board.getStatus().equals(BoardStatus.CLOSED)){
            throw new IllegalStateException( "판매 종료 된 상품 입니다" );
        }

        if(board.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalStateException( "본인이 판매하는 글에는 거래 신청이 불가합니다" );
        }

        // 채팅 방 중복 검사
        ChatRoom chatRoom = chatRoomRepository.findByUserAndBoard(user, board);

        if(chatRoom == null){
            chatRoom = new ChatRoom().dtoToEntity(chatRoomDto)
                                     .boardAndUserSetting(board, user)
                                     .createStatus();
            chatRoom.createName();

            ChatMessage chatMessage = new ChatMessage()
                    .senderSetting(user)
                    .chatRoomSetting(chatRoom)
                    .messageSetting("안녕하세요, 구매 문의드립니다.");

            chatMessageRepository.save(chatMessage);

            chatRoom.sellerCount();

            chatRoomRepository.save(chatRoom);

            PageRequest pageRequest =
                    PageRequest.of(0, 50, Sort.by( Sort.Direction.DESC, "chatMessageId") );


            List<ChatMessageDto> chatMessageDtoList = chatMessageRepository.findByChatRoom( chatRoom, pageRequest ).getContent()
                    .stream()
                    .map( chatMessage1 -> new ChatMessageDto().entityToChatMessageDto(chatMessage1))
                    .collect(Collectors.toList());
            // Stream 생성 -> map()은 중개연산 ( comment -> dto 맵핑 연산 ) -> collect 스트림을 컬렉션으로 변환 toList

            Collections.reverse(chatMessageDtoList);

            messagingTemplate.convertAndSend("/sub/chat/room/"+chatRoom.getChatRoomId(),chatMessageDtoList);

            // 실시간으로 해당 채티방의 구매자에게 채팅방 리스트를 최신화
            messagingTemplate.convertAndSend(
                    "/sub/chat/user/"+user.getUserId(),
                    chatRoomRepository.loginUserChatList(user, ChatRoomStatus.DELETED,
                            user.getUserId(), ChatRoomStatus.DELETED)
                            .stream()
                            .map(chatRoom1 -> new ChatRoomDto().entityToChatRoomDto(chatRoom1))
                            .collect(Collectors.toList()
                            )
            );

            // 실시간으로 해당 채티방의 판매자에게 채팅방 리스트를 최신화
            messagingTemplate.convertAndSend(
                    "/sub/chat/user/"+board.getUser().getUserId(),
                    chatRoomRepository.loginUserChatList(board.getUser(), ChatRoomStatus.DELETED,
                            board.getUser().getUserId(), ChatRoomStatus.DELETED)
                            .stream()
                            .map(chatRoom1 -> new ChatRoomDto().entityToChatRoomDto(chatRoom1))
                            .collect(Collectors.toList()
                            )
            );
        }

        return chatRoomDto.entityToChatRoomDto(chatRoom);
    }



    // 채팅 방 식별키, 유저 식별키로 조회
    public ChatRoomDto findById(Long chatRoomId, Long userId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new IllegalStateException("존재하지 않은 채팅 방 입니다")
        );

        // 판매 글의 유저 ID 아니고 && 구매 유저 ID도 아닌 경우
        if(chatRoom.getSellerId()!=userId && chatRoom.getUser().getUserId()!=userId ) {
            throw new IllegalStateException("다른 사용자의 채팅 방 입니다");
        }

        // 판매 글의 유저이지만 채팅방 나간 상태 또는 구매자 였지만 채팅 방 나간 상태
        if( (chatRoom.getUser().getUserId() == userId
                && chatRoom.getBuyerStatus().equals(ChatRoomStatus.DELETED))
        || (chatRoom.getSellerId() == userId
                && chatRoom.getSellerStatus().equals(ChatRoomStatus.DELETED)) ){
            throw new IllegalStateException("이미 나간 채팅 방 입니다");
        }

        return new ChatRoomDto().entityToChatRoomDto(chatRoom);

    }

    // 유저 식별키로 조회
    public List<ChatRoomDto> findByUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

                // 구매 유저이거나 판매 유저 둘 중 하나에 속한 채팅 방 조회
        return chatRoomRepository.findByUserOrSellerIdOrderByChatRoomIdDesc(user, userId).stream()
                // List<Comment> -> Stream 생성
                .map( chatRoom -> new ChatRoomDto().entityToChatRoomDto(chatRoom) ).collect(Collectors.toList());
                // map()은 중개연산 ( chatRoom -> dto 맵핑 연산 ) -> collect 스트림을 컬렉션으로 변환 toList

    }

    // 채팅방 목록을 조회하는 서비스
    public void chatList(ChatRoomDto chatRoomDto){
        User user = userRepository.findById(chatRoomDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        List<ChatRoom> chatRoomList =  chatRoomRepository
                .loginUserChatList(user, ChatRoomStatus.DELETED ,user.getUserId(), ChatRoomStatus.DELETED);

        // 실시간으로 로그인된 유저에게 채팅방 리스트를 최신화
        messagingTemplate.convertAndSend("/sub/chat/user/"+user.getUserId(),
                chatRoomList
                        .stream()
                        .map(chatRoom1 -> new ChatRoomDto().entityToChatRoomDto(chatRoom1))
                        .collect(Collectors.toList()
                        )
        );

    }
}
