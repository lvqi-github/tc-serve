package com.tcxx.serve.web.handler;

import com.tcxx.serve.core.exception.CustomRuntimeException;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CustomRuntimeException.class)
    public Result handleException(CustomRuntimeException e) {
        Result result = ResultBuild.returnResult(e.getCode().getCode(), e.getMessage());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("error", e);
        Result result = ResultBuild.wrapResult(ResultCodeEnum.ERROR);
        return result;
    }

}
