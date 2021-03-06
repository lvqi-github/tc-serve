package com.tcxx.serve.service.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TcUser {

    /** 用户id **/
    private String id;

    /** 微信用户昵称 **/
    private String nickName;

    /** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效 **/
    private String headImgUrl;

    /** 微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 **/
    private Integer sex;

    /** 用户个人资料填写的省份 **/
    private String province;

    /** 普通用户个人资料填写的城市 **/
    private String city;

    /** 国家，如中国为CN **/
    private String country;

    /** 微信用户id 只针对同一公众号 同一用户在不同的公众号openId不同 此情况使用unionId **/
    private String openId;

    /** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段 **/
    private String unionId;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
