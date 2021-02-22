package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alertkeyword.AlertKeyword;
import com.gmarket.api.domain.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Getter @Setter
public class User {
    @Id @GeneratedValue
    private Long userId;

    private String nickname;

    private int activityPoint;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "receiverId", cascade = CascadeType.ALL)
    private List<Alert> alertList = new ArrayList<>();

    @OneToMany(mappedBy = "registerId", cascade = CascadeType.ALL)
    private List<AlertKeyword> alertKeywordList = new ArrayList<>();

    public void addBoard(Board b) {
        boardList.add(b);
    }
}
