package com.tcxx.serve.service.manager;

import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.query.TcArticleQuery;

import java.util.List;

public interface TcArticleManager {

    boolean insert(TcArticle tcArticle);

    boolean update(TcArticle tcArticle);

    TcArticle getByArticleId(String articleId);

    List<TcArticle> listByCondition(TcArticleQuery query);

    Integer countByCondition(TcArticleQuery query);

}
