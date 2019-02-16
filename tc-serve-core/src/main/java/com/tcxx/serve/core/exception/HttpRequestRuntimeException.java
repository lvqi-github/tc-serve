package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class HttpRequestRuntimeException extends CustomRuntimeException {

    public HttpRequestRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }

}
