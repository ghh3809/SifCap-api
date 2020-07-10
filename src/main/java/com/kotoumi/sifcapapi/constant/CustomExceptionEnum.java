package com.kotoumi.sifcapapi.constant;

public enum CustomExceptionEnum {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(282000, "系统内部错误"),
    /**
     * 参数错误
     */
    ILLEGAL_PARAMETER(282004, "参数[%s]非法或缺失");

    private final int code;
    private final String message;

    CustomExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(String... placeHolder) {
        return String.format(message, placeHolder);
    }

}
