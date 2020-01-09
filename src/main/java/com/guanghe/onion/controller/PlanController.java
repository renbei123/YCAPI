package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.base.SchedulerTask2;
import com.guanghe.onion.dao.ApiJPA;
import com.guanghe.onion.dao.PlanApisOrderJPA;
import com.guanghe.onion.dao.PlanJPA;
import com.guanghe.onion.entity.Api;
import com.guanghe.onion.entity.Plan;
import com.guanghe.onion.entity.PlanApisOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Controller
@CacheConfig(cacheNames = "Plan")
public class PlanController {


    @Autowired
    private PlanJPA planJPA;
    @Autowired
    private ApiJPA apiJPA;
    @Autowired
    private PlanApisOrderJPA planApisOrderJPA;


    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/planlist",method = RequestMethod.GET)
    public String list(Model model){
        List<Object[]> list=planJPA.planlist();
        model.addAttribute("planlist",list);
        return "plan_list";
    }



    @RequestMapping(value = "/planAdd",method = RequestMethod.POST)
    public String add(Plan plan) {
        Long planid = planJPA.save(plan).getId();
        if (plan.getApiIds() != null && !plan.getApiIds().equals("")) {
            String[] apis = plan.getApiIds().split(",");
            for (int i = 1; i <= apis.length; i++) {
                PlanApisOrder planapisorder = new PlanApisOrder();
                planapisorder.setApiId(Long.valueOf(apis[i - 1]));
                planapisorder.setApiOrders(i);
                planapisorder.setPlanId(planid);
                planApisOrderJPA.save(planapisorder);
            }
        }

        return "redirect:/planlist";
    }

    @RequestMapping(value = "/planEditSave2", method = RequestMethod.POST)
    @Transactional
    public String edit(Plan plan){
        List<BigInteger> orderedId=planApisOrderJPA.findApiIdByPlanId(plan.getId());
        Set<String> newIds = new HashSet<>(Arrays.asList(plan.getApiIds().split(",")));
        Iterator<BigInteger> it_orderedId = orderedId.iterator();
        while (it_orderedId.hasNext()) {

            String temp=it_orderedId.next().toString().trim();
            if (newIds.contains(temp))  newIds.remove(temp);
            else  it_orderedId.remove();
        }

        if(newIds.size()>0){
            for(String s:newIds)
              orderedId.add(new BigInteger(s.trim()));
        }
//        System.out.println("**** orderedId:"+orderedId);
        planApisOrderJPA.deleteByPlanId(plan.getId());

        for (int i=1;i<=orderedId.size();i++){
            PlanApisOrder planapisorder=new PlanApisOrder();
            planapisorder.setApiId(Long.valueOf(orderedId.get(i-1).toString()));
            planapisorder.setApiOrders(i);
            planapisorder.setPlanId(plan.getId());
            planApisOrderJPA.save(planapisorder);
        }
        planJPA.save(plan);
        if (SchedulerTask2.plantime != null)
            SchedulerTask2.plantime.put(plan.getId(), plan.getPlanTime());  //重置轮询任务里的计划执行时间段
        return "redirect:/planlist";
    }

    @RequestMapping(value = "/planEditSave", method = RequestMethod.POST)
    public String planEditSave(Plan plan, HttpSession session) {
        if (plan.getCreater().trim().equals(session.getAttribute("user"))) {
            planJPA.save(plan);
            if (SchedulerTask2.plantime != null) {
                SchedulerTask2.plantime.put(plan.getId(), plan.getPlanTime());  //重置轮询任务里的计划执行时间段
            }
            return "redirect:/planlist";
        } else {
            return "forward:/myerror?msg=没有权限！只能修改自己的数据";
        }
    }

    @RequestMapping(value = "/apiselect2")
    public String apiselect(Model model, Long id)
    {
        Plan plan=planJPA.findOne(id);
//        model.addAttribute("plan",plan);
        List<String> ids = Arrays.asList(plan.getApiIds().split(","));
        model.addAttribute("ids",ids);
        model.addAttribute("planId",id);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<Api> list = apiJPA.findAll(sort);
        model.addAttribute("apilist",list);
        return "plan_apiselect";
    }


    @RequestMapping(value = "/newPlan1", method = RequestMethod.GET)
    public String newPlan_apiSelect(Model model) {
        List<Api> list = apiJPA.findAll();
        model.addAttribute("apilist", list);
        return "api_select";
    }

    @RequestMapping(value = "/newPlan2", method = RequestMethod.POST)
    public String toAddPlaninfoPage(Model model, @RequestParam(value = "api_id", required = false) String selectedApiIds) {
//        System.out.println("apiId*******************:" + apiId);
        model.addAttribute("api_id", selectedApiIds);

        return "planinfo_new";
    }


    @RequestMapping(value = "/planedit")
    public String edit(Model model, Long id) {

        Plan plan = planJPA.findOne(id);
        model.addAttribute("plan", plan);
        return "plan_edit";
    }

    @RequestMapping(value = "/planedit2",method = RequestMethod.POST)
    public String edit2(Model model,@RequestParam(value="api_id", required=false) String apiId,long planId){
//        System.out.println("apiId*******************:"+apiId);
        model.addAttribute("api_id",apiId);
        Plan plan=planJPA.findOne(planId);
        model.addAttribute("plan",plan);
        return "plan_edit2";
    }




    @RequestMapping(value = "/plandelete",method = RequestMethod.GET)
    public String delete(Long id, HttpSession session)
    {
        Plan plan = planJPA.findOne(id);
        if (plan.getCreater().trim().equals(session.getAttribute("user"))) {
            planJPA.delete(id);
            planApisOrderJPA.deleteByPlanId(id);
            if (SchedulerTask2.plantime != null) {
                SchedulerTask2.plantime.remove(id);
            }
            return "redirect:/planlist";
        } else {
            return "forward:/myerror?msg=没有权限！只能修改自己的数据";
        }
    }

    @RequestMapping(value = "/planApiSort")
    public String planApiSort(Model model,Long id)
    {
        List<Object[]> list = planApisOrderJPA.planApiOrderlist(id);
        model.addAttribute("planApiList",list);
        model.addAttribute("planId",id);
        return "plan_apisort_list";
    }

    @RequestMapping(value = "/reorderPlanApis",method = RequestMethod.POST)
    public String reorderPlanApis(Model model, @RequestParam(value = "api_id") String apiIds, long planId) {
//        System.out.println("apiId*******************:" + apiIds);
        Plan plan = planJPA.getOne(planId);
        plan.setApiIds(apiIds);
        planJPA.save(plan);
        List<Object[]> list = planApisOrderJPA.planApiOrderlist(planId);
        model.addAttribute("planApiList", list);
        model.addAttribute("planId", planId);
        return "plan_apisort_list";
    }


    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{sessionid}")
    public String pushToWeb(@PathVariable String sessionid, String message) {
        try {
            WebSocketServer.sendInfo(message, sessionid);
        } catch (IOException e) {
            e.printStackTrace();
            return sessionid + "#" + e.getMessage();
        }
        return sessionid + "#success";
    }


/*    @RequestMapping(value = "/reorderPlanApis",method = RequestMethod.POST)
    public String reorderPlanApis(Model model,@RequestParam(value="api_id") String apiIds,long planId){
        System.out.println("apiId*******************:"+apiIds);
        String[] api_ids= apiIds.split(",");
        for (int i=1;i<=api_ids.length;i++){
            PlanApisOrder planapisorder= (PlanApisOrder) planApisOrderJPA.findByApiOrdersAndPlanId(i, planId);
            planapisorder.setApiId(Long.valueOf(api_ids[i-1]));
            planApisOrderJPA.save(planapisorder);
        }
        List<Object[]> list=planApisOrderJPA.planApiOrder(planId);
        model.addAttribute("planApiList",list);
        model.addAttribute("planId",planId);
        return "plan_apisort_list";
    }
    */

//    @Autowired
//    private EntityManager em;
//    public void testSql()
//    {
//
//        String sql = "insert t_car_mark_v2(id,car_mark,trigger_event,operate_state,gps_time,gps_longtitude,gps_latitude,gps_speed,gps_direction,gps_state)
//        values(?,?,?,?,?,?,?,?,?)";
//        Query query = em.createNativeQuery(sql);
//        query.setParameter(1,1);
//        query.setParameter(2,"ddd");
//        query.setParameter(3,1);
//        query.setParameter(4,1);
//        query.setParameter(5,"dd");
//        query.setParameter(6,"");
//        query.setParameter(7,"");
//        query.setParameter(8,"");
//        query.setParameter(9,"value");
//
//        query.executeUpdate();
//
//    }





}

