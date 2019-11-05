package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.entity.Api;
import com.guanghe.onion.tools.StringUtil;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@Controller
@CacheConfig(cacheNames = "Api")
public class ApiController {

    @Autowired
    private ApiJPA apiJPA;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/apilist",method = RequestMethod.GET)
    public String list(Model model){
        List<Api> list=apiJPA.findAll();

        model.addAttribute("apilist",list);
        return "api_list";
    }




    @RequestMapping(value = "/apisave",method = RequestMethod.POST)
    public String save(Api api){
         apiJPA.save(api);
         return "redirect:/apilist";
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

    @RequestMapping(value = "/apiadd")
    public String add()
    {
        return "api_add";
    }

    @RequestMapping(value = "/apidelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        apiJPA.delete(id);
        return "redirect:/apilist";
    }

    @RequestMapping(value = "/ajaxAPI", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxAPI(HttpServletRequest request, String url, String headers, String body, String method) {

        Map Headers = (Map) StringUtil.StringToMap(headers);
        Response result = send(url, method, Headers, body);
        return result.body().asString();
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
//        public void testPageQuery()  {
//        int page=1,size=10;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(page, size, sort);
//        apiJPA.findAll(pageable);
//    }

}

