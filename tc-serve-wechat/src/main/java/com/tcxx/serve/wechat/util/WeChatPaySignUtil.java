package com.tcxx.serve.wechat.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class WeChatPaySignUtil {

    /**
     *
     * @param ParamMap 要签名的参数map
     * @param appSecret 应用秘钥
     * @param isBlank true 过滤签名数组值为null字段  false 保留签名数组值为null字段
     * @return
     */
    public static String buildSign(Map<String, String> ParamMap, String appSecret, boolean isBlank) {
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
        System.out.println(String.format("%s&key=%s", sb.toString(), appSecret));

        return DigestUtils.md5Hex(String.format("%s&key=%s", sb.toString(), appSecret)).toUpperCase();
    }

}
