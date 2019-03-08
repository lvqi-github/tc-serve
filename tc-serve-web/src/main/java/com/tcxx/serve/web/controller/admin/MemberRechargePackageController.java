package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcMemberRechargePackageService;
import com.tcxx.serve.service.entity.TcMemberRechargePackage;
import com.tcxx.serve.service.enumtype.EnableStatusEnum;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.resp.MemberRechargePackageListResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/memberRechargePackage")
public class MemberRechargePackageController {

    @Autowired
    private TcMemberRechargePackageService tcMemberRechargePackageService;

    @AdminLoginTokenValidation
    @RequestMapping("/getMemberRechargePackageList")
    public Result<MemberRechargePackageListResp> getMemberRechargePackageList(Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        Integer count = tcMemberRechargePackageService.countAll();
        List<TcMemberRechargePackage> memberRechargePackageList = tcMemberRechargePackageService.listAllPage(page.getPage(), page.getPageSize());

        List<MemberRechargePackageListResp> memberRechargePackageListResps = new ArrayList<>();
        memberRechargePackageList.stream().forEach(bean -> {
            MemberRechargePackageListResp resp = new MemberRechargePackageListResp();
            resp.setPackageId(bean.getPackageId());
            resp.setPackageDesc(bean.getPackageDesc());
            resp.setDaysOfValidity(bean.getDaysOfValidity());
            resp.setPrice(bean.getPrice());
            resp.setEnableStatus(bean.getEnableStatus());
            resp.setEnableStatusName(EnableStatusEnum.getByStatus(bean.getEnableStatus()));
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            memberRechargePackageListResps.add(resp);
        });

        Result<MemberRechargePackageListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(memberRechargePackageListResps);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/memberRechargePackageAdd", method = RequestMethod.POST)
    public Result<Boolean> memberRechargePackageAdd(TcMemberRechargePackage tcMemberRechargePackage) {
        Result<Boolean> result = verifyMemberRechargePackageAdd(tcMemberRechargePackage);
        if (!result.getResultCode().equals("1000")){
            return result;
        }

        boolean insert = tcMemberRechargePackageService.insert(tcMemberRechargePackage);
        if (!insert) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }

        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getByPackageId")
    public Result<TcMemberRechargePackage> getByPackageId(String packageId) {
        if (StringUtils.isBlank(packageId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "packageId不能为空");
        }
        TcMemberRechargePackage tcMemberRechargePackage = tcMemberRechargePackageService.getByPackageId(packageId);
        Result<TcMemberRechargePackage> result = ResultBuild.wrapSuccess();
        result.setValue(tcMemberRechargePackage);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/memberRechargePackageUpdate", method = RequestMethod.POST)
    public Result<Boolean> memberRechargePackageUpdate(TcMemberRechargePackage tcMemberRechargePackage) {
        Result<Boolean> result = verifyMemberRechargePackageAdd(tcMemberRechargePackage);
        if (!result.getResultCode().equals("1000")){
            return result;
        }

        if (StringUtils.isBlank(tcMemberRechargePackage.getPackageId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "packageId不能为空");
        }

        boolean update = tcMemberRechargePackageService.update(tcMemberRechargePackage);
        if (!update) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }

        result.setValue(true);
        return result;
    }

    private Result<Boolean> verifyMemberRechargePackageAdd(TcMemberRechargePackage tcMemberRechargePackage) {
        if (Objects.isNull(tcMemberRechargePackage)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "请求参数不能为空");
        }
        if (StringUtils.isBlank(tcMemberRechargePackage.getPackageDesc())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "packageDesc无效");
        }
        if (Objects.isNull(tcMemberRechargePackage.getDaysOfValidity())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "daysOfValidity无效");
        }
        if (Objects.isNull(tcMemberRechargePackage.getPrice())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pricey无效");
        }
        if (tcMemberRechargePackage.getEnableStatus() == null || !EnableStatusEnum.contains(tcMemberRechargePackage.getEnableStatus())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "enableStatus无效");
        }
        Result<Boolean> result = ResultBuild.wrapSuccess();
        return result;
    }

}
