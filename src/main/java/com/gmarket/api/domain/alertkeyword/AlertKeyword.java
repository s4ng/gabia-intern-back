package com.gmarket.api.domain.alertkeyword;

import com.gmarket.api.domain.alertkeyword.dto.AlertKeywordDto;
import com.gmarket.api.domain.alertkeyword.enums.AlertKeywordStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AlertKeyword extends BaseTimeEntity {
    @Id // 엔티티 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB increment
    long alertKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private User user;

    private String keyword;

    private AlertKeywordStatus status;

    public void dtoToEntity(AlertKeywordDto alertKeywordDto){
        this.alertKeywordId = alertKeywordDto.getAlertKeywordId();
        this.keyword = alertKeywordDto.getKeyword();
        this.status = alertKeywordDto.getStatus();
    }

    public void createdSetting(User user){
        this.status = AlertKeywordStatus.CREATED;
        this.user = user;
    }

    public void deletedStatus(){
        this.status = AlertKeywordStatus.DELETED;
    }

    public void createdStatus(){
        this.status = AlertKeywordStatus.CREATED;
    }
}

