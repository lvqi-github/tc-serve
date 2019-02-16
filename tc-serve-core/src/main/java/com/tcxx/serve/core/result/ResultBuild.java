package com.tcxx.serve.core.result;

import com.google.common.base.Preconditions;

public class ResultBuild {

    /**
     * 包装成功结果
     * @return
     */
    public static Result wrapSuccess() {
        return wrapResult(ResultCodeEnum.SUCCESS, "");
    }

    /**
     * 包装返回结果
     * @param resultCode
     * @param placeholder
     * @return
     */
    public static Result wrapResult(ResultCodeEnum resultCode, Object... placeholder) {
        Preconditions.checkNotNull(resultCode, "包装处理结果失败,结果码不能为空");
        Result result = Result.build();
        result.setResultCode(resultCode.getCode());
        result.setResultMsg(resultCode.buildMessage(placeholder));
        return result;
    }

    /**
     * 返回结果
     * @param resultCode
     * @param message
     * @return
     */
    public static Result returnResult(String resultCode, String message) {
        Result result = Result.build();
        result.setResultCode(resultCode);
        result.setResultMsg(message);
        return result;
    }

}
