package com.tcxx.serve.service.manager.impl;

import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.enumtype.TemplateMessagePushStatusEnum;
import com.tcxx.serve.service.manager.TcTemplateMessagePushManager;
import com.tcxx.serve.service.mapper.TcTemplateMessagePushMapper;
import com.tcxx.serve.service.query.TcTemplateMessagePushQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TcTemplateMessagePushManagerImpl implements TcTemplateMessagePushManager {

    @Autowired
    private TcTemplateMessagePushMapper tcTemplateMessagePushMapper;

    @Override
    public boolean insert(TcTemplateMessagePush tcTemplateMessagePush) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        tcTemplateMessagePush.setPushId(uuid);
        tcTemplateMessagePush.setUuid(BusinessUuidGenerateUtil.getTcTemplateMessagePushUuid(tcTemplateMessagePush.getPushType(),
                tcTemplateMessagePush.getBusinessId(), tcTemplateMessagePush.getUserId(), tcTemplateMessagePush.getOpenId()));
        tcTemplateMessagePush.setPushStatus(TemplateMessagePushStatusEnum.NOT_PUSH.getStatus());
        tcTemplateMessagePush.setFailedRetryNumber(0);

        int i = tcTemplateMessagePushMapper.insert(tcTemplateMessagePush);
        return 1 == i;
    }

    @Override
    public TcTemplateMessagePush getByUuid(String uuid) {
        TcTemplateMessagePushQuery query = new TcTemplateMessagePushQuery();
        query.setUuid(uuid);
        return tcTemplateMessagePushMapper.getByUuid(query);
    }

    @Override
    public boolean updatePushStatusSuccess(String pushId, String msgId) {
        TcTemplateMessagePush tcTemplateMessagePush = new TcTemplateMessagePush();
        tcTemplateMessagePush.setPushId(pushId);
        tcTemplateMessagePush.setMsgId(msgId);
        return 1 == tcTemplateMessagePushMapper.updatePushStatusSuccess(tcTemplateMessagePush);
    }

    @Override
    public boolean updatePushStatusFailed(String pushId) {
        TcTemplateMessagePush tcTemplateMessagePush = new TcTemplateMessagePush();
        tcTemplateMessagePush.setPushId(pushId);
        return 1 == tcTemplateMessagePushMapper.updatePushStatusFailed(tcTemplateMessagePush);
    }

    @Override
    public boolean deleteByPushId(String pushId) {
        TcTemplateMessagePush tcTemplateMessagePush = new TcTemplateMessagePush();
        tcTemplateMessagePush.setPushId(pushId);
        return 1 == tcTemplateMessagePushMapper.deleteByPushId(tcTemplateMessagePush);
    }

    @Override
    public List<TcTemplateMessagePush> listNotPushTemplateMessage(Integer pageSize) {
        TcTemplateMessagePushQuery query = new TcTemplateMessagePushQuery();
        query.setPagingPageSize(pageSize);
        return tcTemplateMessagePushMapper.listNotPushTemplateMessage(query);
    }

    @Override
    public List<TcTemplateMessagePush> listClearTemplateMessage(Integer pageSize) {
        TcTemplateMessagePushQuery query = new TcTemplateMessagePushQuery();
        query.setPagingPageSize(pageSize);
        return tcTemplateMessagePushMapper.listClearTemplateMessage(query);
    }

    @Override
    public List<TcTemplateMessagePush> listByCondition(TcTemplateMessagePushQuery query) {
        return tcTemplateMessagePushMapper.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcTemplateMessagePushQuery query) {
        return tcTemplateMessagePushMapper.countByCondition(query);
    }
}
