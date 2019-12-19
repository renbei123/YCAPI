package com.guanghe.onion.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.entity.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    public static void parseJsonToArray(JSONObject o, List<JSONObject> list) {
        if (o.containsKey("item")) {
            JSONArray items = o.getJSONArray("item");
            for (Object object : items) {
                parseJsonToArray((JSONObject) object, list);
            }
        } else {
            list.add(o);
        }
    }


    // 解析json大对象，取出里面的所有Request
    public static void toApiList(List<JSONObject> Jsonlist, List<Api> list, String creater, String label, String remarks, List<String> myApiPath_List) {



        for (JSONObject o : Jsonlist) {

            JSONObject request = o.getJSONObject("request");
            String name = o.getString("name");
//                logger.info("name---->{}",name);
            JSONArray event = o.getJSONArray("event");  // postman的脚本

            String path = request.getJSONObject("url").getString("raw").trim();

            //myApiPath_List==null 新增重复接口；myApiPath_List!=null不新增
            if (myApiPath_List != null && myApiPath_List.contains(path)) {
                continue;
            }

            Api api = new Api();
            api.setCreater(creater);
            api.setLabel(label);
            api.setRemarks(remarks);
            api.setPath(path);
            api.setMethod(request.getString("method"));
            api.setName(name);
            JSONArray headJsonArray = request.getJSONArray("header");
            Map heads=new HashMap();
            Iterator<Object> it = headJsonArray.iterator();
            while (it.hasNext()) {
                JSONObject ob = (JSONObject) it.next();
                heads.put(ob.getString("key"),ob.getString("value"));
            }
            api.setHeaders(heads.toString());

            String body = request.getJSONObject("body") != null ? request.getJSONObject("body").getString("raw") : "";
//                if (body.length()>500)
//                logger.info("body---->:{},{}", body,body.length());
            api.setBody(body);
            if (event != null) {
                logger.info("event---->{}", event.toJSONString());
                String scriptexec = event.getJSONObject(0).getJSONObject("script").getString("exec");

                String[] execs = scriptexec.split(",");
                String code = "";
                for (String s : execs) {
                    if (!s.trim().startsWith("//") && s.indexOf("pm.response.to.have.status(") != -1) {
                        code = s.substring(s.indexOf("(") + 1, s.indexOf(")"));

                    }
                }
                int assertcode = code.trim().length() == 0 ? 0 : Integer.valueOf(code);
                api.setAssert_Code(assertcode);

            }
            list.add(api);


        }
    }

}
