package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.util.Date;

@Data
public class BallHitRecordListResp {

    /** 记录id **/
    private String recordId;

    /** 记录标题 **/
    private String recordTitle;

    /** 创建时间 **/
    private Date created;
}
