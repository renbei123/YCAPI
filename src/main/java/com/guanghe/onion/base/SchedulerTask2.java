package com.guanghe.onion.base;

import com.guanghe.onion.controller.UploadController;
import com.guanghe.onion.dao.*;
import com.guanghe.onion.entity.*;
import com.guanghe.onion.tools.StringUtil;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import static io.restassured.RestAssured.given;

@Component
//@EnableAsync
public class SchedulerTask2 {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss a E");

    @Autowired
    private PlanJPA planJPA;
    @Autowired
    private ApiJPA apiJPA;
    @Autowired
    private PlanApisOrderJPA planApisOrderJPA;
    @Autowired
    private ErrorLogJPA errorlogjpa;
    @Autowired
    private MonitorLogJPA monitorlogjpa;

    @Autowired
    private   SystemVarJPA systemvarjpa;

    public static Map sysVars=null;

    private final static Logger logger = LoggerFactory.getLogger("*****");

    public static Map<Long,Integer> plantime=null;

    public   void getSystemVar() {
        if (sysVars == null) {
            sysVars=new HashMap();
            List<SystemVar> list = systemvarjpa.findAll();
            for (SystemVar var : list) {
                sysVars.put(var.getName(), var.getValue());
            }
        }
    }

    //    @Async
    @Scheduled(initialDelay=1000*60*120, fixedDelay = 1000*60*120)
    public void runMonitor() {

        getSystemVar();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("轮询开始时间：" + df.format(new Date()));// new Date()为获取当前系统时间

        List<Plan> planList = planJPA.findAll();

        if (plantime==null){
            plantime=new HashMap();
            for (Plan plan:planList){
                plantime.put(plan.getId(),plan.getPlanTime());
            }
        }
        /*开始计划集合*/
            for (Plan plan:planList){
                 if (!plan.getStatus()||plan.getId()==null)  continue;
                logger.info("plan.getHost():"+plan.getHost());

                // 如果监控计划里有host，取值该host值，否则不取值
               String host=(plan.getHost()==null||plan.getHost().trim().equals("")?"":plan.getHost().trim());
               int curnumber=plantime.get(plan.getId()).intValue()-1;
               if(curnumber>0){
                   plantime.put(plan.getId(),curnumber);
                   continue;
                }

               plantime.put(plan.getId(),plan.getPlanTime());

               List<Object[]> apiList=planApisOrderJPA.getexePlanApis(plan.getId());
               /*开始某个plan里的所有api*/
               for(Object[] api:apiList){
                   if((boolean)api[11]==false)  continue;   //接口废弃
                   String apid=api[1].toString();
                   String apiname=api[2].toString();
                   String method=api[3].toString();
                   String path=api[4].toString();
                   //如果path 以 http和https 或者 {{ 开头，取值不变
                   path = (path.startsWith("http://")||path.startsWith("https://")||path.startsWith("{{"))? path : (host + path);

                   path=(path.indexOf("{{")!=-1)?replaceSysVar(path):path;
                   logger.info(apiname+" : "+path);

                   String body=api[5]==null?"":api[5].toString();
                   body=(body.indexOf("{{")!=-1)?replaceSysVar(body):body;

                   String assert_code=api[6]==null?"0":api[6].toString();
                   String assert_has_string=api[7]==null?"":api[7].toString();
                   String assert_json_check=api[8]==null?"":api[8].toString();
                   String heads=api[9]==null?"":api[9].toString();

                   heads=(heads.indexOf("{{")!=-1)?replaceSysVar(heads):heads;
                   Map headers= (Map)StringUtil.StringToMap(heads);


                   Map cookies=null;

                   long starTime=System.currentTimeMillis();//获取开始时间数

                   Response result=send(path,method,headers,body);

                   long endTime = System.currentTimeMillis();    //获取结束时间
                   long elapsetime=endTime - starTime;
                   System.out.println("运行时间：" + elapsetime + "ms");    //输出程序运行时间
                   MonitorLog monitorlog=new MonitorLog();
                   monitorlog.setApiId(Long.parseLong(apid));
                   monitorlog.setPlanId(plan.getId());
                   monitorlog.setStartTime(df.format(new Date()));
                   monitorlog.setElapsedTime(elapsetime);
                   int res_body_size=result.getBody().asString().length();
                   monitorlog.setResponseSize(res_body_size);
                   monitorlog.setStatusCode(result.statusCode());
                   StringBuffer assertlog=new StringBuffer(0);
                   ErrorLog errorlog=null;


                   if (Integer.parseInt(assert_code)!=0&&result.getStatusCode()!=Integer.parseInt(assert_code)) {

                       assertlog.append("预期返回码是:"+assert_code+"，实际返回:"+result.getStatusCode()+"\r\n");
                   }

                   String[] s= assert_has_string.split(",");
                   for (String assertStr:s){
                       if(result.getBody().asString().indexOf(assertStr.trim())==-1){

                           assertlog.append("返回的结果找不到字符串:"+assertStr);
                       }
                   }
//                   for (String assertJsonStr:s){
//                       if(result.getBody().asString().indexOf(assertStr)==-1){
//
//                           assertlog.append("返回的结果找不到字符串:"+assertStr);
//                       }
//                   }
//
                   if (assertlog.toString().length()>0){
                       monitorlog.setIsok(false);
                       errorlog=new ErrorLog();
                       errorlog.setApiId(Long.parseLong(apid));
                       errorlog.setApiName(apiname);
                       errorlog.setStartTime(df.format(starTime));
                       errorlog.setElapsedTime(elapsetime);
                       errorlog.setMethod(method);
                       errorlog.setPlanId(plan.getId());
                       errorlog.setAssert_result(assertlog.toString());
                       errorlog.setReq_body(body);
                       errorlog.setReq_header(headers.toString());
                       errorlog.setRes_code(result.getStatusCode());
                       errorlog.setRes_header(result.getHeaders().toString());
                       errorlog.setRes_size(res_body_size);
                       errorlog.setUrl(path);
                       errorlog.setRes_body(result.getBody().asString());
                       logger.info("errorlog:"+errorlog.getApiName()+"\r\n "+errorlog.getReq_body().length()+":"+errorlog.getRes_body().length()+"\r\n "+errorlog.getReq_header().length()+"\r\n "
                        +errorlog.getRes_header().length()+"\r\n "+errorlog.getAssert_result().length());
                       //logger.info("errorlog:"+errorlog.toString());
                       errorlogjpa.save(errorlog);

                   } else {
                       monitorlog.setIsok(true);
                   }
                       monitorlogjpa.save(monitorlog);

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
            String value=sysVars.containsKey(temp)?sysVars.get(temp).toString():null;
            content=(value==null?content:content.replace(name,value));
        }
        return content;
    }

}












//　　　                 @Scheduled(fixedRate = 6000)：上一次开始执行时间点后每隔6秒执行一次。
//            　　　　　　@Scheduled(fixedDelay = 6000)：上一次执行完毕时间点之后6秒再执行。
//            　　　　　　@Scheduled(initialDelay=1000, fixedRate=6000)：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次。

