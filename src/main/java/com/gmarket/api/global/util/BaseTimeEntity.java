package com.gmarket.api.global.util;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name="create_time")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="modify_time")
    private LocalDateTime modifiedAt;

    public LocalDateTime getCreatedTime() {
        return createdAt;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedAt;
    }
}
