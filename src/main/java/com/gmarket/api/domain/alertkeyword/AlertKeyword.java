package com.gmarket.api.domain.alertkeyword;

import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AlertKeyword {
    @Id
    @GeneratedValue
    long alertKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private User registerId;

    private String keyword;
}
