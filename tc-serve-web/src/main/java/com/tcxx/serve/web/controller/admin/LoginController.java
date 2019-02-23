package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.annotation.AdminLoginUser;
import com.tcxx.serve.core.annotation.PassTokenValidation;
import com.tcxx.serve.core.jwt.JavaWebTokenUtil;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import com.tcxx.serve.web.domain.AdminUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin/login")
public class LoginController {

    @PassTokenValidation
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result<String> userLogin(String username, String password) {
        if (StringUtils.isBlank(username)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户名不能为空");
        }
        if (StringUtils.isBlank(password)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "密码不能为空");
        }
        // lvqi33 / kadf4X1YnyVv9lMp
        if(!"lvqi33".equals(username) || !"a7bebc3205fbe7fdefffbda578fcbd23".equals(password)) {
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR1502);
        }
        // 生成JavaWebToken
        String token = JavaWebTokenUtil.createToken(username, JavaWebTokenUtil.TC_ADMIN_TOKEN_SECRET, JavaWebTokenUtil.TC_ADMIN_TOKEN_ISSUER, JavaWebTokenUtil.TC_ADMIN_TOKEN_EXPIRE_HOURS);

        Result<String> result = ResultBuild.wrapSuccess();
        result.setValue(token);
        return result;
    }

    @AdminLoginTokenValidation
    @RequestMapping("/getUserInfo")
    public Result<Map<String, Object>> getUserInfo(@AdminLoginUser AdminUser adminUser) {
        if (Objects.isNull(adminUser)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "用户信息不能为空");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("username", adminUser.getUsername());
        map.put("avatar", "https://randy168.com/1533262153771.gif");

        Result<Map<String, Object>> result = ResultBuild.wrapSuccess();
        result.setValue(map);
        return result;
    }

}
