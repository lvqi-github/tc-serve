package com.tcxx.serve.service.query;

import lombok.Data;

import java.util.Date;

@Data
public class TcHitRecordQuery extends Pagination {

    /** 记录id **/
    private String recordId;

    /** 记录类型 1-按日期 2-按作者 **/
    private Integer recordType;

    /** 作者id **/
    private String authorId;

    private Date recordDateStart;
}
