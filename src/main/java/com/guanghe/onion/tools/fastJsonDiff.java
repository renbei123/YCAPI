package com.guanghe.onion.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class fastJsonDiff {


    public static void compareJson(JSONObject json1, JSONObject json2, String key) {
        json1 =sortJsonObject(json1);
        json2 =sortJsonObject(json2);
        Iterator<String> i = json1.keySet().iterator();
        String temp;
        while (i.hasNext()) {
            temp = i.next();
            if(key!=null&&key.trim().equals(temp))  continue;
            else
            compareJson(json1.get(temp), json2.get(temp), temp);
        }
//        return sb.toString();
    }

    public static void compareJson2(JSONObject json1, JSONObject json2, String key) {
        json1 =sortJsonObject(json1);
        json2 =sortJsonObject(json2);
        Iterator<String> i = json1.keySet().iterator();
        String temp;
        while (i.hasNext()) {
            temp = i.next();
            if(key!=null&&key.trim().equals(temp))  continue;
            else
                compareJson(json1.get(temp), json2.get(temp), temp);
        }

    }


    public static void compareJson(Object json1, Object json2, String key) {
        if (json1 instanceof JSONObject) {
//            System.out.println("this JSONObject----" + key);
            compareJson2((JSONObject) json1, (JSONObject) json2, key);
        } else if (json1 instanceof JSONArray) {
//            System.out.println("this JSONArray----" + key);
            compareJson((JSONArray) json1, (JSONArray) json2, key);
        } else if (json1 instanceof String) {
//            System.out.println("this String----" + key);
//            compareJson((String) json1, (String) json2, key);
            try {
                String json1ToStr = json1.toString();
                String json2ToStr = json2.toString();
                compareJson(json1ToStr, json2ToStr, key);
            } catch (Exception e) {
                System.out.println("转换发生异常 key:" + key);
                e.printStackTrace();
            }

        } else {
//            System.out.println("this other----" + key);
            compareJson(json1.toString(), json2.toString(), key);
        }
    }

    public static void compareJson(String str1, String str2, String key) {
        if (!str1.equals(str2)) {
            sb.append("key:" + key + ",json1:" + str1 + ",json2:" + str2);
            System.err.println("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2);
        } else {
            System.out.println("一致：key:" + key + ",json1:" + str1 + ",json2:" + str2);
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
                System.err.println("不一致：key:" + key + "  在json1和json2中均不存在");
            } else if (json1 == null) {
                System.err.println("不一致：key:" + key + "  在json1中不存在");
            } else if (json2 == null) {
                System.err.println("不一致：key:" + key + "  在json2中不存在");
            } else {
                System.err.println("不一致：key:" + key + "  未知原因");
            }

        }
    }

    public static JSONObject sortJsonObject(JSONObject obj) {
        Map map = new TreeMap();
        Iterator<String> it = obj.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                // System.out.println(value + " is JSONObject");
                map.put(key, sortJsonObject((JSONObject)value));
            } else if (value instanceof JSONArray) {
                // System.out.println(value + " is JSONArray");
                map.put(key, sortJsonArray((JSONArray)value));
            } else {
                map.put(key, value);
            }
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

 
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

    private final static String st1 = "{\"username\":\"tom\",\"age\":18,\"address\":[{\"province\":\"上海市\"},{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"}]}";
    private final static String st2 = "{username:\"tom\",age:18, address:[{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"},{\"province\":\"上海市\"}]}";
//private final static String st2 = "{username:\"tom\",age:18}";
    public static StringBuffer sb=new StringBuffer();
    public static void main(String[] args) {

        JSONObject jsonObject1 = JSONObject.parseObject(st1);
        JSONObject jsonObject2 = JSONObject.parseObject(st2);
         jsonObject1 =sortJsonObject(jsonObject1);
        jsonObject2 =sortJsonObject(jsonObject2);
        compareJson(jsonObject1, jsonObject2, null);
        System.out.println(sb.toString());

    }
}
