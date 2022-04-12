package com.project.api.common.exception;

import lombok.Getter;

@Getter
public class ApiRuntimeException extends RuntimeException{
    private String code;

    public ApiRuntimeException(){

    }

    public ApiRuntimeException(String errorCode, String message) {
        super(message);
        this.code= errorCode;
    }
}
