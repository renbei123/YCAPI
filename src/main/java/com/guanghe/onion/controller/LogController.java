package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.ErrorLogJPA;
import com.guanghe.onion.dao.MonitorLogJPA;
import com.guanghe.onion.dao.PlanJPA;
import com.guanghe.onion.entity.ErrorLog;

import com.guanghe.onion.entity.MonitorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@CacheConfig(cacheNames = "Logs")
public class LogController {


    @Autowired
    private ErrorLogJPA errorlogjpa;
    @Autowired
    private MonitorLogJPA monitorlogjpa;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/loglist",method = RequestMethod.GET)
    public String loglist(Model model){
        List<Object[]> list=monitorlogjpa.detailLoglist();
        model.addAttribute("loglist",list);
        return "log_list";
    }

    @RequestMapping(value = "/errorlist",method = RequestMethod.GET)
    public String errlist(Model model){
        List<Object[]> list=errorlogjpa.errorLoglist();
        model.addAttribute("list",list);
        return "errorlog_list";
    }

    @RequestMapping(value = "/errorlogDetail",method = RequestMethod.GET)
    public String errorlogDetail(Model model, Long id){
        ErrorLog errordetail=errorlogjpa.findOne(id);
        model.addAttribute("errordetail",errordetail);
        return "errorlogDetail";
    }




}

