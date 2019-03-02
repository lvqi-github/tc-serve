package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class QiNiuRuntimeException extends CustomRuntimeException {

    public QiNiuRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }
}
