package com.guanghe.onion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commonTest {

    @org.junit.Test
    public void testDemo1() {


        String s = "[\n" +
                "                {\n" +
                "                    \"key\":\"stageId\",\n" +
                "                    \"value\":\"{{stageId}}\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\":\"subjectId\",\n" +
                "                    \"value\":\"{{subjectId}}\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\":\"publisherId\",\n" +
                "                    \"value\":\"{{publisherId}}\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\":\"semesterId\",\n" +
                "                    \"value\":\"{{semesterId}}\"\n" +
                "                }]";
        Map<String, String> map = new HashMap<String, String>();
        List list = JSON.parseArray(s, map.getClass());
        JSONArray a = JSON.parseArray(s);

        System.out.println(list.toString());
//        String s = "    pm.expect(pm.response.responseTime).to.be.below(3000);";
//        System.out.println(s.indexOf("pm.expect(pm.response.responseTime).to.be.below("));
//        System.out.println(s.indexOf("to.be.below("));
//        System.out.println(s.substring(s.indexOf("to.be.below(") + 12, s.lastIndexOf(");")));
//

    }


}
