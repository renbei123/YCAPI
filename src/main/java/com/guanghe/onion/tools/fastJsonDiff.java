package com.guanghe.onion.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.base.CRSTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class fastJsonDiff {


    private final static Logger logger = LoggerFactory.getLogger("fastJsonDiff");

    public static String compareJson(JSONObject json1, JSONObject json2, List exceptkeys) {
        CRSTask.sb.setLength(0);
        json1 =sortJsonObject(json1);
        json2 =sortJsonObject(json2);
        Iterator<String> i = json1.keySet().iterator();
        String key;
        while (i.hasNext()) {
            key = i.next();
            if(exceptkeys!=null&&exceptkeys.contains(key))  continue;
            else
            compareJson(json1.get(key), json2.get(key), key);
        }
        logger.info("***** compareJson size: "+CRSTask.sb.length()+" result:"+CRSTask.sb.toString());
        return CRSTask.sb.toString();
    }

    public static void compareJson2(JSONObject json1, JSONObject json2, String key) {

        Iterator<String> i = json1.keySet().iterator();

        while (i.hasNext()) {
                key=i.next();
                compareJson(json1.get(key), json2.get(key), key);
        }

    }


    public static void compareJson(Object json1, Object json2, String key) {
        if (json1 instanceof JSONObject) {
            compareJson2((JSONObject) json1, (JSONObject) json2, key);
        } else if (json1 instanceof JSONArray) {
            compareJson((JSONArray) json1, (JSONArray) json2, key);
        } else if (json1 instanceof String) {
            try {
                String json1ToStr = json1.toString();
                String json2ToStr = json2.toString();
                compareJson(json1ToStr, json2ToStr, key);
            } catch (Exception e) {
//                logger.info("转换发生异常 key:" + key);
                CRSTask.sb.append("转换发生异常 key:" + key);
                e.printStackTrace();
            }

        } else {
            compareJson(json1.toString(), json2.toString(), key);
        }
    }

    public static void compareJson(String str1, String str2, String key) {
        if (!str1.equals(str2)) {
            CRSTask.sb.append("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2+";");
//            System.err.println("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2);
//            logger.error("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2+";");
        } else {
//            logger.info("一致：key:" + key + ",json1:" + str1 + ",json2:" + str2+";");
        }
    }

    public static void compareJson(JSONArray json1, JSONArray json2, String key) {
        if (json1 != null && json2 != null) {

            Iterator i1 = json1.iterator();
            Iterator i2 = json2.iterator();
            while (i1.hasNext()) {
                compareJson(i1.next(), i2.next(), key);
            }
        } else {
            if (json1 == null && json2 == null) {
                CRSTask.sb.append("不一致：key:" + key + "  在json1和json2中均不存在"+";");
            } else if (json1 == null) {
                CRSTask.sb.append("不一致：key:" + key + "  在json1中不存在"+";");
            } else if (json2 == null) {
                CRSTask.sb.append("不一致：key:" + key + "  在json2中不存在"+";");
            } else {
                CRSTask.sb.append("不一致：key:" + key + "  未知原因"+";");
            }

        }
    }

    /**
     * 对jsonarray做比较：size不同，返回false; 排序后的String对比，不相同返回false;
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

                map.put(key, sortJsonObject((JSONObject)value));
            } else if (value instanceof JSONArray) {

                map.put(key, sortJsonArray((JSONArray)value));
            } else {
                map.put(key, value);
            }
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
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
                list.add(sortJsonObject((JSONObject)obj));
            } else if (obj instanceof JSONArray) {
                list.add(sortJsonArray((JSONArray)obj));
            } else {
                list.add(obj);
            }
        }
        list.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));
        return  JSONArray.parseArray(JSON.toJSONString(list));
    }


}
