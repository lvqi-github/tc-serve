package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.TcAuthorService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.enumtype.*;
import com.tcxx.serve.service.query.TcArticleQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.ArticleListReqQuery;
import com.tcxx.serve.web.domain.admin.resp.ArticleListResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private TcArticleService tcArticleService;

    @Autowired
    private TcAuthorService tcAuthorService;

    @AdminLoginTokenValidation
    @RequestMapping("/getArticleList")
    public Result<ArticleListResp> getArticleList(ArticleListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcArticleQuery query = buildGetArticleListQuery(reqQuery, page);
        Integer count = tcArticleService.countByCondition(query);
        List<TcArticle> articleList = tcArticleService.listByCondition(query);

        List<ArticleListResp> articleListResps = new ArrayList<>();
        articleList.stream().forEach(bean -> {
            ArticleListResp resp = new ArticleListResp();
            resp.setArticleId(bean.getArticleId());
            resp.setArticleTitle(bean.getArticleTitle());
            resp.setArticleDesc(bean.getArticleDesc());
            resp.setArticleType(bean.getArticleType());
            resp.setArticleTypeName(ArticleTypeEnum.getByType(bean.getArticleType()));
            resp.setAuthorName(bean.getAuthorName());
            resp.setChargeType(bean.getChargeType());
            resp.setChargeTypeName(ArticleChargeTypeEnum.getByType(bean.getChargeType()));
            resp.setPrice(bean.getPrice());
            resp.setReleaseStatus(bean.getReleaseStatus());
            resp.setReleaseStatusName(ArticleReleaseStatusEnum.getByStatus(bean.getReleaseStatus()));
            resp.setArticleStatus(bean.getArticleStatus());
            resp.setArticleStatusName(ArticleStatusEnum.getByStatus(bean.getArticleStatus()));
            resp.setPushJobStatus(bean.getPushJobStatus());
            resp.setPushJobStatusName(ArticlePushJobStatusEnum.getByStatus(bean.getPushJobStatus()));
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            articleListResps.add(resp);
        });

        Result<ArticleListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(articleListResps);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/articleGeneratePushJob", method = RequestMethod.POST)
    public Result<Boolean> articleGeneratePushJob(String articleId) {
        if (StringUtils.isBlank(articleId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId不能为空");
        }

        boolean generateRes = tcArticleService.generatePushJob(articleId);
        if (!generateRes) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR10001);
        }
        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/articleAdd", method = RequestMethod.POST)
    public Result<Boolean> articleAdd(TcArticle tcArticle) {
        Result<Boolean> result = verifyArticleAdd(tcArticle);
        if (!result.getResultCode().equals("1000")){
            return result;
        }
        //设置作者名称
        tcArticle.setAuthorName(tcAuthorService.getByAuthorId(tcArticle.getAuthorId()).getAuthorName());

        boolean insert = tcArticleService.insert(tcArticle);
        if (!insert) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }
        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getByArticleId")
    public Result<TcArticle> getByArticleId(String articleId) {
        if (StringUtils.isBlank(articleId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId不能为空");
        }
        TcArticle tcArticle = tcArticleService.getByArticleId(articleId);
        Result<TcArticle> result = ResultBuild.wrapSuccess();
        result.setValue(tcArticle);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/articleUpdate", method = RequestMethod.POST)
    public Result<Boolean> articleUpdate(TcArticle tcArticle) {
        Result<Boolean> result = verifyArticleAdd(tcArticle);
        if (!result.getResultCode().equals("1000")){
            return result;
        }
        if (StringUtils.isBlank(tcArticle.getArticleId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId不能为空");
        }

        //设置作者名称
        tcArticle.setAuthorName(tcAuthorService.getByAuthorId(tcArticle.getAuthorId()).getAuthorName());

        boolean update = tcArticleService.update(tcArticle);
        if (!update) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }

        result.setValue(true);
        return result;
    }

    private Result<Boolean> verifyArticleAdd(TcArticle tcArticle) {
        if (Objects.isNull(tcArticle)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "请求参数不能为空");
        }
        if (StringUtils.isBlank(tcArticle.getArticleTitle())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleTitle无效");
        }
        if (StringUtils.isBlank(tcArticle.getArticleDesc())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleDesc无效");
        }
        if (tcArticle.getArticleType() == null || !ArticleTypeEnum.contains(tcArticle.getArticleType())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleType无效");
        }
        if (StringUtils.isBlank(tcArticle.getAuthorId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId无效");
        }
        if (tcArticle.getChargeType() == null || !ArticleChargeTypeEnum.contains(tcArticle.getChargeType())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "chargeType无效");
        }
        if (tcArticle.getChargeType().equals(ArticleChargeTypeEnum.CHARGE.getType()) && Objects.isNull(tcArticle.getPrice())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "price无效");
        }
        if (tcArticle.getReleaseStatus() == null || !ArticleReleaseStatusEnum.contains(tcArticle.getReleaseStatus())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "releaseStatus无效");
        }
        if (tcArticle.getArticleStatus() == null || !ArticleStatusEnum.contains(tcArticle.getArticleStatus())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleStatus无效");
        }

        Result<Boolean> result = ResultBuild.wrapSuccess();
        return result;
    }

    private TcArticleQuery buildGetArticleListQuery(ArticleListReqQuery reqQuery, Page page) {
        TcArticleQuery query = new TcArticleQuery();
        query.setCreatedStart(reqQuery.getReleaseTimeStart());
        query.setCreatedEnd(reqQuery.getReleaseTimeEnd());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
