package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.PlanJPA;
import com.guanghe.onion.entity.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@CacheConfig(cacheNames = "Plan")
public class PlanController {


    @Autowired
    private PlanJPA planJPA;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/planlist",method = RequestMethod.GET)
    public String list(Model model){
        List<Plan> list=planJPA.findAll();
        model.addAttribute("planlist",list);
        return "plan_list";
    }


    @RequestMapping(value = "/planselect",method = RequestMethod.GET)
    public String apiselect(Model model){
        List<Plan> list=planJPA.findAll();
        model.addAttribute("planlist",list);
        return "plan_select";
    }

    @RequestMapping(value = "/plansave",method = RequestMethod.POST)
    public String save(Plan plan){
         planJPA.save(plan);
         return "redirect:/plan_list";
    }

    @RequestMapping(value = "/planeidt")
    public String edit(Model model,Long id)
    {
        Plan api=planJPA.findOne(id);
        model.addAttribute("plan",api);
        return "plan_edit";
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

