package com.tcxx.serve.web.domain.admin.query;

import lombok.Data;

@Data
public class TemplateMessagePushListReqQuery {

    /** 推送状态 1-未推送 2-推送中 3-推送成功 4-推送失败 5-推送被拒收 **/
    private Integer pushStatus;

}
