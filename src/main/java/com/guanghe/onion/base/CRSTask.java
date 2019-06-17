package com.guanghe.onion.base;

import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.dao.CrsApiJPA;
import com.guanghe.onion.dao.CrsMonitorJPA;
import com.guanghe.onion.dao.CrsMonitorLogJPA;
import com.guanghe.onion.dao.SystemVarJPA;
import com.guanghe.onion.entity.CrsApi;
import com.guanghe.onion.entity.CrsMonitor;
import com.guanghe.onion.entity.CrsMonitorLog;
import com.guanghe.onion.entity.SystemVar;
import com.guanghe.onion.tools.StringUtil;
import com.guanghe.onion.tools.fastJsonDiff;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@Component
//@EnableAsync
public class  CRSTask {
    private final static Logger logger = LoggerFactory.getLogger("CRSTask");
    @Autowired
    private CrsApiJPA crsJPA;
    @Autowired
    private CrsMonitorJPA planjpa;
    @Autowired
    private CrsMonitorLogJPA logjpa;
    @Autowired
    private SystemVarJPA systemvarjpa;


    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    private static Map<Long, Integer> plantime = null;
    public static StringBuffer sb=new StringBuffer();
//    public static ArrayDeque<List>  compareresult = new ArrayDeque();

    public  ConcurrentLinkedQueue queue =new ConcurrentLinkedQueue();


    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @Scheduled(initialDelay = 1000 * 60 * 7, fixedDelay = 1000 * 60 * 5)
    public void runCrsCompare() {
        List<CrsMonitor> list=planjpa.findAll();

        if (plantime == null) {
            plantime = new HashMap<Long, Integer>();
            for (CrsMonitor plan : list) {
                plantime.put(plan.getId(), plan.getPlanTime());
            }
        }

        for (CrsMonitor plan : list) {
            if (!plan.getRunstatus()) continue;
            else {
                int curnumber = plantime.get(plan.getId()).intValue() - 1;
                if (curnumber > 0) {
                    plantime.put(plan.getId(), curnumber);
                    logger.info("crs轮询一次， plantime={}", plantime.toString());
                    continue;
                }else {
                    String[] dingding = isnull(plan.getDingding()) ? null : plan.getDingding().split(",");
                    logger.info("crs接口开始对比！");
                    plantime.put(plan.getId(), plan.getPlanTime());
                    compare(plan.getHost1(), plan.getHost2(), logjpa, true, dingding);
                }
            }
        }
    }

    public void compare(String cachehost, String databasehost, CrsMonitorLogJPA jpa, boolean iflog, String[] dingding) {
        getSystemVar();

        logger.info("crs对比开始时间：" + df.format(new Date()));// new Date()为获取当前系统时间

        List<CrsApi> apilist = crsJPA.findAll();

        for (CrsApi api : apilist) {
            if (!api.getStatus()) continue;

            String method = api.getMethod();
            // 把输入的parm-value，转换到sysVars
            if(api.getVar_name().trim().length()>0) {
                api.setVar_names(api.getVar_name().trim().split(","));
                api.setVar_values(api.getVar_value().trim().split(","));
                Object exesqlresult=null;
                for (int i=0;i<api.getVar_names().length;i++){
                    String sql=api.getVar_values()[i].trim();
                    // 接口里的变量如果是数组 varname[]
                    if (api.getVar_names()[i].contains("[]") ) {
                        exesqlresult = secondaryJdbcTemplate.queryForList(sql, String.class);
                        SchedulerTask2.sysVars.put(api.getVar_names()[i].trim().substring(0,api.getVar_names()[i].trim().length()-2),exesqlresult);
                    } else {
                        exesqlresult = secondaryJdbcTemplate.queryForObject(sql, String.class);
                        SchedulerTask2.sysVars.put(api.getVar_names()[i],exesqlresult);
                    }
                }
//                logger.info(" SchedulerTask2.sysVars:" +  SchedulerTask2.sysVars.toString());
            }
//            logger.info("para-value: "+SchedulerTask2.sysVars.toString());
            String path = api.getPath();
            path = (path.indexOf("{{") != -1) ? replaceSysVar(path) : path;
            logger.info("path:" + cachehost+path);
            logger.info("method:" + method);
            String body = api.getBody();
            body = (body.indexOf("{{") != -1) ? replaceSysVar(body) : body;
            logger.info("body:" + body);

            String heads = api.getHeaders();
            heads = (heads.indexOf("{{") != -1) ? replaceSysVar(heads) : heads;
//            logger.info("heads:" + heads+" end;");

            Map headers = heads.trim().length() > 0 ? (Map) StringUtil.StringToMap(heads) : null;
            logger.info("headers:" + headers.toString());

            
            Response cacheResult = send(cachehost + path, method, headers, body);

            Response databaseResult = send(databasehost + path, method, headers, body);

            //判断返回状态码是否符合预期，不符continue
            if (api.getAssert_Code() != null && (cacheResult.getStatusCode() != api.getAssert_Code() || databaseResult.getStatusCode() != api.getAssert_Code())) {
                if (!iflog) {
                    List list = new ArrayList();
                    list.add(api.getName());
                    list.add(api.getMethod());
                    list.add(path.length() > 60 ? path.substring(0, 60) : path);
                    list.add(cachehost);
                    list.add(databasehost);
                    list.add("<font color='red'>error! 返回码错误</font>  \r\n 返回状态码:cache_host=" + cacheResult.getStatusCode() + "; database_host=" + databaseResult.getStatusCode());
                    queue.offer(list);
                    logger.info("queue size:" + queue.size());

                } else {
                    CrsMonitorLog error = new CrsMonitorLog();
                    error.setApi_id(api.getId());
                    error.setHost1(cachehost);
                    error.setHost2(databasehost);
                    error.setDiffer("返回码错误 \r\n返回状态码:cache_host=" + cacheResult.getStatusCode()
                            + "; database_host=" + databaseResult.getStatusCode());
                    error.setCreatTime(df.format(new Date()));
                    CrsMonitorLog oneCrslog = jpa.save(error);

                    boolean dingsendok = Tools.CRS_sendDingMsg(method, path, cacheResult.getStatusCode(), databaseResult.getStatusCode(), "http://10.8.8.18:8081/viewError?id=" + oneCrslog.getId(), dingding);
                    if (!dingsendok)
                        logger.error("发送钉钉失败! 错误日志id={}, 发送的钉钉={}", oneCrslog.getId(), dingding.toString());
                }
                continue;
            }

            String cacheResult_txt = cacheResult.body().asString();
//            logger.info("*** cacheResult result  :" + cacheResult.getStatusCode()+"; result:"+cacheResult.asString());

            String databaseResult_txt = databaseResult.body().asString();
//            logger.error("*** databaseResult_txt  :" + databaseResult_txt);
          /*  if (!cacheResult_txt.equals(databaseResult_txt)) {
                logger.error("*** cacheResult_txt  :" + cacheResult_txt);
                logger.error("*** databaseResult_txt  :" + databaseResult_txt);
            }*/
            JSONObject cacheResult_JsonObject = null;
            JSONObject databaseResult_JsonObject = null;
            try {
                if (cacheResult_txt.trim().startsWith("[")) { //有的接口返回只是一个数组，转变成jsonobject
                    cacheResult_JsonObject = JSONObject.parseObject("{data:" + cacheResult_txt + "}");
                    databaseResult_JsonObject = JSONObject.parseObject("{data:" + databaseResult_txt + "}");
                } else {
                    cacheResult_JsonObject = JSONObject.parseObject(cacheResult_txt);
                    databaseResult_JsonObject = JSONObject.parseObject(databaseResult_txt);
                }

            } catch (Exception e) {
                logger.error("JSONObject.parseObject error:  *** response body :" + cacheResult.getBody().asString());
                e.printStackTrace();

            }

            List exceptlist=null;
            if (api.getExceptString() != null && api.getExceptString().trim().length() > 0) {
                String[] excepts = api.getExceptString().split(",");
                exceptlist = Arrays.asList(excepts);
            }

            String compare_result = fastJsonDiff.compareJson(databaseResult_JsonObject, cacheResult_JsonObject, null, exceptlist);

              /*  if(api.getExceptString()!=null&&api.getExceptString().trim().length()>0) {
                    String[] excepts = api.getExceptString().split(",");
                    for (String ex : excepts) {
                        cacheResult_txt=replaceExcept(cacheResult_txt, ex);
                        databaseResult_txt=replaceExcept(databaseResult_txt, ex);
                    }
                }*/

            if(compare_result.trim().length()==0){
                logger.info("对比结果正确：ok. \r\n");
                if (!iflog){
                    List list = new ArrayList();
                    list.add(api.getName());
                    list.add(api.getMethod());
                    list.add(path.length() > 60 ? path.substring(0, 60) : path);
                    list.add(cachehost);
                    list.add(databasehost);
                    list.add("对比结果正确：ok. 返回状态码:cache_host=" + cacheResult.getStatusCode() + ";database_host=" + databaseResult.getStatusCode());
                    queue.offer(list);
                    logger.info("queue size:" + queue.size());

                }
            } else {

//                String result = CheckText.check(cacheResult_txt, databaseResult_txt);
                compare_result=compare_result.trim().length()>5000?compare_result.trim().substring(0,4999):compare_result.trim();
                logger.info("对比结果有误：error!!! error is :"+compare_result);
                if (!iflog){
                    List list=new ArrayList();
                    list.add(api.getName());
                    list.add(api.getMethod());
                    list.add(path.length() > 60 ? path.substring(0, 60) : path);
                    list.add(cachehost);
                    list.add(databasehost);
                    list.add("<font color='red'>error!</font> \r\n 返回状态码:cache_host=" + cacheResult.getStatusCode() + ";database_host=" + databaseResult.getStatusCode() + "\r\n ;异常内容:" + compare_result);
                    queue.offer(list);
                    logger.info("queue size:" + queue.size());

                }else {

                    CrsMonitorLog error = new CrsMonitorLog();
                    error.setApi_id(api.getId());
                    error.setHost1(cachehost);
                    error.setHost2(databasehost);
//                    error.setStatus(false);
                    error.setDiffer("error! 返回状态码:cache_host=" + cacheResult.getStatusCode()
                            + ";  database_host=" + databaseResult.getStatusCode() + "\r\n 异常内容:\r\n" + compare_result);
                    error.setCreatTime(df.format(new Date()));
                    CrsMonitorLog one = jpa.save(error);

                    Tools.CRS_sendDingMsg(method, path, cacheResult.getStatusCode(), databaseResult.getStatusCode(), "http://localhost:8081/viewError?id=" + one.getId(), dingding);
                }
            }
        }  //end each for apilist
        if (!iflog) {
            List over = new ArrayList();
            over.add("over");
            queue.offer(over);
        }
    }

    public Response send(String path,String method, Map headers,String body){

        if(method.equalsIgnoreCase("GET")){
            return  given()
                    .headers(headers)
                    .get(path);
        }

        if(method.equalsIgnoreCase("POST")){
            return given()
                    .headers(headers)
                    .body(body)
                    .post(path);
        }
        if(method.equalsIgnoreCase("PUT")){
            return given()
                    .headers(headers)
                    .body(body)
                    .put(path);
        } if(method.equalsIgnoreCase("PATCH")){
            return  given()
                    .headers(headers)
                    .body(body)
                    .patch(path);
        }if(method.equalsIgnoreCase("DELETE")){
            return  given()
                    .headers(headers)
                    .body(body)
                    .delete(path);
        }if(method.equalsIgnoreCase("OPTIONS")){
            return  given()
                    .headers(headers)
                    .body(body)
                    .options(path);
        }
        return null;
    }


    public String  replaceSysVar(String content){
        // 匹配{{host}}类型的变量
        String patten="\\{{2}[\\S&&[^\\{}]]+}}";
        Pattern pattern = Pattern.compile(patten);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String name=m.group();
            String temp=name.substring(2,name.length()-2);
            String value=SchedulerTask2.sysVars.containsKey(temp)?SchedulerTask2.sysVars.get(temp).toString():null;
            content=(value==null?content:content.replace(name,value));
        }
        return content;
    }

    public boolean isnull(Object s) {
        return s == null || s.toString().trim().equals("");
    }

    public void getSystemVar() {
        if (SchedulerTask2.sysVars == null) {
            SchedulerTask2.sysVars = new HashMap();
            List<SystemVar> list = systemvarjpa.findAll();
            for (SystemVar var : list) {
                SchedulerTask2.sysVars.put(var.getName(), var.getValue());
            }
        }
    }

}


