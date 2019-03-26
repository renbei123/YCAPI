package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.PlanJPA;
import com.guanghe.onion.entity.Api;
import com.guanghe.onion.entity.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@CacheConfig(cacheNames = "Plan")
public class PlanController {


    @Autowired
    private PlanJPA planJPA;
    @Autowired
    private ApiJPA apiJPA;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/planlist",method = RequestMethod.GET)
    public String list(Model model){
        List<Object[]> list=planJPA.planlist();
        model.addAttribute("planlist",list);
        return "plan_list";
    }



    @RequestMapping(value = "/plansave",method = RequestMethod.POST)
    public String save(Plan plan){
         planJPA.save(plan);
         return "redirect:/planlist";
    }

    @RequestMapping(value = "/planedit")
    public String edit(Model model,Long id)
    {
        Plan plan=planJPA.findOne(id);
        model.addAttribute("plan",plan);
        List<String> ids = Arrays.asList(plan.getApiIds().split(","));
        model.addAttribute("ids",ids);
        model.addAttribute("planId",id);
        List<Api> list=apiJPA.findAll();
        model.addAttribute("apilist",list);
        return "plan_edit";
    }

    @RequestMapping(value = "/planedit2",method = RequestMethod.POST)
    public String edit2(Model model,@RequestParam(value="api_id", required=false) String apiId,long planId){
        System.out.println("apiId*******************:"+apiId);
        model.addAttribute("api_id",apiId);
        Plan plan=planJPA.findOne(planId);
        model.addAttribute("plan",plan);
        return "plan_edit2";
    }


    @RequestMapping(value = "/planadd")
    public String add()
    {
        return "plan_add";
    }

    @RequestMapping(value = "/plandelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        planJPA.delete(id);
        return "redirect:/planlist";
    }



}

