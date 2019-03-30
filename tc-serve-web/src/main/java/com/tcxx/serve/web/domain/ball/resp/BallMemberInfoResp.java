package com.tcxx.serve.web.domain.ball.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BallMemberInfoResp {

    /** 会员状态 1-未开通会员 2-会员已过期 3-会员有效 **/
    private Integer status;

    /** 会员结束时间 **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date memberEndDate;
}
