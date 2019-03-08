package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcAuthorService;
import com.tcxx.serve.service.TcHitRecordService;
import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.enumtype.HitRecordTypeEnum;
import com.tcxx.serve.service.query.TcHitRecordQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.HitRecordListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.HitRecordListResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/admin/hitRecord")
public class HitRecordController {

    @Autowired
    private TcHitRecordService tcHitRecordService;

    @Autowired
    private TcAuthorService tcAuthorService;

    @AdminLoginTokenValidation
    @RequestMapping("/getHitRecordTypeList")
    public Result<Map<String, Object>> getHitRecordTypeList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HitRecordTypeEnum hitRecordTypeEnum : HitRecordTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", hitRecordTypeEnum.getName());
            map.put("value", hitRecordTypeEnum.getType());
            list.add(map);
        }
        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValues(list);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getHitRecordList")
    public Result<HitRecordListResp> getHitRecordList(HitRecordListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcHitRecordQuery query = buildGetHitRecordListQuery(reqQuery, page);
        Integer count = tcHitRecordService.countByCondition(query);
        List<TcHitRecord> hitRecordList = tcHitRecordService.listByCondition(query);

        List<HitRecordListResp> hitRecordListResps = new ArrayList<>();
        hitRecordList.stream().forEach(bean -> {
            HitRecordListResp resp = new HitRecordListResp();
            resp.setRecordId(bean.getRecordId());
            resp.setRecordTitle(bean.getRecordTitle());
            resp.setRecordType(bean.getRecordType());
            resp.setRecordTypeName(HitRecordTypeEnum.getByType(bean.getRecordType()));
            resp.setRecordDate(bean.getRecordDate());
            resp.setAuthorName(bean.getAuthorName());
            resp.setRecordImgUrl(bean.getRecordImgUrl());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            hitRecordListResps.add(resp);
        });

        Result<HitRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(hitRecordListResps);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/hitRecordAdd", method = RequestMethod.POST)
    public Result<Boolean> hitRecordAdd(TcHitRecord tcHitRecord) {
        Result<Boolean> result = verifyHitRecordAdd(tcHitRecord);
        if (!result.getResultCode().equals("1000")){
            return result;
        }

        //设置作者名称
        if (tcHitRecord.getRecordType().equals(HitRecordTypeEnum.AUTHOR.getType())){
            tcHitRecord.setAuthorName(tcAuthorService.getByAuthorId(tcHitRecord.getAuthorId()).getAuthorName());
        }else {
            tcHitRecord.setAuthorId(null);
            tcHitRecord.setAuthorName(null);
        }

        boolean insert = tcHitRecordService.insert(tcHitRecord);
        if (!insert) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }
        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getByRecordId")
    public Result<TcHitRecord> getByRecordId(String recordId) {
        if (StringUtils.isBlank(recordId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordId不能为空");
        }
        TcHitRecord tcHitRecord = tcHitRecordService.getByRecordId(recordId);
        Result<TcHitRecord> result = ResultBuild.wrapSuccess();
        result.setValue(tcHitRecord);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/hitRecordUpdate", method = RequestMethod.POST)
    public Result<Boolean> hitRecordUpdate(TcHitRecord tcHitRecord) {
        Result<Boolean> result = verifyHitRecordAdd(tcHitRecord);
        if (!result.getResultCode().equals("1000")){
            return result;
        }
        if (StringUtils.isBlank(tcHitRecord.getRecordId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordId不能为空");
        }

        //设置作者名称
        if (tcHitRecord.getRecordType().equals(HitRecordTypeEnum.AUTHOR.getType())){
            tcHitRecord.setAuthorName(tcAuthorService.getByAuthorId(tcHitRecord.getAuthorId()).getAuthorName());
        }else {
            tcHitRecord.setAuthorId(null);
            tcHitRecord.setAuthorName(null);
        }

        boolean update = tcHitRecordService.update(tcHitRecord);
        if (!update) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }

        result.setValue(true);
        return result;
    }

    private Result<Boolean> verifyHitRecordAdd(TcHitRecord tcHitRecord) {
        if (Objects.isNull(tcHitRecord)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "请求参数不能为空");
        }
        if (StringUtils.isBlank(tcHitRecord.getRecordTitle())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordTitle无效");
        }
        if (tcHitRecord.getRecordType() == null || !HitRecordTypeEnum.contains(tcHitRecord.getRecordType())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordType无效");
        }
        if (Objects.isNull(tcHitRecord.getRecordDate())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordDate无效");
        }
        if (tcHitRecord.getRecordType().equals(HitRecordTypeEnum.AUTHOR.getType()) && StringUtils.isBlank(tcHitRecord.getAuthorId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId无效");
        }
        if (StringUtils.isBlank(tcHitRecord.getRecordImgUrl())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordImgUrl无效");
        }
        Result<Boolean> result = ResultBuild.wrapSuccess();
        return result;
    }

    private TcHitRecordQuery buildGetHitRecordListQuery(HitRecordListReqQuery reqQuery, Page page) {
        TcHitRecordQuery query = new TcHitRecordQuery();
        query.setRecordType(reqQuery.getRecordType());
        query.setAuthorId(reqQuery.getAuthorId());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
