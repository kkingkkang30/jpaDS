package com.project.api.common.exception;

import lombok.Data;

@Data
public class Message {

    private String errorCode;
    private String errorMsg;

    public Message() {
        this.errorCode = null;
        this.errorMsg = null;
    }
}