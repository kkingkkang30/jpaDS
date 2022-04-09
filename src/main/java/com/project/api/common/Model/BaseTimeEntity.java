package com.project.api.common.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속한 클래스가 컬럼으로 인식하도록
@EntityListeners(AuditingEntityListener.class) //auditing 기능 포함
public class BaseTimeEntity {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @CreatedDate
        @Column(name="CRET_DT")
        private LocalDateTime cretDt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Column(name="AMT_DT")
        @LastModifiedDate
        private LocalDateTime amtDt;
}
