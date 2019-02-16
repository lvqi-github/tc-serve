package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;
import lombok.Getter;

public class CustomRuntimeException extends RuntimeException {

    @Getter
    private ResultCodeEnum code;

    public CustomRuntimeException (ResultCodeEnum code, Object... placeholder) {
        super(ResultCodeEnum.buildMessage(code, placeholder));
        this.code = code;
    }

}
