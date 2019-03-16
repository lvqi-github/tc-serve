package com.tcxx.serve.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TcTemplateMessagePush {

    /** 推送id **/
    private String pushId;

    /** 推送类型 1-文章订阅推送 **/
    private Integer pushType;

    /** 业务id **/
    private String businessId;

    /** 用户id **/
    private String userId;

    /** openId **/
    private String openId;

    /** 模板消息id **/
    private String templateId;

    /** 数据内容json **/
    private String dataContent;

    /** 推送状态 1-未推送 2-推送中 3-推送成功 4-推送失败 5-推送被拒收 **/
    private Integer pushStatus;

    /** 推送失败重试次数 **/
    private Integer failedRetryNumber;

    /** 防重uuid（MD5（推送类型_业务id_用户id_openId）） **/
    private String uuid;

    /** 微信消息id **/
    private String msgId;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;

}
