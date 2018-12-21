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
    @RequestMapping("/")
    public String index() throws TemplateInputException {
        return "index";
    }


}