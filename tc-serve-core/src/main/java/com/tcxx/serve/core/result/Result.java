package com.tcxx.serve.core.result;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor(staticName = "build")
public class Result<T> {

    /** 结果码 */
    private String resultCode;

    /** ExecuteResult Message */
    private String resultMsg;

    private T value; // 非列表数据实体

    private int totalElements = 0; //列表数据总条数

    private List<T> values; // 列表数据
}
