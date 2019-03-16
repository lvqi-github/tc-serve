package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcTemplateMessagePushService;
import com.tcxx.serve.service.entity.TcTemplateMessagePush;
import com.tcxx.serve.service.enumtype.TemplateMessagePushStatusEnum;
import com.tcxx.serve.service.enumtype.TemplateMessagePushTypeEnum;
import com.tcxx.serve.service.query.TcTemplateMessagePushQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.TemplateMessagePushListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.TemplateMessagePushListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/admin/templateMessagePush")
public class TemplateMessagePushController {

    @Autowired
    private TcTemplateMessagePushService tcTemplateMessagePushService;

    @AdminLoginTokenValidation
    @RequestMapping("/getTemplateMessagePushStatusList")
    public Result<Map<String, Object>> getTemplateMessagePushStatusList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TemplateMessagePushStatusEnum pushStatusEnum : TemplateMessagePushStatusEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", pushStatusEnum.getName());
            map.put("value", pushStatusEnum.getStatus());
            list.add(map);
        }
        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValues(list);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getTemplateMessagePushList")
    public Result<TemplateMessagePushListResp> getTemplateMessagePushList(TemplateMessagePushListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcTemplateMessagePushQuery query = buildGetTemplateMessagePushListQuery(reqQuery, page);

        Integer count = tcTemplateMessagePushService.countByCondition(query);
        List<TcTemplateMessagePush> templateMessagePushList = tcTemplateMessagePushService.listByCondition(query);

        List<TemplateMessagePushListResp> templateMessagePushListResps = new ArrayList<>();
        templateMessagePushList.stream().forEach(bean -> {
            TemplateMessagePushListResp resp = new TemplateMessagePushListResp();
            resp.setPushId(bean.getPushId());
            resp.setPushType(bean.getPushType());
            resp.setPushTypeName(TemplateMessagePushTypeEnum.getByType(bean.getPushType()));
            resp.setBusinessId(bean.getBusinessId());
            resp.setUserId(bean.getUserId());
            resp.setOpenId(bean.getOpenId());
            resp.setPushStatus(bean.getPushStatus());
            resp.setPushStatusName(TemplateMessagePushStatusEnum.getByStatus(bean.getPushStatus()));
            resp.setFailedRetryNumber(bean.getFailedRetryNumber());
            resp.setUuid(bean.getUuid());
            resp.setMsgId(bean.getMsgId());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            templateMessagePushListResps.add(resp);
        });

        Result<TemplateMessagePushListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(templateMessagePushListResps);
        return result;
    }

    private TcTemplateMessagePushQuery buildGetTemplateMessagePushListQuery(TemplateMessagePushListReqQuery reqQuery, Page page) {
        TcTemplateMessagePushQuery query = new TcTemplateMessagePushQuery();
        query.setPushStatus(reqQuery.getPushStatus());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
