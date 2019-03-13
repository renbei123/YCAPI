package com.guanghe.onion.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.controller.UploadController;
import com.guanghe.onion.entity.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class JsonUtil {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public  static void parseJsonToArray(JSONObject o, List<JSONObject> list){
        if (o.containsKey("item")){
            JSONArray items=o.getJSONArray("item");
              for (Object object: items){
                  parseJsonToArray((JSONObject)object,list);
              }
            }
        else {
            list.add(o);
        }
    }

    public static void toApiList( List<JSONObject> Jsonlist,List<Api> list){
        for (JSONObject o:Jsonlist) {

                JSONObject request = o.getJSONObject("request");
                String name = o.getString("name");
                logger.info("name---->{}",name);
                JSONArray event = o.getJSONArray("event");


                Api api = new Api();

                String path = request.getJSONObject("url").getString("raw");
                String host = request.getJSONObject("url").getString("host");

                if (path.indexOf("{{host}}") != -1) {
                    path = path.replace("{{host}}", "");
                }
                if (path.indexOf("{{host2}}") != -1) {
                    path = path.replace("{{host2}}", "");
                }
                int a;
                if ((path.indexOf("http://")) != -1) {
                    path = path.replace("http://", "");
                    path = path.substring(path.indexOf("/"));
                }
                if ((path.indexOf("https://")) != -1) {
                    path = path.replace("https://", "");
                    path = path.substring(path.indexOf("/"));
                }
                api.setPath(path);
                api.setMethod(request.getString("method"));
                api.setName(name);
                api.setHeaders(request.getString("header"));
                api.setLabel("system");
                String body=request.getJSONObject("body").getString("raw");
                if (body.length()>500)
                logger.info("body---->:{},{}", body,body.length());
                api.setBody(body);

                if (event!=null) {
                    logger.info("event---->{}", event.toJSONString());
                    String scriptexec = event.getJSONObject(0).getJSONObject("script").getString("exec");

                    String[] execs = scriptexec.split(",");
                    String code = "";
                    for (String s : execs) {
                        if (!s.trim().startsWith("//") && s.indexOf("pm.response.to.have.status(") != -1) {
                            code = s.substring(s.indexOf("(") + 1, s.indexOf(")"));

                        }
                    }
                    api.setAssert_Code(code);
                }
                list.add(api);


        }
    }

}
