package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginUser;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcArticlePurchaseRecordService;
import com.tcxx.serve.service.TcArticleService;
import com.tcxx.serve.service.TcMemberService;
import com.tcxx.serve.service.entity.TcArticle;
import com.tcxx.serve.service.entity.TcMember;
import com.tcxx.serve.service.enumtype.ArticleChargeTypeEnum;
import com.tcxx.serve.service.enumtype.ArticleReleaseStatusEnum;
import com.tcxx.serve.service.enumtype.ArticleStatusEnum;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import com.tcxx.serve.service.query.TcArticleQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.ball.WeChatUser;
import com.tcxx.serve.web.domain.ball.resp.BallArticleDetailResp;
import com.tcxx.serve.web.domain.ball.resp.BallNewestArticleListResp;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/ball/index")
public class IndexController {

    @Autowired
    private TcArticleService tcArticleService;

    @Autowired
    private TcMemberService tcMemberService;

    @Autowired
    private TcArticlePurchaseRecordService tcArticlePurchaseRecordService;

    @WeChatLoginTokenValidation
    @RequestMapping("/getNewestArticleList")
    public Result<BallNewestArticleListResp> getNewestArticleList(Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        //获取2日内 已发布且未结束的文章
        TcArticleQuery query = new TcArticleQuery();
        query.setCreatedStart(DateUtils.addHours(new Date(), -48));
        query.setReleaseStatus(ArticleReleaseStatusEnum.RELEASE.getStatus());
        query.setArticleStatusList(new ArrayList<Integer>(){{add(ArticleStatusEnum.NOT_STARTED.getStatus()); add(ArticleStatusEnum.ONGOING.getStatus());}});
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

    @WeChatLoginTokenValidation
    @RequestMapping("/getArticleDetail")
    public Result<Map<String, Object>> getArticleDetail(@WeChatLoginUser WeChatUser weChatUser, String articleId) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }
        if (StringUtils.isBlank(articleId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "articleId不能为空");
        }

        Map<String, Object> resultMap = new HashMap<>();

        TcArticle tcArticle = tcArticleService.getByArticleId(articleId);
        BallArticleDetailResp resp = new BallArticleDetailResp();
        resp.setArticleId(tcArticle.getArticleId());
        resp.setArticleTitle(tcArticle.getArticleTitle());
        resp.setCreated(tcArticle.getCreated());

        if (viewPermission(weChatUser.getUserId(), tcArticle)){
            resultMap.put("viewPermission", true);
            resp.setArticleContentText(tcArticle.getArticleContentText());
            resp.setArticleContentImg(tcArticle.getArticleContentImg());
        }else {
            resultMap.put("viewPermission", false);
            resp.setArticlePreviewText(tcArticle.getArticlePreviewText());
            resp.setArticlePreviewImg(tcArticle.getArticlePreviewImg());
            resp.setPrice(tcArticle.getPrice());
        }
        resultMap.put("articleDetail", resp);

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValue(resultMap);
        return result;
    }

    /**
     * 先判断文章类型，再判断会员是否有效，最后判断是否购买
     * @param userId
     * @param tcArticle
     * @return
     */
    private boolean viewPermission(String userId, TcArticle tcArticle) {
        if (tcArticle.getChargeType().equals(ArticleChargeTypeEnum.PUBLIC.getType())){ //文章公开
            return true;
        }else {
            //判断会员有效
            TcMember tcMember = tcMemberService.getByUserId(userId);
            if (Objects.nonNull(tcMember)){
                Date currentTime = new Date();
                if (!currentTime.after(tcMember.getMemberEndDate())) { //当前时间 不在 会员结束时间之后 代表会员有效
                    return true;
                }
            }

            //判断是否购买
            TcArticlePurchaseRecordQuery query = new TcArticlePurchaseRecordQuery();
            query.setUserId(userId);
            query.setArticleId(tcArticle.getArticleId());
            query.setRecordValidStatus(ValidTypeEnum.VALID.getType());
            Integer count = tcArticlePurchaseRecordService.countByCondition(query);
            if (count > 0){
                return true;
            }
        }
        return false;
    }

}
