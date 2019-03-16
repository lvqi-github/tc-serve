package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcUserAuthorSubscribeService;
import com.tcxx.serve.service.entity.TcUserAuthorSubscribe;
import com.tcxx.serve.service.query.TcUserAuthorSubscribeQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.query.UserAuthorSubscribeListReqQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/userAuthorSubscribe")
public class UserAuthorSubscribeController {

    @Autowired
    private TcUserAuthorSubscribeService tcUserAuthorSubscribeService;

    @AdminLoginTokenValidation
    @RequestMapping("/getUserAuthorSubscribeList")
    public Result<TcUserAuthorSubscribe> getUserAuthorSubscribeList(UserAuthorSubscribeListReqQuery reqQuery, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcUserAuthorSubscribeQuery query = buildGetUserAuthorSubscribeListQuery(reqQuery, page);

        Integer count = tcUserAuthorSubscribeService.countByCondition(query);
        List<TcUserAuthorSubscribe> userAuthorSubscribeList = tcUserAuthorSubscribeService.listByCondition(query);

        Result<TcUserAuthorSubscribe> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(userAuthorSubscribeList);
        return result;
    }

    private TcUserAuthorSubscribeQuery buildGetUserAuthorSubscribeListQuery(UserAuthorSubscribeListReqQuery reqQuery, Page page) {
        TcUserAuthorSubscribeQuery query = new TcUserAuthorSubscribeQuery();
        query.setUserId(reqQuery.getUserId());
        query.setAuthorId(reqQuery.getAuthorId());
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        return query;
    }

}
