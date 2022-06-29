package com.its.memberboardproject.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime; //새로 등록된 시간을 체크, update쿼리가 진행됐을 때 값은 false

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedTime; //업데이트 등록 시간을 체크, insert쿼리가 진행됐을 때 값은 false
}
