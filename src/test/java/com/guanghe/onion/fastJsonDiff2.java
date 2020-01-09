package com.guanghe.onion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

public class fastJsonDiff2 {


    /**
     * 对jsonarray做比较：size不同，返回false; 排序后的String对比，不相同返回false;
     *
     * @param expect
     * @param actual
     * @return
     */
    public static boolean compareJsonArray(JSONArray expect, JSONArray actual) {
        int expect_size = expect.size();
        int actual_size = actual.size();
        if (expect_size != actual_size) {
            System.out.println("the two JSONArrays' size is not equal >>"
                    + "expect JSONArray's size  is [[" + expect.size()
                    + "]], but actual JSONArray's size is [[" + actual.size()
                    + "]]");
            return false;
        }
        if (!sortJsonArray(expect).toString().equals(
                sortJsonArray(actual).toString())) {
            System.out.println("the two JSONArrays' value is not equal >>"
                    + "expect is \r\n" + expect + ", \r\nbut actual  is \r\n"
                    + actual);
            return false;
        }
        return true;
    }

    /**
     * JSONObject排序
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("all")
    public static JSONObject sortJsonObject(JSONObject obj) {
        Map map = new TreeMap();
        Iterator<String> it = obj.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                // System.out.println(value + " is JSONObject");
                map.put(key, sortJsonObject((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // System.out.println(value + " is JSONArray");
                map.put(key, sortJsonArray((JSONArray) value));
            } else {
                map.put(key, value);
            }
        }
        return JSONObject.parseObject(JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

    }

    /**
     * JSONArray排序
     *
     * @param array
     * @return
     */
    @SuppressWarnings("all")
    public static JSONArray sortJsonArray(JSONArray array) {
        List list = new ArrayList();
        int size = array.size();
        for (int i = 0; i < size; i++) {
            Object obj = array.get(i);
            if (obj instanceof JSONObject) {
                list.add(sortJsonObject((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                list.add(sortJsonArray((JSONArray) obj));
            } else {
                list.add(obj);
            }
        }
        list.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));
        return JSONArray.parseArray(JSON.toJSONString(list));
    }


}
