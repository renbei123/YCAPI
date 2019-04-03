package com.guanghe.onion.tools;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class  StringUtil{

    @Test
    public void testFun() {
        String str1 = "{idCard=123, phonenum=1234}";
        String str2 = "{idCard=123, phonenum=1234, map={hhaha=haha}}";
        String str3 = "{idCard=123, phonenum=1234, map={hhaha=haha}, nn={en=ha}}";
        String str4 = "{nn={en=ha}, idCard=123, phonenum=1234, map={hhaha=ni, danshi={ke=shi}}}";
        Map<String, Object> mapresutl1 = (Map<String, Object>) StringToMap(str1);
        Map<String, Object> mapresutl2 = (Map<String, Object>) StringToMap(str2);
        Map<String, Object> mapresutl3 = (Map<String, Object>) StringToMap(str3);
        Map<String, Object> mapresutl4 = (Map<String, Object>) StringToMap(str4);
        System.out.println(mapresutl1.toString());
        System.out.println(mapresutl2.toString());
        System.out.println(mapresutl3.toString());
        System.out.println(mapresutl4.toString());
    }

    public static Object StringToMap(String param) {
        Map map = new HashMap();
        String str = "";
        String key = "";
        Object value = "";
        char[] charList = param.toCharArray();
        boolean valueBegin = false;
        for (int i = 0; i < charList.length; i++) {
            char c = charList[i];
            if (c == '{') {
                if (valueBegin == true) {
                    value = StringToMap(param.substring(i, param.length()));
                    i = param.indexOf('}', i) + 1;
                    map.put(key, value);
                }
            } else if (c == '=') {
                valueBegin = true;
                key = str;
                str = "";
            } else if (c == ',') {
                valueBegin = false;
                value = str;
                str = "";
                map.put(key, value);
            } else if (c == '}') {
                if (str != "") {
                    value = str;
                }
                map.put(key, value);
                return map;
            } else if (c != ' ') {
                str += c;
            }
        }
        return map;
    }


    //没有大括号，只有key=value，key=value。。。。
    public static Map<String,String> getStringToMap(String str){
        if(null == str || "".equals(str)){
            return null;
        }
        //根据&截取
        String[] strings = str.split(",");
        //设置HashMap长度
        int mapLength = strings.length;
        //判断hashMap的长度是否是2的幂。
        if((strings.length % 2) != 0){
            mapLength = mapLength+1;
        }

        Map<String,String> map = new HashMap<>(mapLength);
        //循环加入map集合
        for (int i = 0; i < strings.length; i++) {
            //截取一组字符串
            String[] strArray = strings[i].split("=");
            //strArray[0]为KEY  strArray[1]为值
            map.put(strArray[0],strArray[1]);
        }
        return map;
    }



}