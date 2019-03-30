package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.util.Date;

@Data
public class BallHitRecordDetailResp {

    /** 记录id **/
    private String recordId;

    /** 记录标题 **/
    private String recordTitle;

    /** 记录图片url **/
    private String recordImgUrl;

    /** 创建时间 **/
    private Date created;
}
