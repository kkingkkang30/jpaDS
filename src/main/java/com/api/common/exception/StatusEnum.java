package com.api.common.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    VALID_CHECK("1001", "파라미터 유효성 체크");

    String code;
    String msg;

    StatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
