package com.gmarket.api.domain.alert;

import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertStatus;
import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Alert extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String message;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private BoardType boardType;

    public Alert dtoToEntity(AlertDto alertDto){
        this.alertId = alertDto.getAlertId();
        this.message = alertDto.getMessage();
        this.status = alertDto.getStatus();
        this.alertType = alertDto.getAlertType();
        this.boardType = alertDto.getBoardType();
        return this;
    }

    public Alert createAlert(User user, Board board, String message, AlertType alertType,BoardType boardType){
        this.user = user;
        this.board = board;
        this.message = message;
        this.status = AlertStatus.YET;
        this.alertType = alertType;
        this.boardType = boardType;
        return this;
    }

    public void alertUpdate(String message){
        this.message = message;
        this.status = AlertStatus.YET;
    }

    public Alert createAlert(User user, Board board, String message, AlertType alertType) {
        this.user = user;
        this.board = board;
        this.message = message;
        this.alertType = alertType;
        this.boardType = board.getBoardType();
        this.status = AlertStatus.YET;
        return this;
    }
}
