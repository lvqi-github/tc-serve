package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.annotation.WeChatLoginUser;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.core.utils.BusinessUuidGenerateUtil;
import com.tcxx.serve.service.*;
import com.tcxx.serve.service.entity.*;
import com.tcxx.serve.service.enumtype.AuthorPlatformSourceEnum;
import com.tcxx.serve.service.enumtype.ValidTypeEnum;
import com.tcxx.serve.service.query.TcArticlePurchaseRecordQuery;
import com.tcxx.serve.service.query.TcMemberRechargeRecordQuery;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.ball.WeChatUser;
import com.tcxx.serve.web.domain.ball.resp.BallArticlePurchaseRecordListResp;
import com.tcxx.serve.web.domain.ball.resp.BallMemberInfoResp;
import com.tcxx.serve.web.domain.ball.resp.BallMemberRechargePackageListResp;
import com.tcxx.serve.web.domain.ball.resp.BallMemberRechargeRecordListResp;
import com.tcxx.serve.wechat.WeChatConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ball/personal")
public class PersonalController {

    @Autowired
    private TcUserService tcUserService;

    @Autowired
    private TcAuthorService tcAuthorService;

    @Autowired
    private TcUserAuthorSubscribeService tcUserAuthorSubscribeService;

    @Autowired
    private TcPublicAccountFocusService tcPublicAccountFocusService;

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    @Autowired
    private TcMemberService tcMemberService;

    @Autowired
    private TcMemberRechargePackageService tcMemberRechargePackageService;

    @Autowired
    private TcMemberRechargeRecordService tcMemberRechargeRecordService;

    @Autowired
    private TcArticlePurchaseRecordService tcArticlePurchaseRecordService;

    @WeChatLoginTokenValidation
    @RequestMapping("/getArticlePurchaseRecordList")
    public Result<BallArticlePurchaseRecordListResp> getArticlePurchaseRecordList(@WeChatLoginUser WeChatUser weChatUser, Page page) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
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

        //获取用户有效的购买文章记录
        TcArticlePurchaseRecordQuery query = new TcArticlePurchaseRecordQuery();
        query.setUserId(weChatUser.getUserId());
        query.setRecordValidStatus(ValidTypeEnum.VALID.getType());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());

        Integer count = tcArticlePurchaseRecordService.countByCondition(query);
        List<TcArticlePurchaseRecord> articlePurchaseRecordList = tcArticlePurchaseRecordService.listByCondition(query);

        List<BallArticlePurchaseRecordListResp> articlePurchaseRecordListResps = new ArrayList<>();
        articlePurchaseRecordList.stream().forEach(bean -> {
            BallArticlePurchaseRecordListResp resp = new BallArticlePurchaseRecordListResp();
            resp.setPurchaseRecordNo(bean.getPurchaseRecordNo());
            resp.setArticleId(bean.getArticleId());
            resp.setArticleTitle(bean.getArticleTitle());
            resp.setArticleDesc(bean.getArticleDesc());
            resp.setAmount(bean.getAmount());
            resp.setOrderNo(bean.getOrderNo());
            resp.setCreated(bean.getCreated());
            articlePurchaseRecordListResps.add(resp);
        });

        Result<BallArticlePurchaseRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(articlePurchaseRecordListResps);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getMemberRechargeRecordList")
    public Result<BallMemberRechargeRecordListResp> getMemberRechargeRecordList(@WeChatLoginUser WeChatUser weChatUser, Page page) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
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

        //获取用户有效的充值记录
        TcMemberRechargeRecordQuery query = new TcMemberRechargeRecordQuery();
        query.setUserId(weChatUser.getUserId());
        query.setRecordValidStatus(ValidTypeEnum.VALID.getType());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());

        Integer count = tcMemberRechargeRecordService.countByCondition(query);
        List<TcMemberRechargeRecord> memberRechargeRecordList = tcMemberRechargeRecordService.listByCondition(query);

        List<BallMemberRechargeRecordListResp> memberRechargeRecordListResps = new ArrayList<>();
        memberRechargeRecordList.stream().forEach(bean -> {
            BallMemberRechargeRecordListResp resp = new BallMemberRechargeRecordListResp();
            resp.setRechargeRecordNo(bean.getRechargeRecordNo());
            resp.setRechargePackageDesc(bean.getRechargePackageDesc());
            resp.setRechargeAmount(bean.getRechargeAmount());
            resp.setValidityStartDate(bean.getValidityStartDate());
            resp.setValidityEndDate(bean.getValidityEndDate());
            resp.setOrderNo(bean.getOrderNo());
            resp.setCreated(bean.getCreated());
            memberRechargeRecordListResps.add(resp);
        });

        Result<BallMemberRechargeRecordListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(memberRechargeRecordListResps);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getMemberInfo")
    public Result<BallMemberInfoResp> getMemberInfo(@WeChatLoginUser WeChatUser weChatUser) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }

        BallMemberInfoResp resp = new BallMemberInfoResp();

        Date currentTime = new Date();

        //获取会员信息
        TcMember member = tcMemberService.getByUserId(weChatUser.getUserId());
        if (Objects.isNull(member)){
            resp.setStatus(1);
        }else {
            resp.setMemberEndDate(member.getMemberEndDate());
            if (currentTime.after(member.getMemberEndDate())){ //当前时间 在 会员结束时间 之后 代表已失效
                resp.setStatus(2);
            }else {
                resp.setStatus(3);
            }
        }

        Result<BallMemberInfoResp> result = ResultBuild.wrapSuccess();
        result.setValue(resp);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getMemberRechargePackageList")
    public Result<Map<String, Object>> getMemberRechargePackageList() {
        List<TcMemberRechargePackage> rechargePackageList = tcMemberRechargePackageService.listEnableAll();

        Map<String, Object> map = new HashMap<>();
        if (rechargePackageList != null && rechargePackageList.size() > 0) {
            map.put("selectPackageId", rechargePackageList.get(0).getPackageId());
            map.put("selectPackageDesc", rechargePackageList.get(0).getPackageDesc());
            map.put("selectPackageDesc", rechargePackageList.get(0).getPackageDesc());
            map.put("selectPackageCentPrice", rechargePackageList.get(0).getPrice().multiply(BigDecimal.valueOf(100)).longValue());

            List<BallMemberRechargePackageListResp> rechargePackageListRespList = new ArrayList<>();
            rechargePackageList.stream().forEach(bean -> {
                BallMemberRechargePackageListResp resp = new BallMemberRechargePackageListResp();
                resp.setPackageId(bean.getPackageId());
                resp.setPackageDesc(bean.getPackageDesc());
                resp.setPrice(bean.getPrice());
                resp.setCentPrice(bean.getPrice().multiply(BigDecimal.valueOf(100)).longValue());
                resp.setDailyPrice(bean.getPrice().divide(BigDecimal.valueOf(bean.getDaysOfValidity()), 1, BigDecimal.ROUND_HALF_UP));
                resp.setSpecialCombo(bean.getPackageId().equals(rechargePackageList.get(0).getPackageId()));
                rechargePackageListRespList.add(resp);
            });
            map.put("rechargePackageList", rechargePackageListRespList);
        }

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setTotalElements(rechargePackageList.size());
        result.setValue(map);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping(value = "/subscribeAuthor", method = RequestMethod.POST)
    public Result<Boolean> subscribeAuthor(@WeChatLoginUser WeChatUser weChatUser, String authorId) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }
        if (StringUtils.isBlank(authorId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId不能为空");
        }

        //获取用户信息
        TcUser tcUser = tcUserService.getByUserId(weChatUser.getUserId());
        if (Objects.isNull(tcUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "非法用户");
        }

        // 校验是否关注公众号
        TcPublicAccountFocus publicAccountFocus = tcPublicAccountFocusService.
                getByUuid(BusinessUuidGenerateUtil.getTcPublicAccountFocusUuid(weChatConfiguration.getPublicAccountWechatId(), tcUser.getOpenId()));
        if (Objects.isNull(publicAccountFocus)) {
            Result<Boolean> result = ResultBuild.wrapSuccess();
            result.setValue(false);
            result.setResultMsg("请先关注公众号");
            return result;
        }

        TcUserAuthorSubscribe authorSubscribe = tcUserAuthorSubscribeService.getByUserIdAndAuthorId(weChatUser.getUserId(), authorId);
        if (Objects.isNull(authorSubscribe)){
            TcUserAuthorSubscribe subscribe = new TcUserAuthorSubscribe();
            subscribe.setUserId(weChatUser.getUserId());
            subscribe.setAuthorId(authorId);
            boolean insert = tcUserAuthorSubscribeService.insert(subscribe);
            if (!insert){
                return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
            }
        }

        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping(value = "/unSubscribeAuthor", method = RequestMethod.POST)
    public Result<Boolean> unSubscribeAuthor(@WeChatLoginUser WeChatUser weChatUser, String authorId) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }
        if (StringUtils.isBlank(authorId)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId不能为空");
        }

        TcUserAuthorSubscribe authorSubscribe = tcUserAuthorSubscribeService.getByUserIdAndAuthorId(weChatUser.getUserId(), authorId);
        if (Objects.nonNull(authorSubscribe)){
            tcUserAuthorSubscribeService.deleteByUserIdAndAuthorId(weChatUser.getUserId(), authorId);
        }

        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

    @WeChatLoginTokenValidation
    @RequestMapping("/getSubscribeSetupList")
    public Result<Map<String,Object>> getSubscribeSetupList(@WeChatLoginUser WeChatUser weChatUser) {
        if (Objects.isNull(weChatUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }

        // 获取作者列表
        List<TcAuthor> authorList = tcAuthorService.listAll();
        //按照平台分组
        Map<String, List<TcAuthor>> groupAuthorMap = authorList.stream().collect(Collectors.groupingBy(TcAuthor::getPlatformSource));

        // 获取用户订阅作者列表
        TcUserAuthorSubscribeQuery query = new TcUserAuthorSubscribeQuery();
        query.setUserId(weChatUser.getUserId());
        List<TcUserAuthorSubscribe> subscribeList = tcUserAuthorSubscribeService.listAllByCondition(query);
        // 以作者id为key 转为map
        Map<String, String> subscribeMap = subscribeList.stream().collect(Collectors.toMap(TcUserAuthorSubscribe::getAuthorId, TcUserAuthorSubscribe::getUserId));

        //组装数据
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (AuthorPlatformSourceEnum platformSourceEnum : AuthorPlatformSourceEnum.values()) {
            Map<String,Object> map = new HashMap<>();
            map.put("groupName", platformSourceEnum.getPlatformName());

            List<Map<String, Object>> mapList = new ArrayList<>();
            if (groupAuthorMap.get(platformSourceEnum.getPlatformCode()) != null){
                for (TcAuthor author : groupAuthorMap.get(platformSourceEnum.getPlatformCode())){
                    Map<String, Object> groupMap = new HashMap<>();
                    groupMap.put("authorId", author.getAuthorId());
                    groupMap.put("authorName", author.getAuthorName());
                    groupMap.put("subscribeFlag", (subscribeMap.get(author.getAuthorId()) == null) ? false : true);
                    mapList.add(groupMap);
                }
            }
            map.put("options", mapList);
            resultList.add(map);
        }

        Result<Map<String,Object>> result = ResultBuild.wrapSuccess();
        result.setValues(resultList);
        return result;
    }

}
