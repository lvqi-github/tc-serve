package com.tcxx.serve.web.interceptor;

import com.tcxx.serve.core.annotation.PassRequestSignValidation;
import com.tcxx.serve.core.enumtype.AppEnum;
import com.tcxx.serve.core.exception.HttpRequestParamVerifyRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class RequestSignValidateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查是否有PassRequestSignValidation注释，有则跳过验证
        if (method.isAnnotationPresent(PassRequestSignValidation.class)) {
            return true;
        }

        //获取所有参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //转化为Map<String, String>
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            map.put(entry.getKey(), (entry.getValue() != null && entry.getValue().length > 0) ? entry.getValue()[0] : null );
        }

        // 参数校验
        if (map.get("appId") == null) {
            throw new HttpRequestParamVerifyRuntimeException(ResultCodeEnum.ERROR4001, "appId不能为空");
        }
        if (!AppEnum.contains(map.get("appId"))) {
            throw new HttpRequestParamVerifyRuntimeException(ResultCodeEnum.ERROR4001, "appId不合法");
        }
        if (map.get("timestamp") == null) {
            throw new HttpRequestParamVerifyRuntimeException(ResultCodeEnum.ERROR4001, "timestamp不能为空");
        }
        if (map.get("sign") == null) {
            throw new HttpRequestParamVerifyRuntimeException(ResultCodeEnum.ERROR4001, "sign不能为空");
        }

        String sign = buildSign(map, AppEnum.getByAppId(map.get("appId")), true);
        if (!map.get("sign").equals(sign)){
            throw new HttpRequestParamVerifyRuntimeException(ResultCodeEnum.ERROR4001, "sign不合法");
        }

        return true;
    }

    /**
     *
     * @param ParamMap 要签名的参数map
     * @param appSecret 应用秘钥
     * @param isBlank true 过滤签名数组值为null字段  false 保留签名数组值为null字段
     * @return
     */
    private String buildSign(Map<String, String> ParamMap, String appSecret, boolean isBlank) {
        // 除去数组中的空值和签名参数
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : ParamMap.entrySet()) {
            if (isBlank) {
                if (StringUtils.isBlank(entry.getValue())){
                    continue;
                }
            }
            if (entry.getKey().equalsIgnoreCase("sign")) {
                continue;
            }
            map.put(entry.getKey(), entry.getValue());
        }

        // 把map所有元素按字典升序排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                sb.append(key + "=" + value);
            } else {
                sb.append(key + "=" + value + "&");
            }
        }

        return DigestUtils.md5Hex(String.format("%s%s%s", appSecret, sb.toString(), appSecret));
    }
}
