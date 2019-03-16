package com.tcxx.serve.service.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobStatusEnum {

    JOB_STATUS_RUNNING(1, "运行中"),
    JOB_STATUS_STOP(2, "停止");

    private int status;
    private String name;

}
