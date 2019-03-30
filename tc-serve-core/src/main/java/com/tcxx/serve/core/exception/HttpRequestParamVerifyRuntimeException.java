package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class HttpRequestParamVerifyRuntimeException extends CustomRuntimeException {
    public HttpRequestParamVerifyRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }
}
