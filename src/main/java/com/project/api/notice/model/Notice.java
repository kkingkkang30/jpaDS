package com.project.api.notice.model;

import com.project.api.common.Model.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Table(name="NOTICE")
@Entity
public class Notice extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    @NotNull(message = "제목은 빈 값일 수 없습니다.")
    @Size(min=1, max = 10, message = "제목은 1 글자 이상 30 글자 이하이어야 합니다.")
    @Column(name="TITLE")
    private String title;

    @NotNull(message = "내용은 빈 값일 수 없습니다.")
    @Size(max = 100, message = "내용은 100 글자 이하이어야 합니다.")
    @Column(name="SBST")
    private String sbst;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="CRETR_ID")
    private String cretrId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="AMT_ID")
    private String amtId;

    @Column(name = "ATC_FILE_SEQ")
    private Long atcFileSeq;

    //private List<MultipartFile> multipartFileList;
   /* @Column(name="CRETDT")
    private LocalDateTime cretDt;

    @Column(name="AMTDT")
    private LocalDateTime amtDt;*/
}
