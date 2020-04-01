package com.guanghe.onion.controller;

/**
 * Created by renjie on 2018/12/5.
 */

import com.guanghe.onion.dao.SystemVarJPA;
import com.guanghe.onion.entity.SystemVar;
import com.guanghe.onion.tools.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@CacheConfig(cacheNames = "SysVar")
public class SysVarController {


    @Autowired
    private SystemVarJPA systemvarjpa;
    @Autowired
    private RedisUtils redisUtil;

    //@Cacheable
    @RequestMapping(value = "/SystemVar", method = RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        String user = session.getAttribute("user").toString();
        List<SystemVar> list = systemvarjpa.findByCreaterOrderById(user);
        model.addAttribute("sysvars", list);
        return "systemVar_edit";
    }


    @RequestMapping(value = "/sysVar_delete", method = RequestMethod.POST)
    @ResponseBody
    public String del(Long id) {
        try {
            SystemVar system_var = systemvarjpa.findOne(id);
            systemvarjpa.delete(system_var);
            redisUtil.hdel(system_var.getCreater(), system_var.getName());
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    @RequestMapping(value = "/sysVarSave", method = RequestMethod.POST)
    @ResponseBody
    public String save(Long id, String varname, String value, HttpSession session) {
        SystemVar system_var = null;
        String user = session.getAttribute("user").toString().trim();

        try {
            if (id != null) { //edit
                system_var = systemvarjpa.findOne(id);
                system_var.setCreater(user);
                if (varname.equals("varname")) {
                    system_var.setName(value.trim());

                    redisUtil.hset(user, value.trim(), system_var.getValue());

                } else {

                    system_var.setValue(value.trim());
                    redisUtil.hset(user, system_var.getName(), value.trim());

                }
                systemvarjpa.saveAndFlush(system_var);

            } else {// new var
                system_var = new SystemVar();
                system_var.setCreater(user);
                if (varname.equals("varname")) {
                    system_var.setName(value.trim());
                } else {
                    system_var.setValue(value.trim());
                }
                systemvarjpa.saveAndFlush(system_var);
            }
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }


   /* @RequestMapping(value = "/sysVarSave",method = RequestMethod.POST)
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
    }*/



}

