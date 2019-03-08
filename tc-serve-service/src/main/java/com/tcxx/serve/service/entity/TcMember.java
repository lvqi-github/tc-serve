package com.tcxx.serve.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TcMember {

    /** 用户id **/
    private String userId;

    /** 首次会员开通时间**/
    private Date firstMemberOpenTime;

    /** 会员结束时间 **/
    private Date memberEndDate;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
