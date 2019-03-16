package com.tcxx.serve.service;

import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.query.TcTemplateMessagePushQuery;

import java.util.List;

public interface TcTemplateMessagePushService {

    boolean updatePushStatusSuccess(String pushId, String msgId);

    boolean updatePushStatusFailed(String pushId);

    boolean deleteByPushId(String pushId);

    List<TcTemplateMessagePush> listNotPushTemplateMessage(Integer pageSize);

    List<TcTemplateMessagePush> listClearTemplateMessage(Integer pageSize);

    List<TcTemplateMessagePush> listByCondition(TcTemplateMessagePushQuery query);

    Integer countByCondition(TcTemplateMessagePushQuery query);

}
