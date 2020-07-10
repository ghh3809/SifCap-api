package com.kotoumi.sifcapapi.model.vo.response;

import lombok.Data;

@Data
public class Response<T> {

    private int errcode;
    private String errorMsg;
    private T result;

    public Response() {

    }

    public Response(int errcode, String errorMsg) {
        this.errcode = errcode;
        this.errorMsg = errorMsg;
    }

}
