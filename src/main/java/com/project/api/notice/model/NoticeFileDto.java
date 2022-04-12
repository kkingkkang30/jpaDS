package com.project.api.notice.model;

public class NoticeFileDto {
    public Long seq;

    public String title;

    public String sbst;

    public Long fileDtlSeq;


    public NoticeFileDto(Long seq, String title, String sbst, Long fileDtlSeq) {
        this.seq = seq;
        this.title = title;
        this.sbst = sbst;
        this.fileDtlSeq = fileDtlSeq;
    }
}
