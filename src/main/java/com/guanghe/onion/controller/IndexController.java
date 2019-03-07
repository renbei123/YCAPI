package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */
import com.guanghe.onion.dao.UserJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

@Controller
public class IndexController {
    @RequestMapping("index")
    public String index() throws TemplateInputException {
        return "index";
    }

//    @RequestMapping("/")
//    public String index3() {
//        return "redirect:/index";
//    }

    @RequestMapping(value = "user/login_view",method = RequestMethod.GET)
    public String login_view(){
        return "login";
    }

    @RequestMapping(value = "user/index",method = RequestMethod.GET)
    public String index2(){
        return "index";
    }
}