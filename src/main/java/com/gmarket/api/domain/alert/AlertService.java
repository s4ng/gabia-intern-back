package com.gmarket.api.domain.alert;

import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public void alertYetList(AlertDto alertDto){

        if(alertDto.getUserId() == null ){
            throw new IllegalStateException( "로그인 한 이후부터 알림이 가능합니다" );
        }

        User user = userRepository.findById( alertDto.getUserId() )
                .orElseThrow( ()-> new IllegalStateException( "존재하지 않는 사용자 입니다" ));

        if( user.getStatus().equals(UserStatus.DELETED )){
            throw new IllegalStateException( "이미 탈퇴한 유저 입니다" );
        }

        if(alertDto.getStatus().equals(AlertStatus.READ)){
            alertRepository.updateStatus(user);
        }

        List<Alert> alertList = alertRepository.findByUserOrderByAlertIdDesc(user);

        messagingTemplate.convertAndSend("/sub/alert/"+String.valueOf(alertDto.getUserId())
                        , alertList
                        .stream()
                        .map( alert -> new AlertDto().entityToDto(alert))
                        .collect(Collectors.toList()));

    }
}
