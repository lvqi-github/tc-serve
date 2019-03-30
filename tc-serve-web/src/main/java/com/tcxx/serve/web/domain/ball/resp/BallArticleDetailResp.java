package com.tcxx.serve.web.domain.ball.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BallArticleDetailResp {

    /** 文章id **/
    private String articleId;

    /** 文章标题 **/
    private String articleTitle;

    /** 文章预览文字**/
    private String articlePreviewText;

    /** 文章预览图片 **/
    private String articlePreviewImg;

    /** 文章内容文字 **/
    private String articleContentText;

    /** 文字内容图片 **/
    private String articleContentImg;

    /** 价格 **/
    private BigDecimal price;

    /** 创建时间 **/
    private Date created;
}
