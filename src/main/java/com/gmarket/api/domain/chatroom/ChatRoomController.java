package com.gmarket.api.domain.chatroom;

import com.gmarket.api.domain.chatroom.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoomDto createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        return chatRoomService.createChatRoom(chatRoomDto);
    }
/*  http://localhost:8080/chat/room
    요청 정보
{
    "board_id": 1,
    "user_id": 2,
    "seller_id": 1
}
    반환 정보
    {
     "chat_room_id" :1,
     "board_id": 1,
     "user_id: 1,
     "seller_id: 2
     "seller_status": "CREATED",
     "buyer_status": "CREATED:
    }
* */


    // 특정 채팅방 조회
    @GetMapping("/room/{chatRoomId}")
    @ResponseBody
    public ChatRoomDto find(@PathVariable Long chatRoomId, Long userId) {
        return chatRoomService.findById(chatRoomId, userId);
    }
    // GetMapping -> 139.150.76.155/chat/room/1?userId=1



    // 사용자의 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDto> rooms(Long userId) {
        return chatRoomService.findByUser(userId);
    }
    // GetMapping -> 139.150.76.155/chat/rooms?userId=1


//    // 채팅방 입장 화면
//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/roomdetail";
//    }


    // 설정한 prefix 포함하면 /pub/chat/user
    // MessageMapping 실시간으로 알림을 읽었다는 내용을 FrontEnd 측에서 요청할 url 매핑
    @MessageMapping("/chat/user")
    public void chatList(ChatRoomDto chatRoomDto){
        chatRoomService.chatList(chatRoomDto);
    }

}