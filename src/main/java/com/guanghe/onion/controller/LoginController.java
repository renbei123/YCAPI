package com.guanghe.onion.controller;


import com.guanghe.onion.dao.UserJPA;
import com.guanghe.onion.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


//@RestController
//@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    private UserJPA userJPA;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(UserEntity user, HttpServletRequest request)
    {
        //登录成功
        boolean flag = true;
        String result = "登录成功";
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");

        UserEntity userEntity = userJPA.findByNameAndPwd(name,pwd);

        //用户不存在
        if(userEntity == null){
            flag = false;
            result = "登录失败";}

        //登录成功
        if(flag){
            //将用户写入session
            request.getSession().setAttribute("_session_user",userEntity);
        }
        return result;
    }


}
