package com.guanghe.onion.base;

import com.guanghe.onion.dao.*;
import com.guanghe.onion.entity.*;
import com.guanghe.onion.tools.CheckText;
import com.guanghe.onion.tools.StringUtil;
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
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    @Autowired
    SchedulerTask2 schedulertask2;

    public static Map<Long,Integer> plantime=null;

    public static ArrayDeque<CrsMonitorLog> compareresult = new ArrayDeque();


    @Scheduled(initialDelay=1000*60*30, fixedDelay = 1000*60*30)
    public void runCrsCompare() {
        schedulertask2.getSystemVar();
        List<CrsMonitor> list=planjpa.findAll();
        if (plantime==null){
            plantime=new HashMap();
            for (CrsMonitor plan:list){
                plantime.put(plan.getId(),plan.getPlanTime());
            }
        }

        for (CrsMonitor plan : list) {
            if (!plan.getRunstatus()) continue;
            else {
                int curnumber = plantime.get(plan.getId()).intValue() - 1;
                if (curnumber > 0) {
                    plantime.put(plan.getId(), curnumber);
                    continue;
                }else {
                    plantime.put(plan.getId(), plan.getPlanTime());
                    compare(plan.getHost1(),plan.getHost2(), logjpa, true);
                }
            }
        }
    }

    public   void compare(String cachehost, String databasehost, CrsMonitorLogJPA jpa, boolean iflog){
        schedulertask2.getSystemVar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("crs对比开始时间：" + df.format(new Date()));// new Date()为获取当前系统时间

        List<CrsApi> apilist = crsJPA.findAll();

        for (CrsApi api : apilist) {
            if (!api.getStatus()) continue;

            // 把输入的parm-value，转换到sysVars
            if(api.getVar_name().trim().length()>0) {
                api.setVar_names(api.getVar_name().trim().split(","));
                api.setVar_values(api.getVar_value().trim().split(","));
                for (int i=0;i<api.getVar_names().length;i++){
                    String sql=api.getVar_values()[i].trim();
                    String exesqlresult=secondaryJdbcTemplate.queryForObject(sql,String.class);
                    SchedulerTask2.sysVars.put(api.getVar_names()[i],exesqlresult);

                }
            }
            logger.info("para-value: "+SchedulerTask2.sysVars.toString());
                String path =api.getPath();
                path = (path.indexOf("{{") != -1) ? replaceSysVar(path) : path;

                String method=api.getMethod();
                String body = api.getBody();
                body = (body.indexOf("{{") != -1) ? replaceSysVar(body) : body;
                String heads = api.getHeaders();

                heads = (heads.indexOf("{{") != -1) ? replaceSysVar(heads) : heads;

                Map headers = heads.trim().length()>0? (Map) StringUtil.StringToMap(heads):null;

                Response cacheResult = send(cachehost+path, method, headers, body);
                String cacheResult_txt=cacheResult.body().prettyPrint();

                Response databaseResult = send(databasehost+path, method, headers, body);
                String databaseResult_txt=databaseResult.body().prettyPrint();

                if(api.getExceptString().length()>0) {
                    String[] excepts = api.getExceptString().split(",");
                    for (String ex : excepts) {
                        cacheResult_txt=replaceExcept(cacheResult_txt, ex);
                        databaseResult_txt=replaceExcept(databaseResult_txt, ex);
                    }
                }

            logger.info("api name:" + api.getName());
            logger.info("path:" + api.getPath());
            if(cacheResult_txt.equals(databaseResult_txt)){
                logger.info("对比结果正确：ok");
                if (!iflog){
                    CrsMonitorLog oklog=new CrsMonitorLog();
                    oklog.setApi_id(api.getId());
                    oklog.setHost1(cachehost);
                    oklog.setHost2(databasehost);
                    oklog.setChannel(0);
                    oklog.setStatus(true);
                    oklog.setDiffer("对比结果正确：ok.返回状态码:cache_host="+cacheResult.getStatusCode() + "; database_host="+databaseResult.getStatusCode()+"\n");
                    compareresult.offer(oklog);
                }
            } else {
                logger.info("对比结果有误：error!");
                String result = CheckText.check(cacheResult_txt, databaseResult_txt);
                CrsMonitorLog error = new CrsMonitorLog();
                error.setApi_id(api.getId());
                error.setHost1(cachehost);
                error.setHost2(databasehost);
                error.setChannel(1);
                error.setStatus(false);
                error.setDiffer("对比结果有误：error! 返回状态码:cache_host=" + cacheResult.getStatusCode()
                        + "database_host=" + databaseResult.getStatusCode() + "\n 异常内容:" + result);
                if (!iflog){
                    compareresult.offer(error);
                }else {
                    jpa.save(error);
                }
            }
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

    public Response  GET(String url, Map para, Map heads, Map cookies){
        return  given()
                .cookies(cookies)
                .headers(heads)
                .params(para)
                .get(url);

    }

    public Response  GET(String url, Map para, Map heads){
        return  given()
                .headers(heads)
                .params(para)
                .get(url);

    }

    public Response  POST(String url, Map para, Map heads, Map cookies){
        return  given()
                .cookies(cookies)
                .headers(heads)
                .params(para)
                .post(url);
    }

    public String  replaceSysVar(String content){
        // 匹配{{host}}类型的变量
        String patten="\\{{2}[\\S&&[^\\{{}}]]+}}";
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


    public String  replaceExcept(String content,String patten){
        // "key":***,
        String Patten=patten+"(.*)\n";

        Pattern pattern = Pattern.compile(Patten);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String name=m.group();
            logger.info("匹配的except内容名称***:"+name);
            content=content.replace(name,"");
        }
        return content;
    }
}


