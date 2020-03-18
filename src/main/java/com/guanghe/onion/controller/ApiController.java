package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.entity.Api;
import com.guanghe.onion.tools.StringUtil;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@Controller
//@CacheConfig(cacheNames = "Api")
public class ApiController {

    @Autowired
    private ApiJPA apiJPA;

    private final static Logger logger = LoggerFactory.getLogger("ApiController");

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")、、、
    @RequestMapping(value = "/apilist",method = RequestMethod.GET)
    public String list(Model model){
        List<Api> list=apiJPA.findAll();
//        logger.info(" list.count(): {}", list.size());
        model.addAttribute("apilist",list);
        return "api_list";
    }

    @RequestMapping(value = "/apidelete", method = RequestMethod.GET)
    public String delete(Long id, HttpSession session) {
        Api del_api = apiJPA.findOne(id);
        if (del_api.getCreater().equals(session.getAttribute("user"))) {
            apiJPA.delete(id);
            return "redirect:/apilist";
        } else {
            return "forward:/myerror?msg=没有权限！只能修改自己的数据";
        }


    }


    @RequestMapping(value = "/apiAdd", method = RequestMethod.POST)
    public String add(Api api) {
        apiJPA.save(api);
        return "redirect:/apilist";
    }


    @RequestMapping(value = "/apiSave", method = RequestMethod.POST)
    public String save(Api api, HttpSession session) {
        if (api.getCreater().trim().equals(session.getAttribute("user"))) {
            apiJPA.save(api);
            return "redirect:/apilist";
        } else {
            return "forward:/myerror?msg=没有权限！只能修改自己的数据";
        }

    }

    @RequestMapping(value = "/apieidt")
    public String edit(Model model,Long id)
    {
        Api api=apiJPA.findOne(id);

        if (api.getAssert_hasString()!=null)  api.setAssert_hasStringArray(api.getAssert_hasString().split(","));
        if (api.getAssert_json_path() != null && api.getAssert_json_value() != null) {
            api.setAssert_json_pathArray(api.getAssert_json_path().split(","));
            api.setAssert_json_valueArray(api.getAssert_json_value().split(","));
            model.addAttribute("assert_json_length", api.getAssert_json_pathArray().length - 1);
        } else model.addAttribute("assert_json_length", 0);

        if (api.getPlanVar_name() != null && api.getPlanVar_jsonpath() != null) {
            api.setPlanVar_nameArray(api.getPlanVar_name().split(","));
            api.setPlanVar_jsonpathArray(api.getPlanVar_jsonpath().split(","));
            model.addAttribute("plan_var_length", api.getPlanVar_nameArray().length - 1);
        } else model.addAttribute("plan_var_length", 0);
        model.addAttribute("api",api);
        return "api_edit";
    }


    @RequestMapping(value = "/apicopy")
    public String copy(Model model, Long id) {
        Api api = apiJPA.findOne(id);

        if (api.getAssert_hasString() != null) api.setAssert_hasStringArray(api.getAssert_hasString().split(","));
        if (api.getAssert_json_path() != null && api.getAssert_json_value() != null) {
            api.setAssert_json_pathArray(api.getAssert_json_path().split(","));
            api.setAssert_json_valueArray(api.getAssert_json_value().split(","));
            model.addAttribute("assert_json_length", api.getAssert_json_pathArray().length - 1);
        } else model.addAttribute("assert_json_length", 0);

        if (api.getPlanVar_name() != null && api.getPlanVar_jsonpath() != null) {
            api.setPlanVar_nameArray(api.getPlanVar_name().split(","));
            api.setPlanVar_jsonpathArray(api.getPlanVar_jsonpath().split(","));
            model.addAttribute("plan_var_length", api.getPlanVar_nameArray().length - 1);
        } else model.addAttribute("plan_var_length", 0);
        model.addAttribute("api", api);
        return "api_copy";
    }



    @RequestMapping(value = "/apiadd")
    public String toaddpage()
    {
        return "api_add";
    }



    @RequestMapping(value = "/ajaxAPI", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> ajaxAPI(String url, String headers, String body, String method, String[] varNames, String[] varValues) {

        Map Headers = (Map) StringUtil.StringToMap(headers);
        Response result = send(url, method, Headers, body);
        String response_body = result.body().asString();
        String response_header = result.headers().toString();
        Map<String, Object> response_result = new HashMap<String, Object>();

        response_result.put("body", response_body);
        response_result.put("header", response_header);
        String errormsg = null;

        logger.info("varNames:" + varNames.toString());

        if (varNames.length > 0 && varValues.length == varNames.length) {
            if (response_body.startsWith("[")) {
                for (int i = 0; i < varValues.length; i++) {
                    try {
                        if (varValues[i].startsWith("#")) {
                            if (varValues[i].startsWith("#header."))
                                varValues[i] = result.getHeader(varValues[i].substring(8).trim());
                            if (varValues[i].startsWith("#body."))
                                varValues[i] = JsonPath.parse(response_body).read("$." + varValues[i].substring(6).trim()).toString();
                        } else {
                            varValues[i] = JsonPath.parse(response_body).read("$." + varValues[i].trim()).toString();
                        }
                    } catch (Exception exception) {
//                        exception.printStackTrace();
                        errormsg = "解析json出现问题";
                    }
                }

            } else {
                for (int i = 0; i < varValues.length; i++) {
                    try {
                        if (varValues[i].startsWith("#")) {
                            if (varValues[i].startsWith("#header."))
                                varValues[i] = result.getHeader(varValues[i].substring(8).trim());
                            if (varValues[i].startsWith("#body."))
                                varValues[i] = result.jsonPath().get(varValues[i].substring(6).trim()).toString();
                        } else {

                            varValues[i] = result.jsonPath().get(varValues[i].trim()).toString();
                        }

                    } catch (Exception exception) {
//                        exception.printStackTrace();
                        errormsg = "解析json出现问题";
                    }
                }
            }
        }
        response_result.put("varNames", varNames);
        response_result.put("varValues", varValues);
        response_result.put("errormsg", errormsg);

        return response_result;

    }


    public static String replaceVar(String content, HashMap debugVars) {
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

            value = debugVars.containsKey(temp) ? debugVars.get(temp).toString() : null;

            content = (value == null ? content : content.replace(name, value));
        }
        return content;
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

    public boolean isnull(Object s) {
        return s == null || s.toString().trim().equals("");
    }

//    /**
//     * 分页查询测试
//     * @param page 传入页码，从1开始
//     * @return
//     */

//    @RequestMapping(value = "/apicurpage")
//    public List<Api> curPage(int page)
//    {
//        Api user = new Api();
//        user.setSize(2);
//        user.setSord("desc");
//        user.setPage(page);
//
//        //获取排序对象
//        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
//        //设置排序对象参数
//        Sort sort = new Sort(sort_direction, user.getSidx());
//        //创建分页对象
//        PageRequest pageRequest = new PageRequest(user.getPage() - 1,user.getSize(),sort);
//        //执行分页查询
//        return apiJPA.findAll(pageRequest).getContent();
//    }

//
//    @RequestMapping(value = "/apicurpage2")
//        public void testPageQuery()  {/*/**/*/
//        int page=1,size=10;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(page, size, sort);
//        apiJPA.findAll(pageable);
//    }

}

