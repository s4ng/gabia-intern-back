package com.gmarket.api.domain.chatmessage;

import com.gmarket.api.domain.chatmessage.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/room")
    public void messageCreate(ChatMessageDto chatMessageDto){

        chatMessageService.messageCreate(chatMessageDto);
    }

    @MessageMapping("/chat/room/start")
    public void messageView(ChatMessageDto chatMessageDto){
        chatMessageService.messageView(chatMessageDto);
    }

    @MessageMapping("/chat/room/leave")
    public void messageLeave(ChatMessageDto chatMessageDto){
        chatMessageService.messageLeave(chatMessageDto);
    }

    @MessageMapping("/chat/room/close")
    public void dealClose(ChatMessageDto chatMessageDto){
        chatMessageService.dealClose(chatMessageDto);
    }
}
