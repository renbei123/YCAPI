package com.guanghe.onion.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
            if (!json2.containsKey(key)) {
                CRSTask.sb.append("不一致key:" + key + ",json1有该key， json2没有");
                continue;
            } else
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

        } else if (json1 == null) {
            if (json2 != null) CRSTask.sb.append("key为（" + key + "）的json1的值为null，json2的值不为null");
        } else {
            compareJson(json1.toString(), json2.toString(), key);
        }
    }

    public static void compareJson(String str1, String str2, String key) {
        if (!str1.equals(str2)) {
            CRSTask.sb.append("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2+";");
//            System.err.println("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2);
//            logger.error("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2+";");
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


    public static void main(String[] args) {


        String jons1 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXiTZ4Q=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false,\"jump\":null},{\"body\":\"B\",\"correct\":true,\"jump\":null},{\"body\":\"C\",\"correct\":false,\"jump\":null}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";
        String jons2 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXiTZ4Q=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false},{\"body\":\"B\",\"correct\":true},{\"body\":\"C\",\"correct\":false}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";

        JSONObject jsonObject1 = JSONObject.parseObject(jons1);
        JSONObject jsonObject2 = JSONObject.parseObject(jons2);
        JSONObject jsonObject11 = sortJsonObject(jsonObject1);
        JSONObject jsonObject22 = sortJsonObject(jsonObject2);
        List list = null;
        System.out.println(compareJson(jsonObject11, jsonObject22, list));
//        System.out.println(JSONObject.toJSONString(jsonObject11, SerializerFeature.WriteMapNullValue));
//        System.out.println(jsonObject22.toString());
//        compareJson(jsonObject11, jsonObject22, null);
    }


}
