package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class HitRecordListReqQuery {

    /** 记录类型 1-按日期 2-按作者 **/
    private Integer recordType;

    /** 作者id **/
    private String authorId;

}
