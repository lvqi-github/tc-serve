package com.tcxx.serve.service.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TcHitRecord {

    /** 记录id **/
    private String recordId;

    /** 记录标题 **/
    private String recordTitle;

    /** 记录类型 1-按日期 2-按作者 **/
    private Integer recordType;

    /** 记录日期 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date recordDate;

    /** 作者id **/
    private String authorId;

    /** 作者名称 **/
    private String authorName;

    /** 记录图片url **/
    private String recordImgUrl;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
