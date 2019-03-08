package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.entity.TcArticlePurchaseRecord;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.ArticlePurchaseRecordListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.ArticlePurchaseRecordListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/articlePurchaseRecord")
public class ArticlePurchaseRecordController {

    @Autowired
    private TcArticlePurchaseRecordService tcArticlePurchaseRecordService;

    @AdminLoginTokenValidation
    @RequestMapping("/getArticlePurchaseRecordList")
    public Result<ArticlePurchaseRecordListResp> getArticlePurchaseRecordList(ArticlePurchaseRecordListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcArticlePurchaseRecordQuery query = buildGetArticlePurchaseRecordListQuery(reqQuery, page);

        Integer count = tcArticlePurchaseRecordService.countByCondition(query);
        List<TcArticlePurchaseRecord> articlePurchaseRecordList = tcArticlePurchaseRecordService.listByCondition(query);

        List<ArticlePurchaseRecordListResp> articlePurchaseRecordListResps = new ArrayList<>();
        articlePurchaseRecordList.stream().forEach(bean -> {
            ArticlePurchaseRecordListResp resp = new ArticlePurchaseRecordListResp();
            resp.setPurchaseRecordNo(bean.getPurchaseRecordNo());
            resp.setArticleId(bean.getArticleId());
            resp.setArticleTitle(bean.getArticleTitle());
            resp.setArticleDesc(bean.getArticleDesc());
            resp.setArticleReleaseTime(bean.getArticleReleaseTime());
            resp.setAmount(bean.getAmount());
            resp.setUserId(bean.getUserId());
            resp.setRecordValidStatus(bean.getRecordValidStatus());
            resp.setRecordValidStatusName(ValidTypeEnum.getByType(bean.getRecordValidStatus()));
            resp.setOrderNo(bean.getOrderNo());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            articlePurchaseRecordListResps.add(resp);
        });

        Result<ArticlePurchaseRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(articlePurchaseRecordListResps);
        return result;
    }

    private TcArticlePurchaseRecordQuery buildGetArticlePurchaseRecordListQuery(ArticlePurchaseRecordListReqQuery reqQuery, Page page) {
        TcArticlePurchaseRecordQuery query = new TcArticlePurchaseRecordQuery();
        query.setPurchaseRecordNo(reqQuery.getPurchaseRecordNo());
        query.setUserId(reqQuery.getUserId());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
