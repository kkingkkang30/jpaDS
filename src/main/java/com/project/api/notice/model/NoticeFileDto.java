package com.project.api.notice.model;

public class NoticeFileDto {
    public Long seq;

    public String title;

    public String sbst;

    public Long atcFileSeq;
    public Long fileDtlSeq;


    public NoticeFileDto(Long seq, String title, String sbst, Long atcFileSeq, Long fileDtlSeq) {
        this.seq = seq;
        this.title = title;
        this.sbst = sbst;
        this.atcFileSeq = atcFileSeq;
        this.fileDtlSeq = fileDtlSeq;
    }
}
