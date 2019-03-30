package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcHitRecordService;
import com.tcxx.serve.service.entity.TcHitRecord;
import com.tcxx.serve.service.enumtype.HitRecordTypeEnum;
import com.tcxx.serve.service.query.TcHitRecordQuery;
import com.tcxx.serve.uid.utils.DateUtils;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.ball.resp.BallHitRecordDetailResp;
import com.tcxx.serve.web.domain.ball.resp.BallHitRecordListResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ball/statistic")
public class StatisticController {

    @Autowired
    private TcHitRecordService tcHitRecordService;

    @WeChatLoginTokenValidation
    @RequestMapping("/getHitRecordList")
    public Result<BallHitRecordListResp> getHitRecordList(Integer recordType, String authorId, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }
        if (recordType == null){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordType不能为空");
        }
        if (recordType.equals(HitRecordTypeEnum.AUTHOR.getType()) && StringUtils.isBlank(authorId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId不能为空");
        }

        //获取90天内的 记录数据
        TcHitRecordQuery query = new TcHitRecordQuery();
        query.setRecordDateStart(DateUtils.addDays(new Date(), -90));
        query.setRecordType(recordType);
        if (recordType.equals(HitRecordTypeEnum.AUTHOR.getType())){
            query.setAuthorId(authorId);
        }
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());

        Integer count = tcHitRecordService.countByCondition(query);
        List<TcHitRecord> hitRecordList = tcHitRecordService.listByCondition(query);

        List<BallHitRecordListResp> hitRecordListRespList= new ArrayList<>();
        hitRecordList.stream().forEach(bean -> {
            BallHitRecordListResp resp = new BallHitRecordListResp();
            resp.setRecordId(bean.getRecordId());
            resp.setRecordTitle(bean.getRecordTitle());
            resp.setCreated(bean.getCreated());
            hitRecordListRespList.add(resp);
        });

        Result<BallHitRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(hitRecordListRespList);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getHitRecordDetail")
    public Result<BallHitRecordDetailResp> getHitRecordDetail(String recordId) {
        if (StringUtils.isBlank(recordId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "recordId不能为空");
        }
        TcHitRecord tcHitRecord = tcHitRecordService.getByRecordId(recordId);

        BallHitRecordDetailResp resp = new BallHitRecordDetailResp();
        resp.setRecordId(tcHitRecord.getRecordId());
        resp.setRecordTitle(tcHitRecord.getRecordTitle());
        resp.setRecordImgUrl(tcHitRecord.getRecordImgUrl());
        resp.setCreated(tcHitRecord.getCreated());

        Result<BallHitRecordDetailResp> result = ResultBuild.wrapSuccess();
        result.setValue(resp);
        return result;
    }

}
