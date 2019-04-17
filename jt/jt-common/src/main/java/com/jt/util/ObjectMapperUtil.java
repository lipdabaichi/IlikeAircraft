package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 该工具类解决对象转化中的try-catch问题
 * 1.对象转json toJson
 * 2.json转对象 toObject
 */
public class ObjectMapperUtil {
    //定义常量
//    private static final int aa = 200;
    //下面这句:是否有线程安全性问题   A:没有线程安全性问题
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJSON(Object object){
        String json=null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //如果报错需要转化为运行时异常
            throw new RuntimeException();
        }
        return json;
    }

    public static <T> T toObject(String json, Class<T> target) {
        T t = null;
        try {
             t = mapper.readValue(json, target);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
        return t;
    }
}
