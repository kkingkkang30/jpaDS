package com.project.api.util.FileUpload.model;

import com.project.api.common.Model.BaseTimeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name="ATC_FILE_BAS")
@Entity
@NoArgsConstructor
@IdClass(UploadFilePK.class)
public class UploadFileVO extends BaseTimeEntity {

    @Id
    @Column(name="FILE_SEQ")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long fileSeq;

    @Id
    @Column(name="FILE_DTL_SEQ")
    private Long fileDtlSeq;

    @Column(name="CRETR_ID")
    private String cretrId;

    @Column(name="AMT_ID")
    private String amtId;

    @Column(name="atc_file_path_nm")
    private String atcFilePathNm;

    @Column(name="store_file_nm")
    private String storeFileNm;
    @Column(name="origin_file_nm")
    private String originFileNm;
    @Column(name="file_ext_nm")
    private String fileExtNm;
    @Column(name="atc_file_size")
    private long atcFileSize;

    @Builder
    public UploadFileVO(long fileSeq, long fileDtlSeq, String cretrId, String amtId, String atcFilePathNm, String storeFileNm,
                        String originFileNm, String fileExtNm, long atcFileSize){

        this.fileSeq = fileSeq;
        this.fileDtlSeq = fileDtlSeq;
        this.cretrId = cretrId;
        this.amtId = amtId;
        this.atcFilePathNm = atcFilePathNm;
        this.storeFileNm =storeFileNm;
        this.originFileNm = originFileNm;
        this.fileExtNm = fileExtNm;
        this.atcFileSize = atcFileSize;
    }


    @Override
    public String toString() {
        return "UploadFileVO{" +
                "fileSeq=" + fileSeq +
                ", cretrId='" + cretrId + '\'' +
                ", amtId='" + amtId + '\'' +
                ", atcFilePathNm='" + atcFilePathNm + '\'' +
                ", storeFileNm='" + storeFileNm + '\'' +
                ", originFileNm='" + originFileNm + '\'' +
                ", fileExtNm='" + fileExtNm + '\'' +
                ", atcFileSize=" + atcFileSize +
                '}';
    }
}