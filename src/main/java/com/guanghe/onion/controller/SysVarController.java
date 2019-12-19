package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.SystemVarJPA;
import com.guanghe.onion.entity.SystemVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@CacheConfig(cacheNames = "SysVar")
public class SysVarController {


    @Autowired
    private SystemVarJPA systemvarjpa;

    //@Cacheable
    @RequestMapping(value = "/SystemVar",method = RequestMethod.GET)
    public String list(Model model){
        List<SystemVar> list=systemvarjpa.findAllByOrderById();
        model.addAttribute("sysvars",list);
        return "systemVar_edit";
    }


    @RequestMapping(value = "/sysVarSave",method = RequestMethod.POST)
    public String list(String[] id, String[] varname, String[] value, Model model, HttpSession session) {

        if (session.getAttribute("user").toString().equals("renjie")) {
            List<SystemVar> userList = new ArrayList<SystemVar>();
            for (int i = 0; i < varname.length; i++) {
                SystemVar var = null;
                if (id[i] != null && !id[i].equals("")) {
                    var = systemvarjpa.findOne(Long.valueOf(id[i]));
                }
                if ((id[i] == null || id[i].equals("")) && varname[i].trim().length() == 0) {
                    continue;
                } else
                    var = new SystemVar();
                var.setName(varname[i]);
                var.setValue(value[i]);
                userList.add(var);
            }
            systemvarjpa.deleteAll();
            systemvarjpa.save(userList);
            return "redirect:SystemVar";
        } else {
            return "forward:/myerror?msg=没有权限!";
        }



    }



}

