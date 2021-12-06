package com.hikvision.model.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.util.Strings;

/**
 * @author zhangwei151
 * @date 2021/12/3 14:17
 */
public class JsonUtil {

    /**
     * 对象转json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return Strings.EMPTY;
        }
        return JSONObject.toJSONString(object);
    }

    public static <T> T stringToObject(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        return JSONObject.parseObject(json, clazz);
    }
}
