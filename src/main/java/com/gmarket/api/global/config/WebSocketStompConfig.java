package com.gmarket.api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // stomp 사용을 위해 선언,  WebSocketMessageBrokerConfigurer 상속
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {


    @Override // MessageBroker 등록
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // MessageBroker -> MQ (Message Queue)
        // 메시지 기반의 미들웨어로 메시지를 이용하여 여러 어플리케이션, 시스템, 서비스들을 연결해주는 솔루션

        //in-memory message-broker, topic prefix 설정
        registry.enableSimpleBroker("/sub");


        // prefix mapping-message uri, 메세지를 수신하는 handler 메세지 prefix 설정
        registry.setApplicationDestinationPrefixes("/pub");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 프로토콜은 (simple text oriented messaging protocol)의 약자, 텍스트 기반

        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();

        // ws:(domain.com:8080)/ws-stomp
        // 웹 소켓에서 활용될 주소 "/ws-stomp"
        // setAllowedOriginPatterns("*") 옵션은 CORS(Cross Origin Resource Sharing) 문제
        // Websocket 사용할 수없는 경우 대체 전송을 사용할 수 있도록 SockJS 폴백 옵션을 활성화
        // 사용 가능한 최상의 전송 (websocket, xhr-streaming, xhr-polling 등)을 시도

    }
}