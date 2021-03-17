package com.gmarket.api.domain.alertkeyword.dto;

import com.gmarket.api.domain.alertkeyword.AlertKeyword;
import com.gmarket.api.domain.alertkeyword.enums.AlertKeywordStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AlertKeywordDto {

    long alertKeywordId;

    private Long userId;

    private String keyword;

    private AlertKeywordStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public AlertKeywordDto entityToDto(AlertKeyword alertKeyword){
        this.alertKeywordId = alertKeyword.getAlertKeywordId();
        this.userId = alertKeyword.getUser().getUserId();
        this.keyword = alertKeyword.getKeyword();
        this.status = alertKeyword.getStatus();
        this.createdAt = alertKeyword.getCreatedAt();
        this.modifiedAt = alertKeyword.getModifiedAt();
        return this;
    }
}
