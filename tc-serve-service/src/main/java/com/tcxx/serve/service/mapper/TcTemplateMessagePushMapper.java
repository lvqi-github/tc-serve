package com.tcxx.serve.service.mapper;

import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.query.TcTemplateMessagePushQuery;

import java.util.List;

public interface TcTemplateMessagePushMapper {

    int insert(TcTemplateMessagePush tcTemplateMessagePush);

    TcTemplateMessagePush getByUuid(TcTemplateMessagePushQuery query);

    int updatePushStatusSuccess(TcTemplateMessagePush tcTemplateMessagePush);

    int updatePushStatusFailed(TcTemplateMessagePush tcTemplateMessagePush);

    int deleteByPushId(TcTemplateMessagePush tcTemplateMessagePush);

    List<TcTemplateMessagePush> listNotPushTemplateMessage(TcTemplateMessagePushQuery query);

    List<TcTemplateMessagePush> listClearTemplateMessage(TcTemplateMessagePushQuery query);

    List<TcTemplateMessagePush> listByCondition(TcTemplateMessagePushQuery query);

    Integer countByCondition(TcTemplateMessagePushQuery query);

}
