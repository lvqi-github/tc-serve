package com.tcxx.serve.service.impl;

import com.alibaba.fastjson.JSON;
import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.enumtype.TemplateMessagePushStatusEnum;
import com.tcxx.serve.service.enumtype.TemplateMessagePushTypeEnum;
import com.tcxx.serve.service.manager.TcArticleManager;
import com.tcxx.serve.service.manager.TcTemplateMessagePushManager;
import com.tcxx.serve.service.manager.TcUserAuthorSubscribeManager;
import com.tcxx.serve.service.manager.TcUserManager;
import com.tcxx.serve.service.query.TcArticleQuery;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;
import com.tcxx.serve.wechat.model.message.template.TemplateData;
import com.tcxx.serve.wechat.model.message.template.TemplateMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class TcArticleServiceImpl implements TcArticleService {

    @Autowired
    private TcArticleManager tcArticleManager;

    @Autowired
    private TcUserAuthorSubscribeManager tcUserAuthorSubscribeManager;

    @Autowired
    private TcUserManager tcUserManager;

    @Autowired
    private TcTemplateMessagePushManager tcTemplateMessagePushManager;

    @Override
    public boolean insert(TcArticle tcArticle) {
        return tcArticleManager.insert(tcArticle);
    }

    @Override
    public boolean update(TcArticle tcArticle) {
        return tcArticleManager.update(tcArticle);
    }

    @Override
    public TcArticle getByArticleId(String articleId) {
        return tcArticleManager.getByArticleId(articleId);
    }

    @Override
    public List<TcArticle> listByCondition(TcArticleQuery query) {
        return tcArticleManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcArticleQuery query) {
        return tcArticleManager.countByCondition(query);
    }

    @Override
    public boolean generatePushJob(String articleId) {
        boolean result = true;
        try {
            // 获取文章信息
            TcArticle tcArticle = tcArticleManager.getByArticleId(articleId);
            // 获取文章所属作者的关注用户列表
            TcUserAuthorSubscribeQuery query = new TcUserAuthorSubscribeQuery();
            query.setAuthorId(tcArticle.getAuthorId());
            List<TcUserAuthorSubscribe> subscribeList = tcUserAuthorSubscribeManager.listAllByCondition(query);
            // 遍历生成推送任务
            for (TcUserAuthorSubscribe subscribe : subscribeList) {
                // 获取用户信息
                TcUser tcUser = tcUserManager.getByUserId(subscribe.getUserId());
                // 查询是否已存在任务
                String uuid = BusinessUuidGenerateUtil.getTcTemplateMessagePushUuid(TemplateMessagePushTypeEnum.ARTICLE_SUBSCRIBE_PUSH.getType(),
                        tcArticle.getAuthorId(), tcUser.getId(), tcUser.getOpenId());
                TcTemplateMessagePush templateMessagePush = tcTemplateMessagePushManager.getByUuid(uuid);
                if (Objects.isNull(templateMessagePush)) {
                    templateMessagePush = buildTcTemplateMessagePush(tcArticle, tcUser);
                    boolean b = tcTemplateMessagePushManager.insert(templateMessagePush);
                    if (!b){
                        result = false;
                        break;
                    }
                }
            }

            if (result){
                boolean b = tcArticleManager.updatePushJobStatusGenerated(articleId);
                if (!b){
                    result = false;
                }
            }
        } catch (Exception e) {
            log.error("TcArticleServiceImpl#generatePushJob error", e);
            result = false;
        }
        return result;
    }

    private TcTemplateMessagePush buildTcTemplateMessagePush(TcArticle tcArticle, TcUser tcUser) {
        TcTemplateMessagePush templateMessagePush = new TcTemplateMessagePush();
        templateMessagePush.setPushType(TemplateMessagePushTypeEnum.ARTICLE_SUBSCRIBE_PUSH.getType());
        templateMessagePush.setBusinessId(tcArticle.getArticleId());
        templateMessagePush.setUserId(tcUser.getId());
        templateMessagePush.setOpenId(tcUser.getOpenId());
        templateMessagePush.setTemplateId(TemplateMessagePushTypeEnum.ARTICLE_SUBSCRIBE_PUSH.getTemplateId());

        String json = buildDataContent(tcArticle, tcUser);

        templateMessagePush.setDataContent(json);
        return templateMessagePush;
    }

    private String buildDataContent(TcArticle tcArticle, TcUser tcUser) {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setToUser(tcUser.getOpenId());
        templateMessage.setTemplateId(TemplateMessagePushTypeEnum.ARTICLE_SUBSCRIBE_PUSH.getTemplateId());
        templateMessage.setUrl(TemplateMessagePushTypeEnum.ARTICLE_SUBSCRIBE_PUSH.getUrlPrefix() + tcArticle.getArticleId());

        Map<String, TemplateData> data = new HashMap<>();

        TemplateData firstData = new TemplateData();
        firstData.setValue(String.format("您好，您关注的作者「%s」发布了新的文章。", tcArticle.getAuthorName()));
        firstData.setColor("#FFA500");
        data.put("first", firstData);

        TemplateData titleData = new TemplateData();
        titleData.setValue(tcArticle.getArticleTitle());
        titleData.setColor("#FF0000");
        data.put("title", titleData);

        TemplateData releaseTimeData = new TemplateData();
        releaseTimeData.setValue(DateFormatUtils.format(tcArticle.getCreated(), "yyyy-MM-dd HH:mm:ss"));
        releaseTimeData.setColor("#0000FF");
        data.put("releaseTime", releaseTimeData);

        TemplateData remarkData = new TemplateData();
        remarkData.setValue("点击查看详情");
        remarkData.setColor("#0000FF");
        data.put("remark", remarkData);

        templateMessage.setData(data);
        return JSON.toJSONString(templateMessage);
    }
}
