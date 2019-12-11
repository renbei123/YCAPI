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
    public static void toApiList(List<JSONObject> Jsonlist, List<Api> list, String creater) {
        for (JSONObject o : Jsonlist) {

            JSONObject request = o.getJSONObject("request");
            String name = o.getString("name");
//                logger.info("name---->{}",name);
            JSONArray event = o.getJSONArray("event");  // postman的脚本


            Api api = new Api();

            String path = request.getJSONObject("url").getString("raw");
//                String host = request.getJSONObject("url").getString("host");

//            if (path.indexOf("{{host}}") != -1) {
//                path = path.replace("{{host}}", "");
//            }
//            if (path.indexOf("{{host2}}") != -1) {
//                path = path.replace("{{host2}}", "");
//            }
//            int a;
//            if ((path.indexOf("http://")) != -1) {
//                path = path.replace("http://", "");
//                path = path.substring(path.indexOf("/"));
//            }
//            if ((path.indexOf("https://")) != -1) {
//                path = path.replace("https://", "");
//                path = path.substring(path.indexOf("/"));
//            }

            api.setCreater(creater);

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
            api.setLabel("system");
            String body = request.getJSONObject("body").getString("raw");
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
