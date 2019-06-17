package com.guanghe.onion.base;

import com.guanghe.onion.dao.*;
import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.MonitorLog;
import com.guanghe.onion.entity.Plan;
import com.guanghe.onion.entity.SystemVar;
import com.guanghe.onion.tools.StringUtil;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@Component
//@EnableAsync
public class SchedulerTask2 {

//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss a E");

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
    private SystemVarJPA systemvarjpa;

    public static Map sysVars = null;

    //    private Map planvarmap  = new HashMap();
    private static Map<Long, Map> planvarmap = new HashMap<Long, Map>();

    private final static Logger logger = LoggerFactory.getLogger("SchedulerTask2");

    public static Map<Long, Integer> plantime = null;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    public void getSystemVar() {
        if (sysVars == null) {
            sysVars = new HashMap();
            List<SystemVar> list = systemvarjpa.findAll();
            for (SystemVar var : list) {
                sysVars.put(var.getName(), var.getValue());
            }
        }
    }

    //    @Async
    @Scheduled(initialDelay = 1000 * 60 * 5, fixedDelay = 1000 * 60 * 5)
    public void runMonitor() {

        getSystemVar();

        List<Plan> planList = planJPA.findAll();

        if (plantime == null) {
            plantime = new HashMap<Long, Integer>();
            for (Plan plan : planList) {
                plantime.put(plan.getId(), plan.getPlanTime());
            }
        }
        logger.info("api plantime={}", plantime.toString());


        /*开始计划集合*/
        for (Plan plan : planList) {
            if (!plan.getStatus() || plan.getId() == null) continue;

            int curnumber = plantime.get(plan.getId()).intValue() - 1;
            if (curnumber > 0) {
                plantime.put(plan.getId(), curnumber);
                continue;
            }

            logger.info("SchedulerTask2 执行时间：" + df.format(new Date()));// new Date()为获取当前系统时间

            plantime.put(plan.getId(), plan.getPlanTime());

            String[] dingding = isnull(plan.getDingding()) ? null : plan.getDingding().split(",");

            // 如果监控计划里有host，取值该host值，否则不取值
            String host = (plan.getHost() == null || plan.getHost().trim().equals("") ? "" : plan.getHost().trim());

            if (planvarmap.get(plan.getId()) == null) {
                planvarmap.put(plan.getId(), new HashMap());
            }


            List<Object[]> apiList = planApisOrderJPA.getexePlanApis(plan.getId());

            /*开始某个plan里的所有api*/
            for (Object[] api : apiList) {
                try {

                    if ((boolean) api[8] == false) continue;   //接口废弃
                    String apid = api[1].toString();
                    String apiname = api[2].toString();
                    String method = api[3].toString();
                    String path = api[4].toString();

                    //如果path 以 http和https 或者 {{ 开头，取值不变
                    path = (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("{{")) ? path : (host + path);

                    path = (path.contains("{{")) ? replaceSysVar(plan.getId(), path) : path;
                    logger.info(apiname + " : " + path);

                    String body = isnull(api[5]) ? "" : api[5].toString();
                    body = (body.contains("{{")) ? replaceSysVar(plan.getId(), body) : body;
                    logger.info("after body String");
                    String heads = isnull(api[6]) ? "" : api[6].toString();
                    heads = (heads.contains("{{")) ? replaceSysVar(plan.getId(), heads) : heads;
                    Map headers = (Map) StringUtil.StringToMap(heads);

                    String assert_code = isnull(api[9]) ? null : api[9].toString();
                    String assert_has_string = isnull(api[10]) ? null : api[10].toString();
                    String[] assert_json_path = isnull(api[11]) ? null : api[11].toString().split(",");
                    String[] assert_json_value = isnull(api[12]) ? null : api[12].toString().split(",");
                    String[] plan_var_name = isnull(api[13]) ? null : api[13].toString().split(",");
                    String[] plan_var_jsonpath = isnull(api[14]) ? null : api[14].toString().split(",");

                    long starTime = System.currentTimeMillis();//获取开始时间数
                    logger.info(" starTime :  {}:", starTime);
                    //发送请求  得到response
//                    logger.info("发送的数据****： path:{}; method:{},header:{};body:{}", path, method, heads, body);
                    Response result = send(path, method, headers, body);
                    result.then().extract().response().time();


                    long endTime = System.currentTimeMillis();    //获取结束时间
                    long elapsetime = endTime - starTime;
                    logger.info("运行时间：{}ms ;  statusline:{}  \r\n", elapsetime, result.getStatusLine());

                    MonitorLog monitorlog = new MonitorLog();
                    monitorlog.setApiId(Long.parseLong(apid));
                    monitorlog.setPlanId(plan.getId());
                    monitorlog.setStartTime(df.format(new Date()));
                    monitorlog.setElapsedTime(elapsetime);
                    int res_body_size = result.getBody().asString().length();
                    monitorlog.setResponseSize(res_body_size);
                    monitorlog.setStatusCode(result.statusCode());

                    StringBuilder assertlog = new StringBuilder(0);
                    ErrorLog errorlog = null;


                    //code断言
                    if (assert_code != null && result.getStatusCode() != Integer.parseInt(assert_code)) {

                        assertlog.append("预期返回码是:" + assert_code + "，实际返回:" + result.getStatusCode() + "\r\n");
                        if (Integer.parseInt(assert_code) >= 500) {
                            assertlog.append("服务器错误: code= " + assert_code + ";\r\n");
                        }
                    }


//                   string包含断言
                    if (assert_has_string != null) {
                        String[] s = assert_has_string.split(",");
                        for (String assertStr : s) {
                            if (result.getBody().asString().indexOf(assertStr.trim()) == -1) {

                                assertlog.append("返回的结果找不到字符串:" + assertStr + "\r\n");
                            }
                        }
                    }


//                   json匹配断言

                    if (assert_json_path != null && assert_json_path.length > 0) {
                        for (int i = 0; i < assert_json_path.length; i++) {
                            try {
                                String jsonvalue = result.jsonPath().get(assert_json_path[i]).toString().trim();
                                if (!jsonvalue.equals(assert_json_value[i])) {

                                    assertlog.append("Json匹配错误，" + assert_json_path[i] + " 的预期值：" + assert_json_value[i] + "；实际值=" + jsonvalue + "\r\n");
                                }
                            } catch (NullPointerException exception) {
                                logger.error("找不到输入的json表达式:" + assert_json_path[i] + ",请重新检查更新输入！\r\n");
                                assertlog.append("找不到输入的json表达式:" + assert_json_path[i] + ",请重新检查更新输入！\r\n");
                            }

                        }
                    }

                    //设置接口的变量到计划公共变量集合
                    String res_body = result.getBody().asString();

                    if (plan_var_name != null && plan_var_name.length > 0) {
                        if (res_body.startsWith("[")) {

                            for (int i = 0; i < plan_var_name.length; i++) {
                                logger.info("plan_var_name:" + plan_var_name[i] + "; api:" + path);
                                try {
                                    if (plan_var_jsonpath[i].startsWith("#")) {
                                        if (plan_var_jsonpath[i].startsWith("#header."))
                                            planvarmap.get(plan.getId()).put(plan_var_name[i], result.getHeader(plan_var_jsonpath[i].substring(8).trim()));
                                        if (plan_var_jsonpath[i].startsWith("#body."))
                                            planvarmap.get(plan.getId()).put(plan_var_name[i], JsonPath.parse(result.getBody().asString()).read("$." + plan_var_jsonpath[i].substring(6).trim()).toString());
                                    } else {
                                        planvarmap.get(plan.getId()).put(plan_var_name[i], JsonPath.parse(result.getBody().asString()).read("$." + plan_var_jsonpath[i].trim()).toString());
                                    }


                                    logger.info("计划的自定义变量：{}={};", plan_var_name[i], planvarmap.get(plan.getId()).get(plan_var_name[i]).toString());
                                } catch (NullPointerException exception) {
                                    logger.error("找不到输入的json表达式:" + plan_var_jsonpath[i] + ",请重新检查更新输入！\r\n");
                                }
                            }

                        } else {
                            for (int i = 0; i < plan_var_name.length; i++) {
                                try {
                                    if (plan_var_jsonpath[i].startsWith("#")) {
                                        if (plan_var_jsonpath[i].startsWith("#header."))
                                            planvarmap.get(plan.getId()).put(plan_var_name[i], result.getHeader(plan_var_jsonpath[i].substring(8).trim()));
                                        if (plan_var_jsonpath[i].startsWith("#body."))
                                            planvarmap.get(plan.getId()).put(plan_var_name[i], result.jsonPath().get(plan_var_jsonpath[i].substring(6).trim()).toString());
                                    } else {
                                        logger.info("varName:{}; jsonpath:{}", plan_var_name[i], plan_var_jsonpath[i]);
                                        logger.info("var_value:{}", result.jsonPath().get(plan_var_jsonpath[i].trim()).toString());
                                        planvarmap.get(plan.getId()).put(plan_var_name[i], result.jsonPath().get(plan_var_jsonpath[i].trim()).toString());
                                    }


                                    logger.info("计划的自定义变量：{}={};", plan_var_name[i], planvarmap.get(plan.getId()).get(plan_var_name[i]));
                                } catch (NullPointerException exception) {
                                    logger.error("找不到输入的json表达式:" + plan_var_jsonpath[i] + ",请重新检查更新输入！\r\n");
                                }
                            }
                        }
                    }


                    if (assertlog.toString().length() > 0) {
                        monitorlog.setIsok(false);
                        errorlog = new ErrorLog();
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

                        errorlog = errorlogjpa.save(errorlog);

                        logger.error("********* 请求失败！！！code:{}; elapsetime:{}; response:{}", result.getStatusCode(), elapsetime, result.getBody().asString());
                        logger.info("发送的数据****： path:{}; method:{},header:{};body:{}", path, method, heads, body);
                        boolean dingsendok = Tools.sendDingMsg(errorlog, "http://10.8.8.18:8081/errorlogDetail?id=" + errorlog.getId(), dingding);
                        if (!dingsendok)
                            logger.error("发送钉钉失败! 错误日志id={}; 发送的钉钉={}", errorlog.getId(), dingding.toString());
                    } else {
                        monitorlog.setIsok(true);
                    }
                    monitorlogjpa.save(monitorlog);
                    monitorlog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("error! there is has Exception :{},{}", e.getMessage(), e.toString());
                    continue;
                }
            }

        }
    }


    public Response send(String path, String method, Map headers, String body) {

        if (method.equalsIgnoreCase("GET")) {
            return given()
                    .headers(headers)
                    .get(path);
        }

        if (method.equalsIgnoreCase("POST")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .post(path);
        }
        if (method.equalsIgnoreCase("PUT")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .put(path);
        }
        if (method.equalsIgnoreCase("PATCH")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .patch(path);
        }
        if (method.equalsIgnoreCase("DELETE")) {
            if (isnull(body))
                return given()
                        .headers(headers)
                        .delete(path);
            else
                return given()
                        .headers(headers)
                        .body(body)
                        .delete(path);
        }
        if (method.equalsIgnoreCase("OPTIONS")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .options(path);
        }
        return null;
    }


    public String replaceSysVar(Long key, String content) {
        // 匹配{{host}}类型的变量
        String patten = "\\{{2}[\\S&&[^\\{{}}]]+}}";
        Pattern pattern = Pattern.compile(patten);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String name = m.group();
            String temp = name.substring(2, name.length() - 2);
            String value = null;

            //优先使用集合自定义实时变量
            if (planvarmap.get(key).containsKey(temp)) {
//                logger.info("replace vars: {}; {};", temp, planvarmap.get(key).get(temp).toString());
                value = planvarmap.get(key).containsKey(temp) ? planvarmap.get(key).get(temp).toString() : null;
            } else {

                value = sysVars.containsKey(temp) ? sysVars.get(temp).toString() : null;

            }

            content = (value == null ? content : content.replace(name, value));
        }
        return content;
    }

    public boolean isnull(Object s) {
        return s == null || s.toString().trim().equals("");
    }

}


//　　　                 @Scheduled(fixedRate = 6000)：上一次开始执行时间点后每隔6秒执行一次。
//            　　　　　　@Scheduled(fixedDelay = 6000)：上一次执行完毕时间点之后6秒再执行。
//            　　　　　　@Scheduled(initialDelay=1000, fixedRate=6000)：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次。

