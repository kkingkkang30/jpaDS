package com.project.api.common.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    VALID_CHECK("1001", "파라미터 유효성 체크"),
    // file upload util
    NO_FILE_ATTACHED("2001", "파일이 없습니다"),
    OVER_FILE_MAX_SIZE("2002", "파일 용량 초과"),
    NO_EXTENSION_NAME("2003","파일 확장자 없음"),
    DUPLICATED_FILE("2004","파일 중복"),
    FAIL_TO_FILE_UPLOAD("2005", "파일 업로드 실패"),
    // excel
    OVER_MAX_RECORD("3001","레코드 갯수 초과");


    String code;
    String msg;

    StatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
