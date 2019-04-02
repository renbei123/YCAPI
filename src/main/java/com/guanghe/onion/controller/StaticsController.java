package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.ErrorLogJPA;
import com.guanghe.onion.dao.MonitorLogJPA;
import com.guanghe.onion.entity.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@CacheConfig(cacheNames = "Statics")
public class StaticsController {


    @Autowired
    private ApiJPA apiJPA;
    @Autowired
    private ErrorLogJPA errorlogjpa;
    @Autowired
    private MonitorLogJPA monitorlogjpa;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/allapiSum",method = RequestMethod.GET)
    public String allApiSum(Model model) {
        long apisum = apiJPA.count();
        model.addAttribute("apisum", apisum);
        return "index";
    }





}

