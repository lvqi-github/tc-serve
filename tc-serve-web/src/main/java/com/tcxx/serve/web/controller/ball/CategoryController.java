package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.TcAuthorService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.entity.TcAuthor;
import com.tcxx.serve.service.enumtype.ArticleReleaseStatusEnum;
import com.tcxx.serve.service.enumtype.ArticleStatusEnum;
import com.tcxx.serve.service.query.TcArticleQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.ball.resp.BallNewestArticleListResp;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/ball/category")
public class CategoryController {

    @Autowired
    private TcAuthorService tcAuthorService;

    @Autowired
    private TcArticleService tcArticleService;

    @WeChatLoginTokenValidation
    @RequestMapping("/getAuthorList")
    public Result<Map<String, Object>> getAuthorList() {
        List<TcAuthor> authorList = tcAuthorService.listAll();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("selectedAuthorId", (authorList != null && authorList.size() > 0) ? authorList.get(0).getAuthorId() : "");

        List<Map<String, String>> authorMapList = new ArrayList<>();
        authorList.stream().forEach(bean -> {
            Map<String, String> map = new HashMap<>();
            map.put("authorId", bean.getAuthorId());
            map.put("authorName", bean.getAuthorName());
            authorMapList.add(map);
        });
        resultMap.put("authorList", authorMapList);

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValue(resultMap);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getAuthorArticleList")
    public Result<BallNewestArticleListResp> getAuthorArticleList(String authorId, Page page) {
        if (StringUtils.isBlank(authorId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId不能为空");
        }
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        //获取作者30日内 已发布的文章
        TcArticleQuery query = new TcArticleQuery();
        query.setAuthorId(authorId);
        query.setCreatedStart(DateUtils.addDays(new Date(), -30));
        query.setReleaseStatus(ArticleReleaseStatusEnum.RELEASE.getStatus());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());

        Integer count = tcArticleService.countByCondition(query);
        List<TcArticle> articleList = tcArticleService.listByCondition(query);

        List<BallNewestArticleListResp> articleListRespList = new ArrayList<>();
        articleList.stream().forEach(bean -> {
            BallNewestArticleListResp resp = new BallNewestArticleListResp();
            resp.setArticleId(bean.getArticleId());
            resp.setArticleTitle(bean.getArticleTitle());
            resp.setArticleDesc(bean.getArticleDesc());
            resp.setArticleType(bean.getArticleType());
            resp.setChargeType(bean.getChargeType());
            resp.setPrice(bean.getPrice());
            resp.setArticleStatus(bean.getArticleStatus());
            resp.setCreated(bean.getCreated());
            articleListRespList.add(resp);
        });

        Result<BallNewestArticleListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(articleListRespList);
        return result;
    }

}
