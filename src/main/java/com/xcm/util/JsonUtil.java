package com.xcm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 35394 on 2016/10/16.
 */
public class JsonUtil {
    private static Logger logger = LoggerManage.getLogger();

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * String Object转换
     *
     * @param string
     * @param classT
     * @param <T>
     * @return
     */
    public static <T> T StringToObject(String string, Class<T> classT) {
        try {
            return objectMapper.readValue(string, classT);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * Object String转换
     *
     * @param object
     * @return
     */
    public static String ObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * LinkedHashMap转换为T
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T linkedHashMapToObject(LinkedHashMap<Object, Object> map, Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                field.set(object, map.get(field.getName()));
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

}
