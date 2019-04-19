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
import com.guanghe.onion.entity.jsonbean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CacheConfig(cacheNames = "CrsApi")
public class Crs2Controller {



    @Autowired
    private CrsMonitorLogJPA logjpa;

    @RequestMapping(value = "/testcrs",method = RequestMethod.GET)
    public jsonbean testcrs(){
        List<Object[]> list=logjpa.compareresult();
        jsonbean ben =new jsonbean();
        ben.setData(list);
        ben.setDraw(1);
        ben.setRecordsFiltered(list.size());
        ben.setRecordsTotal(list.size());
        return ben;
    }
}

