package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcMemberService;
import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.query.TcMemberQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.MemberListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.MemberListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/member")
public class MemberController {

    @Autowired
    private TcMemberService tcMemberService;

    @AdminLoginTokenValidation
    @RequestMapping("/getMemberList")
    public Result<MemberListResp> getMemberList(MemberListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcMemberQuery query = buildGetMemberListQuery(reqQuery, page);

        Integer count = tcMemberService.countByCondition(query);
        List<TcMember> memberList = tcMemberService.listByCondition(query);

        Date currentTime = new Date();

        List<MemberListResp> memberListResps = new ArrayList<>();
        memberList.stream().forEach(bean -> {
            MemberListResp resp = new MemberListResp();
            resp.setUserId(bean.getUserId());
            resp.setFirstMemberOpenTime(bean.getFirstMemberOpenTime());
            resp.setMemberEndDate(bean.getMemberEndDate());
            if (currentTime.after(bean.getMemberEndDate())){ //当前时间 在 会员结束时间 之后 代表已失效
                resp.setValid(ValidTypeEnum.INVALID.getType());
                resp.setValidName(ValidTypeEnum.INVALID.getName());
            }else {
                resp.setValid(ValidTypeEnum.VALID.getType());
                resp.setValidName(ValidTypeEnum.VALID.getName());
            }
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            memberListResps.add(resp);
        });

        Result<MemberListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(memberListResps);
        return result;
    }

    private TcMemberQuery buildGetMemberListQuery(MemberListReqQuery reqQuery, Page page) {
        TcMemberQuery query = new TcMemberQuery();
        query.setUserId(reqQuery.getUserId());
        query.setValid(reqQuery.getValid());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
