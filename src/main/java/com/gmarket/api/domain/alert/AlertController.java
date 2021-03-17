package com.gmarket.api.domain.alert;

import com.gmarket.api.domain.alert.dto.AlertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    // 설정한 prefix 포함하면 /pub/alert/user
    // MessageMapping 실시간으로 알림을 읽었다는 내용을 FrontEnd 측에서 요청할 url 매핑
    @MessageMapping("/alert/user")
    public void alertYetList(AlertDto alertDto){
        alertService.alertYetList(alertDto);
    }

}