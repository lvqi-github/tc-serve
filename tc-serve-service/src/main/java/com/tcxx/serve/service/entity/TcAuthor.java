package com.tcxx.serve.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TcAuthor {

    /** 作者id **/
    private String authorId;

    /** 作者名称 **/
    private String authorName;

    /** 平台来源 **/
    private String platformSource;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
