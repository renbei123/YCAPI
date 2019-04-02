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
    //    @Async
    @Scheduled(initialDelay=1000*4, fixedDelay = 1000*60*5)
    public void runMonitor() {
        getSystemVar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("轮询开始：" + df.format(new Date()));// new Date()为获取当前系统时间

        List<Plan> planList = planJPA.findAll();
        Matcher matcher1,matcher2;
            for (Plan plan:planList){

               String host=plan.getHost().trim().length()==0?sysVars.get("host").toString().trim():plan.getHost().trim();

               List<Object[]> apiList=planApisOrderJPA.getexePlanApis(plan.getId());
               for(Object[] api:apiList){
                   String apid=api[1].toString();
                   String apiname=api[2].toString();
                   String method=api[3].toString();
                   String path=api[4].toString();
                   path = (path.startsWith("http://") || path.startsWith("https://")) ? path : (host + path);
                   matcher1 = StringUtil.myconvert(path);

                   logger.info(apiname+" : "+path);
                   while (matcher1.find()) {
                       String name=matcher1.group();
                       String temp=name.substring(2,name.length()-2);
                       String value=sysVars.containsKey(temp)?sysVars.get(temp).toString():null;
                       path=(value==null?path:path.replace(name,value));
                   }

                   String body=api[5]==null?"":api[5].toString();

                   matcher2 = StringUtil.myconvert(body);
                   while (matcher2.find()) {
                       String name=matcher2.group();
                       String temp=name.substring(2,name.length()-2);
                       String value=sysVars.containsKey(temp)?sysVars.get(temp).toString():null;
                       body=(value==null?body:body.replace(name,value));
                   }
                   String assert_code=api[6].toString();
                   String assert_has_string=api[7]==null?"":api[7].toString();
                   String assert_json_check=api[8]==null?"":api[8].toString();
                   String heads=api[9]==null?"":api[9].toString();

                   matcher1 = StringUtil.myconvert(heads);
                   while (matcher1.find()) {
                       String name=matcher1.group();
                       String temp=name.substring(2,name.length()-2);
                       logger.info("name:"+temp);
                       String value=sysVars.containsKey(temp)?sysVars.get(temp).toString():null;
                       logger.info("value:"+value);
                       heads=(value==null?heads:heads.replace(name,value));

                   }

                   Map headers= (Map)StringUtil.StringToMap(heads);
                   Response result=null;
                   Map cookies=null;
                   Date execTime=new Date();
                   long starTime=System.currentTimeMillis();//获取开始时间
                   if(method.equals("GET")){
                       result=given()
                               .headers(headers)
                               .get(path);;
                   }

                   if(method.equals("POST")){
                       result=given()
                               .headers(headers)
                               .body(body)
                               .post(path);;
                   }
                   if(method.equals("PUT")){
                       result=given()
                               .headers(headers)
                               .body(body)
                               .put(path);;
                   } if(method.equals("PATCH")){
                       result=given()
                               .headers(headers)
                               .body(body)
                               .patch(path);;
                   }if(method.equals("DELETE")){
                       result=given()
                               .headers(headers)
                               .body(body)
                               .delete(path);;
                   }if(method.equals("OPTIONS")){
                       result=given()
                               .headers(headers)
                               .body(body)
                               .options(path);;
                   }

                   long endTime = System.currentTimeMillis();    //获取结束时间
                   System.out.println("运行时间：" + (endTime - starTime) + "ms");    //输出程序运行时间
                   MonitorLog monitorlog=new MonitorLog();
                   monitorlog.setApiId(Long.parseLong(apid));
                   monitorlog.setPlanId(plan.getId());
                   monitorlog.setExecTime(endTime - starTime);
                   int res_body_size=result.getBody().asString().length();
                   monitorlog.setResponseSize(res_body_size);
                   monitorlog.setStatusCode(result.statusCode());
                   StringBuffer assertlog=new StringBuffer(0);
                   ErrorLog errorlog=null;

                   String[] s= assert_has_string.split(",");
                   if (result.getStatusCode()!=Integer.parseInt(assert_code)) {

                       assertlog.append("预期返回码是:"+assert_code+"，实际返回:"+result.getStatusCode());
                   }
                   for (String assertStr:s){
                       if(result.getBody().asString().indexOf(assertStr)==-1){

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
                       monitorlog.setResult(false);
                       errorlog=new ErrorLog();
                       errorlog.setApiId(Long.parseLong(apid));
                       errorlog.setApiName(apiname);
                       errorlog.setExecTime(df.format(starTime));
                       errorlog.setMethod(method);
                       errorlog.setPlanId(plan.getId());
                       errorlog.setAssert_result(assertlog.toString());
                       errorlog.setReq_body(body);
                       errorlog.setReq_header(headers.toString());
                       errorlog.setRes_code(result.getStatusCode());
                       errorlog.setRes_header(result.getHeaders().toString());
                       errorlog.setRes_size(String.valueOf(res_body_size));
                       errorlog.setUrl(path);
                       errorlog.setRes_body(result.getBody().toString());
                       errorlogjpa.save(errorlog);

                   } else
                       monitorlog.setResult(true);
                       monitorlogjpa.save(monitorlog);

               }
           }
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

    public void getSystemVar() {
        if (sysVars == null) {
            sysVars=new HashMap();
            List<SystemVar> list = systemvarjpa.findAll();
            for (SystemVar var : list) {
                sysVars.put(var.getName(), var.getValue());
            }
        }
    }



}












//　　　                 @Scheduled(fixedRate = 6000)：上一次开始执行时间点后每隔6秒执行一次。
//            　　　　　　@Scheduled(fixedDelay = 6000)：上一次执行完毕时间点之后6秒再执行。
//            　　　　　　@Scheduled(initialDelay=1000, fixedRate=6000)：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次。

//
//    @Async
//    @Scheduled(fixedDelay = 6000)  //间隔1秒
//    public void first() throws InterruptedException {
//        System.out.println("第一个定时任务开始 : " +  dateFormat.format(new Date()) + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//        Thread.sleep(1000 * 10);
//    }
//
//    @Async
//    @Scheduled(fixedDelay = 6000)
//    public void second() {
//        System.out.println("第二个定时任务开始 : " +  dateFormat.format(new Date()) + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//    }
