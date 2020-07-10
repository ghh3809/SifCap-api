package com.kotoumi.sifcapapi.controller;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.constant.LogConstant;
import com.kotoumi.sifcapapi.model.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class BaseController {

    Response ok() {
        Response res = new Response();
        initialResponse(res);
        log.info("Response: {}", JSON.toJSONString(res));
        return res;
    }

    <T> Response<T> finish(T result) {
        Response<T> res = new Response<>();
        initialResponse(res);
        res.setResult(result);
        log.info("Response: {}", JSON.toJSONString(res));
        return res;
    }

    private void initialResponse(Response res) {
        res.setErrcode(0);
        res.setErrorMsg("ok");
        MDC.put(LogConstant.LOG_RETURN_CODE_KEY, "0");
        MDC.put(LogConstant.LOG_RETURN_MESSAGE_KEY, "ok");
    }

}
