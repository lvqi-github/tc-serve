package com.tcxx.serve.service.impl;

import com.tcxx.serve.service.TcTemplateMessagePushService;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.manager.TcTemplateMessagePushManager;
import com.tcxx.serve.service.query.TcTemplateMessagePushQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcTemplateMessagePushServiceImpl implements TcTemplateMessagePushService {

    @Autowired
    private TcTemplateMessagePushManager tcTemplateMessagePushManager;

    @Override
    public boolean updatePushStatusSuccess(String pushId, String msgId) {
        return tcTemplateMessagePushManager.updatePushStatusSuccess(pushId, msgId);
    }

    @Override
    public boolean updatePushStatusFailed(String pushId) {
        return tcTemplateMessagePushManager.updatePushStatusFailed(pushId);
    }

    @Override
    public boolean deleteByPushId(String pushId) {
        return tcTemplateMessagePushManager.deleteByPushId(pushId);
    }

    @Override
    public List<TcTemplateMessagePush> listNotPushTemplateMessage(Integer pageSize) {
        return tcTemplateMessagePushManager.listNotPushTemplateMessage(pageSize);
    }

    @Override
    public List<TcTemplateMessagePush> listClearTemplateMessage(Integer pageSize) {
        return tcTemplateMessagePushManager.listClearTemplateMessage(pageSize);
    }

    @Override
    public List<TcTemplateMessagePush> listByCondition(TcTemplateMessagePushQuery query) {
        return tcTemplateMessagePushManager.listByCondition(query);
    }

    @Override
    public Integer countByCondition(TcTemplateMessagePushQuery query) {
        return tcTemplateMessagePushManager.countByCondition(query);
    }
}
