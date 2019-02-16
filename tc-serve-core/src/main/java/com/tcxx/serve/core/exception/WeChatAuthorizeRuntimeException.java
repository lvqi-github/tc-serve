package com.tcxx.serve.core.exception;

import com.tcxx.serve.core.result.ResultCodeEnum;

public class WeChatAuthorizeRuntimeException extends CustomRuntimeException {

    public WeChatAuthorizeRuntimeException(ResultCodeEnum code, Object... placeholder) {
        super(code, placeholder);
    }

}
