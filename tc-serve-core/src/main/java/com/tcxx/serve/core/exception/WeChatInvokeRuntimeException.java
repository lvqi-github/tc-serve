package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class WeChatInvokeRuntimeException extends CustomRuntimeException {

    public WeChatInvokeRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }

}
