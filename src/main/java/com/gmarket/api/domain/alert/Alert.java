package com.gmarket.api.domain.alert;

import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Alert {
    @Id @GeneratedValue
    long alertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiverId;

    private String message;

    private boolean readOrNot;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private LocalDateTime createAt;

    enum AlertType {
        KEYWORD, RAFFLE
    }
}
