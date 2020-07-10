package com.kotoumi.sifcapapi.model.util;

import com.kotoumi.sifcapapi.constant.CustomExceptionEnum;

public class CustomException extends RuntimeException {

    private int code;

    public CustomException(String message) {
        super(message);
        code = -1;
    }

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(CustomExceptionEnum businessEnum, String... args) {
        super(businessEnum.getMessage(args));
        this.code = businessEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
