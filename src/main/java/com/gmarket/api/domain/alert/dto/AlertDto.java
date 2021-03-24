package com.gmarket.api.domain.alert.dto;

import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alert.enums.AlertStatus;
import com.gmarket.api.domain.alert.enums.AlertType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AlertDto {

    private Long alertId;

    private Long userId;

    private Long boardId;

    private String message;

    private AlertStatus status;

    private AlertType alertType;

    private LocalDateTime createdAt;

    public AlertDto entityToDto(Alert alert){
        this.alertId = alert.getAlertId();
        this.userId = alert.getUser().getUserId();
        this.boardId = alert.getBoard().getBoardId();
        this.message = alert.getMessage();
        this.status = alert.getStatus();
        this.alertType = alert.getAlertType();
        this.createdAt = alert.getCreatedAt();
        return this;
    }

}
