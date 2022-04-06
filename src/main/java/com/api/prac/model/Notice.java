package com.api.prac.model;

import com.api.common.Model.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Table(name="NOTICE")
@Entity
public class Notice extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "제목은 빈 값일 수 없습니다.")
    @Size(min=1, max = 10, message = "제목은 1 글자 이상 30 글자 이하이어야 합니다.")
    @Column(name="TITLE")
    private String title;

    @NotNull(message = "내용은 빈 값일 수 없습니다.")
    @Size(max = 100, message = "내용은 100 글자 이하이어야 합니다.")
    @Column(name="SBST")
    private String sbst;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="CRETRID")
    private String cretrId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="AMTID")
    private String amtId;

   /* @Column(name="CRETDT")
    private LocalDateTime cretDt;

    @Column(name="AMTDT")
    private LocalDateTime amtDt;*/
}
