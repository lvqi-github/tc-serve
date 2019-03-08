package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class MemberListReqQuery {

    /** 用户id **/
    private String userId;

    /** 用于列表查询项 会员是否有效 1-有效 2-无效 **/
    private Integer valid;

}
