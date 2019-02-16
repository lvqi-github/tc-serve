package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class JavaWebTokenVerifyRuntimeException extends CustomRuntimeException {

    public JavaWebTokenVerifyRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }

}
