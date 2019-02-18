package com.guanghe.onion.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    //在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        logger.info("请求路径：{}", request.getRequestURI());
        //登录不做拦截
        if(request.getRequestURI().equals("/index")
                ||request.getRequestURI().equals("/user/login_view")
                ||request.getRequestURI().equals("/user/login")
                ||request.getRequestURI().startsWith("/static")
                ||request.getRequestURI().startsWith("/templates")
                ||request.getRequestURI().startsWith("/list")

        )
        {
            return true;
        }
        //验证session是否存在
        Object obj = request.getSession().getAttribute("_session_user");
        if(obj == null)
        {
            response.sendRedirect("/user/login_view");

            return false;
        }
        return true;
    }

    @Override
    //在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    //在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}