package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class TcTemplateMessagePushQuery extends Pagination {

    /** 推送状态 1-未推送 2-推送中 3-推送成功 4-推送失败 5-推送被拒收 **/
    private Integer pushStatus;

    /** 防重uuid（MD5（推送类型_业务id_用户id_openId）） **/
    private String uuid;

    /** 微信消息id **/
    private String msgId;

}
