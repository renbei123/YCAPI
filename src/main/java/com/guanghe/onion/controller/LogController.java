package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.LogJPA;
import com.guanghe.onion.dao.PlanJPA;
import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.okLog;
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
    private LogJPA logJPA;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/loglist",method = RequestMethod.GET)
    public String list(Model model){
        List<ErrorLog> list=logJPA.findAll();
        model.addAttribute("loglist",list);
        return "log_list";
    }




    @RequestMapping(value = "/errorLogSave",method = RequestMethod.POST)
    public String save(ErrorLog log){
         logJPA.save(log);
         return "redirect:/errorlog_list";
    }




    @RequestMapping(value = "/logdelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        logJPA.delete(id);
        return "redirect:/log_list";
    }



}

