package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.base.PageData;
import com.guanghe.onion.dao.ErrorLogJPA;
import com.guanghe.onion.dao.MonitorLogJPA;
import com.guanghe.onion.entity.ErrorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CacheConfig(cacheNames = "Logs")
public class LogController {


    @Autowired
    private ErrorLogJPA errorlogjpa;
    @Autowired
    private MonitorLogJPA monitorlogjpa;

    //@Cacheable
    //@Cacheable(cacheNames="users", condition="#result.name.length < 32")
//    @RequestMapping(value = "/loglist",method = RequestMethod.GET)
//    public String loglist(Model model ){
//        List<Object[]> list=monitorlogjpa.detailLoglist();
//        model.addAttribute("loglist",list);
//        model.addAttribute("logclass","treeview active");
//        model.addAttribute("logclass2","active");
//        return "log_list";
//    }


    @RequestMapping(value = "/loglist",method = RequestMethod.GET)
    public String loglist(Model model) {

        model.addAttribute("logclass","treeview active");
        model.addAttribute("logclass2","active");
        return "log_list2";
    }

    @RequestMapping(value = "/loglist2", method = RequestMethod.GET)
    @ResponseBody
    public Object loglistPage(Model model, @RequestParam(value = "draw") int draw,
                              @RequestParam(value = "start") long start,
                              @RequestParam(value = "length") int length,
                              @RequestParam(value = "search[value]") String search
//                              @RequestParam(value = "order[0][column]") String order
//                              @RequestParam(value="order") String[][] order
    ) {

        String[][] data = null;
        long total;
        if (search != null && !search.equals("")) {

            data = monitorlogjpa.searchLoglist(start, length, search);
            total = monitorlogjpa.searchTotal(search);
        } else {
            data = monitorlogjpa.detailLoglist(start, length);
            total = monitorlogjpa.total();
        }

//        System.out.println("start:"+start);

        PageData pagedata = new PageData();
        pagedata.setData(data);
        pagedata.setDraw(draw);
        pagedata.setRecordsTotal(total);
        pagedata.setRecordsFiltered(total);
//        String responseData = JSON.toJSONString(pagedata);
//        System.out.println(responseData);
        return pagedata;
    }


    @RequestMapping(value = "/errorlist",method = RequestMethod.GET)
    public String errlist(Model model){
        model.addAttribute("logclass","treeview active");
        model.addAttribute("errorlogclass","active");
        return "errorlog_list";
    }

    @RequestMapping(value = "/errorlist2", method = RequestMethod.GET)
    @ResponseBody
    public Object errlist2(Model model,
                           @RequestParam(value = "draw") int draw,
                           @RequestParam(value = "start") long start,
                           @RequestParam(value = "length") int length,
                           @RequestParam(value = "search[value]") String search
//                           @RequestParam(value = "order[0][column]") String order
    ) {
        String[][] data = null;
        long total;
        if (search != null && !search.equals("")) {

            data = errorlogjpa.searcherrorLoglist(start, length, search);
            total = errorlogjpa.searchTotal(search);
        } else {
            data = errorlogjpa.errorLoglist(start, length);
            total = errorlogjpa.total();
        }

        PageData pagedata = new PageData();
        pagedata.setData(data);
        pagedata.setDraw(draw);
        pagedata.setRecordsTotal(total);
        pagedata.setRecordsFiltered(total);

        return pagedata;
    }


    @RequestMapping(value = "/errorlogDetail",method = RequestMethod.GET)
    public String errorlogDetail(Model model, Long id){
        ErrorLog errordetail=errorlogjpa.findOne(id);
        model.addAttribute("errordetail",errordetail);
        return "errorlogDetail";
    }




}

