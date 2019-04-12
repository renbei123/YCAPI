package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */


import com.guanghe.onion.base.CRSTask;
import com.guanghe.onion.base.SchedulerTask2;
import com.guanghe.onion.dao.CrsApiJPA;

import com.guanghe.onion.entity.CrsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@CacheConfig(cacheNames = "CrsApi")
public class CrsApiController {


    @Autowired
    private CrsApiJPA  apiJPA;

    @Autowired
    private CRSTask task;


    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/crsapilist",method = RequestMethod.GET)
    public String list(Model model){
        List<CrsApi> list=apiJPA.findAll();

        model.addAttribute("state",CRSTask.state);
        model.addAttribute("apilist",list);
        return "crsapi_list";
    }

    @RequestMapping(value = "/crscompare",method = RequestMethod.GET)
    public String crscompare(Model model, @RequestParam(value="state")boolean state){
        CRSTask.state=state;
        return "redirect:/crsapilist";
    }

    @RequestMapping(value = "/crsapisave",method = RequestMethod.POST)
    public String save(CrsApi api){
         apiJPA.save(api);
         return "redirect:/crsapilist";
    }

    @RequestMapping(value = "/crsapieidt")
    public String edit(Model model,Long id)
    {
        CrsApi api=apiJPA.findOne(id);
        if (api.getVar_name()!=null)  api.setVar_names(api.getVar_name().split(","));
        if(api.getVar_value()!=null)   api.setVar_values(api.getVar_value().split(","));

        if(api.getExceptString()!=null)   api.setExcepts(api.getExceptString().split(","));
        model.addAttribute("api",api);
        model.addAttribute("length",api.getVar_name().split(",").length-1);
        return "crsapi_edit";
    }

    @RequestMapping(value = "/crsapiadd")
    public String add()
    {
        return "crsapi_add";
    }

    @RequestMapping(value = "/crsapidelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        apiJPA.delete(id);
        return "redirect:/crsapilist";
    }

    @RequestMapping(value = "/crsdebug")
    public String crsdebug(@RequestParam(value="cachehost") String cachehost,@RequestParam(value="databasehost") String databasehost )
    {   task.compare(cachehost,databasehost);
        return "redirect:/crsapilist";
    }


}

