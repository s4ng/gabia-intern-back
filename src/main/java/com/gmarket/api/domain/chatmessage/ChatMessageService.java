package com.gmarket.api.domain.chatmessage;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.chatmessage.dto.ChatMessageDto;
import com.gmarket.api.domain.chatmessage.enums.ChatMessageType;
import com.gmarket.api.domain.chatroom.ChatRoom;
import com.gmarket.api.domain.chatroom.ChatRoomRepository;
import com.gmarket.api.domain.chatroom.ChatRoomService;
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
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final BoardRepositoryInterface boardRepositoryInterface;

    private final ChatRoomService chatRoomService;

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void messageCreate(ChatMessageDto chatMessageDto){
        User user = userRepository.findById(chatMessageDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 채팅방입니다" ));

        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            if(chatRoom.getBuyerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            if(chatRoom.getSellerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            chatRoom.buyerCount();
            chatRoom.sellerView();
            chatRoomRepository.save(chatRoom);

        }
        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            chatRoom.sellerCount();
            chatRoom.buyerView();
            chatRoomRepository.save(chatRoom);
        }

        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setUserId(chatRoom.getUser().getUserId());
        chatRoomService.chatList(chatRoomDto);
        chatRoomDto.setUserId(chatRoom.getSellerId());
        chatRoomService.chatList(chatRoomDto);

        ChatMessage chatMessage = new ChatMessage()
                .dtoToEntity(chatMessageDto).senderSetting(user).chatRoomSetting(chatRoom);

        chatMessageRepository.save(chatMessage);


        PageRequest pageRequest =
                PageRequest.of(0, 50, Sort.by( Sort.Direction.DESC, "chatMessageId") );


        List<ChatMessageDto> chatMessageDtoList = chatMessageRepository.findByChatRoom( chatRoom, pageRequest ).getContent()
                .stream()
                .map( chatMessage1 -> new ChatMessageDto().entityToChatMessageDto(chatMessage1))
                .collect(Collectors.toList());
        // Stream 생성 -> map()은 중개연산 ( comment -> dto 맵핑 연산 ) -> collect 스트림을 컬렉션으로 변환 toList

        Collections.reverse(chatMessageDtoList);

        messagingTemplate.convertAndSend("/sub/chat/room/"+chatRoom.getChatRoomId(),chatMessageDtoList);


    }

    @Transactional
    public void messageView(ChatMessageDto chatMessageDto){

        User user = userRepository.findById(chatMessageDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 채팅방입니다" ));

        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            if(chatRoom.getBuyerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            if(chatRoom.getSellerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            chatRoom.sellerView();
            chatRoomRepository.save(chatRoom);
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setUserId(chatRoom.getSellerId());
            chatRoomService.chatList(chatRoomDto);

        }

        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            chatRoom.buyerView();
            chatRoomRepository.save(chatRoom);
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setUserId(chatRoom.getUser().getUserId());
            chatRoomService.chatList(chatRoomDto);
        }

        PageRequest pageRequest =
                PageRequest.of(0, 50, Sort.by( Sort.Direction.DESC, "chatMessageId") );

        List<ChatMessageDto> chatMessageDtoList = chatMessageRepository.findByChatRoom( chatRoom, pageRequest ).getContent()
                .stream()
                .map( chatMessage1 -> new ChatMessageDto().entityToChatMessageDto(chatMessage1))
                .collect(Collectors.toList());
        // Stream 생성 -> map()은 중개연산 ( comment -> dto 맵핑 연산 ) -> collect 스트림을 컬렉션으로 변환 toList

        Collections.reverse(chatMessageDtoList);

        messagingTemplate.convertAndSend("/sub/chat/room/"+chatRoom.getChatRoomId(),chatMessageDtoList);

    }

    @Transactional
    public void messageLeave(ChatMessageDto chatMessageDto){

        User user = userRepository.findById(chatMessageDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 채팅방입니다" ));

        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            if(chatRoom.getBuyerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            if(chatRoom.getSellerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        ChatMessageDto chatMessageDto1 = new ChatMessageDto();
        chatMessageDto1.setUserId(user.getUserId());
        chatMessageDto1.setChatRoomId(chatMessageDto.getChatRoomId());
        chatMessageDto1.setChatMessageType(ChatMessageType.LEAVE);
        chatMessageDto1.setMessage(user.getName()+"님이 채팅방을 나가셨습니다.");
        messageCreate(chatMessageDto1);

        ChatRoomDto chatRoomDto = new ChatRoomDto();

        if(chatRoom.getSellerId().equals(user.getUserId())){
            chatRoom.sellerLeave();
            chatRoomRepository.save(chatRoom);
            chatRoomDto.setUserId(chatRoom.getSellerId());

        }
        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            chatRoom.buyerLeave();
            chatRoomRepository.save(chatRoom);
            chatRoomDto.setUserId(chatRoom.getUser().getUserId());
        }
        chatRoomDto.setChatRoomId(chatRoom.getChatRoomId());
        chatRoomService.chatList(chatRoomDto);
    }

    @Transactional
    public void dealClose(ChatMessageDto chatMessageDto){

        User user = userRepository.findById(chatMessageDto.getUserId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 유저 입니다" ));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException( "탈퇴한 유저 입니다" );
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId())
                .orElseThrow( () -> new IllegalStateException( "존재하지 않은 채팅방입니다" ));

        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
            if(chatRoom.getBuyerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        if(chatRoom.getSellerId().equals(user.getUserId())){
            if(chatRoom.getSellerStatus().equals(ChatRoomStatus.DELETED)){
                throw new IllegalStateException( "이미 나간 채팅 방입니다" );
            }
        }

        ChatMessageDto chatMessageDto1 = new ChatMessageDto();
        chatMessageDto1.setUserId(user.getUserId());
        chatMessageDto1.setChatRoomId(chatMessageDto.getChatRoomId());
        chatMessageDto1.setChatMessageType(ChatMessageType.CLOSE);
        chatMessageDto1.setMessage(user.getName()+"님과의 거래가 완료되었습니다.");
        messageCreate(chatMessageDto1);

        Board board = chatRoom.getBoard();
        board.closedStatus();
        boardRepositoryInterface.save(board);

        if(chatRoom.getSellerId().equals(user.getUserId())){
            chatRoom.completeDeal();
//            chatRoom.createName();
            chatRoomRepository.save(chatRoom);
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setUserId(chatRoom.getSellerId());
            chatRoomService.chatList(chatRoomDto);
        }

//        if(chatRoom.getUser().getUserId().equals(user.getUserId())){
//            chatRoom.completeDeal();
//            chatRoomRepository.save(chatRoom);
//            ChatRoomDto chatRoomDto = new ChatRoomDto();
//            chatRoomDto.setUserId(chatRoom.getUser().getUserId());
//            chatRoomService.chatList(chatRoomDto);
//        }

    }
}
