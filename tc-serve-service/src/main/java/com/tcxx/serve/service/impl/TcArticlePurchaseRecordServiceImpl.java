package com.tcxx.serve.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tcxx.serve.core.exception.DataBaseOperateRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.entity.TcOrder;
import com.tcxx.serve.service.manager.TcArticleManager;
import com.tcxx.serve.service.manager.TcArticlePurchaseRecordManager;
import com.tcxx.serve.service.manager.TcOrderManager;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TcArticlePurchaseRecordServiceImpl implements TcArticlePurchaseRecordService {

    @Autowired
    private TcArticleManager tcArticleManager;

    @Autowired
    private TcArticlePurchaseRecordManager tcArticlePurchaseRecordManager;

    @Autowired
    private TcOrderManager tcOrderManager;

    @Override
    @Transactional
    public void handlerArticlePurchase(TcOrder tcOrder, String transactionId, Date payFinishedTime) {
        // 购买的文章信息
        JSONObject jsonObject = JSONObject.parseObject(tcOrder.getBusinessData());
        TcArticle tcArticle = tcArticleManager.getByArticleId(jsonObject.getString("articleId"));

        // 写入文章购买记录
        TcArticlePurchaseRecord purchaseRecord = new TcArticlePurchaseRecord();
        purchaseRecord.setArticleId(tcArticle.getArticleId());
        purchaseRecord.setArticleTitle(tcArticle.getArticleTitle());
        purchaseRecord.setArticleDesc(tcArticle.getArticleDesc());
        purchaseRecord.setArticleReleaseTime(tcArticle.getCreated());
        purchaseRecord.setAmount(tcOrder.getOrderAmount());
        purchaseRecord.setUserId(tcOrder.getUserId());
        purchaseRecord.setOrderNo(tcOrder.getOrderNo());
        boolean insert = tcArticlePurchaseRecordManager.insert(purchaseRecord);
        if (!insert){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }

        // 更改订单状态
        boolean b = tcOrderManager.updateOrderPayFinished(tcOrder.getOrderNo(), transactionId, payFinishedTime);
        if (!b){
            throw new DataBaseOperateRuntimeException(ResultCodeEnum.ERROR2501);
        }
    }

    @Override
    public List<TcArticlePurchaseRecord> listByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticlePurchaseRecordQuery query) {
        return tcArticlePurchaseRecordManager.countByCondition(query);
    }
}
