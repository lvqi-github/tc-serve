package com.tcxx.serve.web.domain.admin.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HitRecordListResp {

    /** 记录id **/
    private String recordId;

    /** 记录标题 **/
    private String recordTitle;

    /** 记录类型 1-按日期 2-按作者 **/
    private Integer recordType;

    /** 记录类型名称 **/
    private String recordTypeName;

    /** 记录日期 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date recordDate;

    /** 作者名称 **/
    private String authorName;

    /** 记录图片url **/
    private String recordImgUrl;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
