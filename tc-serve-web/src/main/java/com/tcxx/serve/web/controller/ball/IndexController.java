package com.tcxx.serve.web.controller.ball;

import com.tcxx.serve.core.annotation.WeChatLoginTokenValidation;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ball/index")
public class IndexController {

    @WeChatLoginTokenValidation
    @RequestMapping("/getNewestArticles")
    public Result<Map<String, Object>> getNewestArticles(Integer page, Integer pageSize) {
        int len = (32 - pageSize * (page - 1)) < pageSize ? (32 - pageSize * (page - 1)) : pageSize;
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = new HashMap();
            map.put("id", String.valueOf((page - 1) * pageSize + (i + 1)));
            map.put("title", "【飞刀】夜间足球5场");
            map.put("label", "03:45 英甲 普利茅斯 vs 沃尔索尔");
            map.put("releaseTime", "2019-01-31 17:39");
            map.put("amount", "6.66");
            map.put("urlImg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548934533040&di=af2682ea8c31719b24775b98de9470a6&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F16%2F12%2F26%2Fc23a545ffcde0207c9d4d84c4e1f418c.jpg");
            list.add(map);
        }
        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setTotalElements(32);
        result.setValues(list);
        return result;
    }

}
