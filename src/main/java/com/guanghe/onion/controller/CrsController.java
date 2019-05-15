package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */


import com.guanghe.onion.base.CRSTask;
import com.guanghe.onion.dao.CrsApiJPA;

import com.guanghe.onion.dao.CrsMonitorJPA;
import com.guanghe.onion.dao.CrsMonitorLogJPA;
import com.guanghe.onion.entity.CrsApi;
import com.guanghe.onion.entity.CrsMonitor;
import com.guanghe.onion.entity.CrsMonitorLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
//@CacheConfig(cacheNames = "CrsApi")
public class CrsController {


    @Autowired
    private CrsApiJPA  apiJPA;

    @Autowired
    private CRSTask task;

    @Autowired
    private CrsMonitorJPA crsmonitorjpa;

    @Autowired
    private CrsMonitorLogJPA logjpa;

    private final static Logger logger = LoggerFactory.getLogger("CrsController");
    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
    @RequestMapping(value = "/crsapilist",method = RequestMethod.GET)
    public String list(Model model){
        List<CrsApi> list=apiJPA.findAll();

        model.addAttribute("apilist",list);
        model.addAttribute("class1","treeview active");
        model.addAttribute("class2","active");
        return "crs/crsapi_list";
    }

    @RequestMapping(value = "/CRSstartMonitor",method = RequestMethod.GET)
    public String CRSstartMonitor(Model model, @RequestParam(value="state")boolean state){

        return "redirect:/crsapilist";
    }
    @RequestMapping(value = "/monitorList",method = RequestMethod.GET)
    public String monitorList(Model model){
        List list=crsmonitorjpa.findAll();
        model.addAttribute("list",list);
        model.addAttribute("class1","treeview active");
        model.addAttribute("crsmonitorclass2","active");
        return "crs/monitorList";
    }

    @RequestMapping(value = "/crsmonitorEdit",method = RequestMethod.GET)
    public String crsmonitorEdit(Model model,Long id){
        CrsMonitor crsmonitor=crsmonitorjpa.findOne(id);
        model.addAttribute("crsmonitor",crsmonitor);
        return "crs/crsmonitor_edit";
    }

    @RequestMapping(value = "/toComparePage",method = RequestMethod.GET)
    public String toComparePage(Model model,  @RequestParam(value="host1")String  host1, @RequestParam(value="host2")String  host2){
        model.addAttribute("host1",host1);
        model.addAttribute("host2",host2);
        return "crs/compare_result";
    }

    @RequestMapping(value = "/CRScompare",method = RequestMethod.GET)
    @ResponseBody
    public void CRScompare(Model model,  @RequestParam(value="host1")String  host1, @RequestParam(value="host2")String  host2){
        task.compare(host1.trim(), host2.trim(), null, false, null);
    }

    @RequestMapping(value = "/getOneresult", method = RequestMethod.GET)
    @ResponseBody
    public List getOneresult() throws InterruptedException {

        if (!task.queue.isEmpty()) {
            logger.info("queue size:"+task.queue.size());
            return (List) task.queue.poll();
        } else
            return null;
    }



    @RequestMapping(value = "/crsapisave",method = RequestMethod.POST)
    public String save(CrsApi api){
         apiJPA.save(api);
         return "redirect:/crsapilist";
    }

    @RequestMapping(value = "/crsMonitorSave",method = RequestMethod.POST)
    public String crsMonitorSave(CrsMonitor monitor){
        crsmonitorjpa.save(monitor);
        return "redirect:/monitorList";
    }


    @RequestMapping(value = "/crsapieidt")
    public String edit(Model model,Long id)
    {
        CrsApi api=apiJPA.findOne(id);
        if (api.getVar_name()!=null&&api.getVar_value()!=null) {
            api.setVar_names(api.getVar_name().split(","));
            api.setVar_values(api.getVar_value().split(","));
            model.addAttribute("length",api.getVar_name().split(",").length-1);
        }else  model.addAttribute("length",0);

        if(api.getExceptString()!=null)   api.setExcepts(api.getExceptString().split(","));
        model.addAttribute("api",api);

        return "crs/crsapi_edit";
    }

    @RequestMapping(value = "/crsmonitoradd")
    public String crsmonitoradd()
    {
        return "crs/crsmonitor_add";
    }

    @RequestMapping(value = "/crsapiadd")
    public String add()
    {
        return "crs/crsapi_add";
    }

    @RequestMapping(value = "/crsapidelete",method = RequestMethod.GET)
    public String delete(Long id)
    {
        apiJPA.delete(id);
        return "redirect:/crsapilist";
    }

    @RequestMapping(value = "/crsMonitordel",method = RequestMethod.GET)
    public String crsMonitordel(Long id)
    {
        crsmonitorjpa.delete(id);
        return "redirect:/monitorList";
    }
    @RequestMapping(value = "/crslog",method = RequestMethod.GET)
    public String crslog(Model model )
    {
        List<String[]> list=logjpa.Loglist();
        model.addAttribute("loglist",list);
        model.addAttribute("class1","treeview active");
        model.addAttribute("crslogclass","treeview active");
        return "crs/log_list";
    }

    @RequestMapping(value = "/viewError", method = RequestMethod.GET)
    public String viewError(Model model, Long id) {
        List<String[]> errordetail = logjpa.errordetail(id);
        model.addAttribute("onelog", errordetail);

        return "crs/viewError";
    }

}

