package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcUserService;
import com.tcxx.serve.service.entity.TcUser;
import com.tcxx.serve.service.enumtype.UserSexEnum;
import com.tcxx.serve.service.query.TcUserQuery;
import com.tcxx.serve.web.domain.Page;
import com.tcxx.serve.web.domain.admin.resp.UserListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private TcUserService tcUserService;

    @AdminLoginTokenValidation
    @RequestMapping("/getUserList")
    public Result<UserListResp> getUserList(String nickName, Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        TcUserQuery query = new TcUserQuery();
        query.setNickName(nickName);
        query.setPagingPageCurrent(page.getPage());
        query.setPagingPageSize(page.getPageSize());
        Integer count = tcUserService.countByCondition(query);
        List<TcUser> userList = tcUserService.listByCondition(query);

        List<UserListResp> userListResps = new ArrayList<>();
        userList.stream().forEach(bean -> {
            UserListResp resp = new UserListResp();
            resp.setUserId(bean.getId());
            resp.setNickName(bean.getNickName());
            resp.setHeadImgUrl(bean.getHeadImgUrl());
            resp.setSex(bean.getSex());
            resp.setSexName(UserSexEnum.getByType(bean.getSex()));
            resp.setProvince(bean.getProvince());
            resp.setCity(bean.getCity());
            resp.setCountry(bean.getCountry());
            resp.setOpenId(bean.getOpenId());
            resp.setUnionId(bean.getUnionId());
            resp.setModified(bean.getModified());
            resp.setCreated(bean.getCreated());
            userListResps.add(resp);
        });

        Result<UserListResp> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(userListResps);
        return result;
    }

}
