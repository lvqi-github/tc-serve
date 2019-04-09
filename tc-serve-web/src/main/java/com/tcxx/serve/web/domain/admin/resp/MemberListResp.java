package com.tcxx.serve.web.domain.admin.resp;

import lombok.Data;

import java.util.Date;

@Data
public class MemberListResp {

    /** 用户id **/
    private String userId;

    /** 首次会员开通时间**/
    private Date firstMemberOpenTime;

    /** 会员结束时间 **/
    private Date memberEndDate;

    /** 会员是否有效 1-有效 2-无效 **/
    private Integer valid;

    /** 会员是否有效 **/
    private String validName;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
