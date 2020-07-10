package com.kotoumi.sifcapapi.controller;

import com.alibaba.fastjson.JSON;
import com.kotoumi.sifcapapi.constant.CustomExceptionEnum;
import com.kotoumi.sifcapapi.constant.LogConstant;
import com.kotoumi.sifcapapi.model.util.CustomException;
import com.kotoumi.sifcapapi.model.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Exception handling
     *
     * @param exception 异常
     * @return response
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response handleException(Exception exception) {
        Response response = new Response();
        if (exception instanceof MethodArgumentNotValidException) {
            response.setErrcode(CustomExceptionEnum.ILLEGAL_PARAMETER.getCode());
            String errorMsg = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors()
                    .get(0).getDefaultMessage();
            response.setErrorMsg(CustomExceptionEnum.ILLEGAL_PARAMETER.getMessage(errorMsg));
        } else if (exception instanceof CustomException) {
            response.setErrcode(((CustomException) exception).getCode());
            response.setErrorMsg(exception.getMessage());
        } else {
            response.setErrcode(CustomExceptionEnum.SYSTEM_ERROR.getCode());
            response.setErrorMsg(CustomExceptionEnum.SYSTEM_ERROR.getMessage());
            log.error("system error stack trace: {}", exception.fillInStackTrace().toString());
        }
        MDC.put(LogConstant.LOG_RETURN_CODE_KEY, String.valueOf(response.getErrcode()));
        MDC.put(LogConstant.LOG_RETURN_MESSAGE_KEY, response.getErrorMsg());
        log.error("Response: {}", JSON.toJSONString(response));
        return response;
    }

}
