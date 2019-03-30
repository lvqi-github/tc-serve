package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcMemberRechargeRecordService;
import com.tcxx.serve.service.entity.TcMemberRechargeRecord;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.MemberRechargeRecordListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.MemberRechargeRecordListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/memberRechargeRecord")
public class MemberRechargeRecordController {

    @Autowired
    private TcMemberRechargeRecordService tcMemberRechargeRecordService;

    @AdminLoginTokenValidation
    @RequestMapping("/getMemberRechargeRecordList")
    public Result<MemberRechargeRecordListResp> getMemberRechargeRecordList(MemberRechargeRecordListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcMemberRechargeRecordQuery query = buildGetMemberRechargeRecordListQuery(reqQuery, page);

        Integer count = tcMemberRechargeRecordService.countByCondition(query);
        List<TcMemberRechargeRecord> memberRechargeRecordList = tcMemberRechargeRecordService.listByCondition(query);

        List<MemberRechargeRecordListResp> memberRechargeRecordListResps = new ArrayList<>();
        memberRechargeRecordList.stream().forEach(bean -> {
            MemberRechargeRecordListResp resp = new MemberRechargeRecordListResp();
            resp.setRechargeRecordNo(bean.getRechargeRecordNo());
            resp.setRechargePackageId(bean.getRechargePackageId());
            resp.setRechargePackageDesc(bean.getRechargePackageDesc());
            resp.setRechargeAmount(bean.getRechargeAmount());
            resp.setDaysOfValidity(bean.getDaysOfValidity());
            resp.setValidityStartDate(bean.getValidityStartDate());
            resp.setValidityEndDate(bean.getValidityEndDate());
            resp.setUserId(bean.getUserId());
            resp.setRecordValidStatus(bean.getRecordValidStatus());
            resp.setRecordValidStatusName(ValidTypeEnum.getByType(bean.getRecordValidStatus()));
            resp.setOrderNo(bean.getOrderNo());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            memberRechargeRecordListResps.add(resp);
        });

        Result<MemberRechargeRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(memberRechargeRecordListResps);
        return result;
    }

    private TcMemberRechargeRecordQuery buildGetMemberRechargeRecordListQuery(MemberRechargeRecordListReqQuery reqQuery, Page page) {
        TcMemberRechargeRecordQuery query = new TcMemberRechargeRecordQuery();
        query.setRechargeRecordNo(reqQuery.getRechargeRecordNo());
        query.setUserId(reqQuery.getUserId());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
