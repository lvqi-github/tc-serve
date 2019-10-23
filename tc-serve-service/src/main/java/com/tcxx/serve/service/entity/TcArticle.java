package com.tcxx.serve.service.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TcArticle {

    /** 文章id **/
    private String articleId;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章描述 **/
    private String articleDesc;

    /** 文章类型：1-足球 2-篮球 3-足篮混合 **/
    private Integer articleType;

    /** 文章预览文字**/
    private String articlePreviewText;

    /** 文章预览图片 **/
    private String articlePreviewImg;

    /** 文章内容文字 **/
    private String articleContentText;

    /** 文字内容图片 **/
    private String articleContentImg;

    /** 作者id **/
    private String authorId;

    /** 作者名称 **/
    private String authorName;

    /** 收费类型：1-收费 2-公开 **/
    private Integer chargeType;

    /** 价格 **/
    private BigDecimal price;

    /** 发布状态：1-已发布 2-未发布 **/
    private Integer releaseStatus;

    /** 文章状态：1-未开始 2-进行中 3-已结束 **/
    private Integer articleStatus;

    /** 推送任务状态 1-未生成推送任务 2-已生成推送任务 **/
    private Integer pushJobStatus;

    /** 修改时间 **/
    private Date modified;

    /** 创建时间 **/
    private Date created;
}
