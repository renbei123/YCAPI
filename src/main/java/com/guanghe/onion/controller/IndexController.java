package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.exceptions.TemplateInputException;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() throws TemplateInputException {
        return "index";
    }

}