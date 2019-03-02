package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.service.TcAuthorService;
import com.tcxx.serve.service.entity.TcAuthor;
import com.tcxx.serve.service.enumtype.AuthorPlatformSourceEnum;
import com.tcxx.serve.web.domain.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/author")
public class AuthorController {

    @Autowired
    private TcAuthorService tcAuthorService;

    @AdminLoginTokenValidation
    @RequestMapping("/getAuthorList")
    public Result<TcAuthor> getAuthorList(Page page) {
        if (Objects.isNull(page)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page信息不能为空");
        }
        if (Objects.isNull(page.getPage()) || page.getPage() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "page不能为空且大于0");
        }
        if (Objects.isNull(page.getPageSize()) || page.getPageSize() < 1){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "pageSize不能为空且大于0");
        }

        Integer count = tcAuthorService.countAll();
        List<TcAuthor> authorList = tcAuthorService.listAllPage(page.getPage(), page.getPageSize());
        authorList.stream().filter(bean -> {
            bean.setPlatformSource(AuthorPlatformSourceEnum.getByPlatformCode(bean.getPlatformSource()));
            return true;
        }).collect(Collectors.toList());

        Result<TcAuthor> result = ResultBuild.wrapSuccess();
        result.setTotalElements(count);
        result.setValues(authorList);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getPlatformSourceList")
    public Result<Map<String, String>> getPlatformSourceList() {
        List<Map<String, String>> list = new ArrayList<>();
        for (AuthorPlatformSourceEnum platformSourceEnum : AuthorPlatformSourceEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("label", platformSourceEnum.getPlatformName());
            map.put("value", platformSourceEnum.getPlatformCode());
            list.add(map);
        }
        Result<Map<String, String>> result = ResultBuild.wrapSuccess();
        result.setValues(list);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/authorAdd", method = RequestMethod.POST)
    public Result<Boolean> authorAdd(TcAuthor tcAuthor) {
        if (Objects.isNull(tcAuthor)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "请求参数不能为空");
        }
        if (StringUtils.isBlank(tcAuthor.getAuthorName())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorName无效");
        }
        if (StringUtils.isBlank(tcAuthor.getPlatformSource()) || !AuthorPlatformSourceEnum.contains(tcAuthor.getPlatformSource())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "platformSource无效");
        }

        boolean insert = tcAuthorService.insert(tcAuthor);
        if (!insert) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }
        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/authorUpdate", method = RequestMethod.POST)
    public Result<Boolean> authorUpdate(TcAuthor tcAuthor) {
        if (Objects.isNull(tcAuthor)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "请求参数不能为空");
        }
        if (StringUtils.isBlank(tcAuthor.getAuthorId())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorId无效");
        }
        if (StringUtils.isBlank(tcAuthor.getAuthorName())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "authorName无效");
        }
        if (StringUtils.isBlank(tcAuthor.getPlatformSource()) || !AuthorPlatformSourceEnum.contains(tcAuthor.getPlatformSource())){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "platformSource无效");
        }

        boolean update = tcAuthorService.update(tcAuthor);
        if (!update) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR2501);
        }
        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(true);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getAuthorSelectOptions")
    public Result<Map<String,Object>> getAuthorSelectOptions() {
        List<TcAuthor> authorList = tcAuthorService.listAll();
        //按照平台分组
        Map<String, List<TcAuthor>> groupAuthorMap = authorList.stream().collect(Collectors.groupingBy(TcAuthor::getPlatformSource));
        //组装数据
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (AuthorPlatformSourceEnum platformSourceEnum : AuthorPlatformSourceEnum.values()) {
            Map<String,Object> map = new HashMap<>();
            map.put("label", platformSourceEnum.getPlatformName());

            List<Map<String, String>> mapList = new ArrayList<>();
            if (groupAuthorMap.get(platformSourceEnum.getPlatformCode()) != null){
                for (TcAuthor author : groupAuthorMap.get(platformSourceEnum.getPlatformCode())){
                    Map<String, String> groupMap = new HashMap<>();
                    groupMap.put("label", author.getAuthorName());
                    groupMap.put("value", author.getAuthorId());
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
