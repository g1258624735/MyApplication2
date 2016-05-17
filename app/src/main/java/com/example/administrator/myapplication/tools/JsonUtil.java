//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.administrator.myapplication.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
    public JsonUtil() {
    }

    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }

    public static <T> T deserialize(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }

    public static <T> T parseObject(String json, Type clazz) {
        return JSON.parseObject(json, clazz, new Feature[0]);
    }

    public static <T> List<T> deserializeList(String json, Class<T> clz) {
        return JSON.parseArray(json, clz);
    }

    public static <T> T deserializeAny(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type, new Feature[0]);
    }
}
