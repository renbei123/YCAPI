package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */
import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.UserJPA;

import com.guanghe.onion.entity.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

@Controller
public class IndexController {
    @RequestMapping("index")
    public String index() throws TemplateInputException {
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
//    @RequestMapping("/")
//    public String index3() {
//        return "redirect:/index";
//    }

    @RequestMapping(value = "user/login_view",method = RequestMethod.GET)
    public String login_view(){
        return "login";
    }



    @Autowired
    private ApiJPA apiJPA;



}