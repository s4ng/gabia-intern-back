package com.gmarket.api.domain.user;

import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alertkeyword.AlertKeyword;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Getter
public class User {
    @Id @GeneratedValue
    private String userId;

    private String nickname;

    private int activityPoint;

    @OneToMany(mappedBy = "receiverId", cascade = CascadeType.ALL)
    private List<Alert> alertList = new ArrayList<>();

    @OneToMany(mappedBy = "registerId", cascade = CascadeType.ALL)
    private List<AlertKeyword> alertKeywordList = new ArrayList<>();

}
