package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */
import com.guanghe.onion.dao.*;

import com.guanghe.onion.entity.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.exceptions.TemplateInputException;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ApiJPA apiJPA;
    @Autowired
    private ErrorLogJPA errorlogjpa;
    @Autowired
    private MonitorLogJPA monitorlogjpa;
    @Autowired
    private StaticsJPA staticsjpa;

    @RequestMapping("index")
    public String index(Model model) throws TemplateInputException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
//        String endDate = sdf.format(today);//当前日期
        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, -30);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String startDate = sdf.format(start);//三十天之前日期
        long apisum = apiJPA.count();
        model.addAttribute("apisum", apisum);

        int errorsOf30days=staticsjpa.errorsOf30days(startDate);
        int longExecTimeOf30days=staticsjpa.longExecTimeOf30days(startDate, Long.valueOf(5000));
        int sum_500Of30days=staticsjpa.sum_500Of30days(startDate);
        int discardApiNums=staticsjpa.discardApiNums();
        model.addAttribute("errorsOf30days", errorsOf30days);
        model.addAttribute("longExecTimeOf30days", longExecTimeOf30days);
        model.addAttribute("sum_500Of30days", sum_500Of30days);
        model.addAttribute("discardApiNums", discardApiNums);
        return "index";
    }


    @RequestMapping("/")
    public String index2() {
        return "redirect:/list";
    }


    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }


    @RequestMapping(value = "user/login_view",method = RequestMethod.GET)
    public String login_view(){
        return "login";
    }





}