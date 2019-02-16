package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class DataBaseOperateRuntimeException extends CustomRuntimeException {

    public DataBaseOperateRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }

}
