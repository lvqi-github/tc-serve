package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.query.TcArticleQuery;

import java.util.List;

public interface TcArticleService {

    boolean insert(TcArticle tcArticle);

    boolean update(TcArticle tcArticle);

    TcArticle getByArticleId(String articleId);

    List<TcArticle> listByCondition(TcArticleQuery query);

    Integer countByCondition(TcArticleQuery query);

    boolean generatePushJob(String articleId);

}
