package com.project.api.util.FileUpload.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UploadFilePK implements Serializable {

    private Long fileSeq;
    private Long fileDtlSeq;
}
