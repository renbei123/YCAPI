package com.guanghe.onion.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    //在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        logger.info("请求路径：{}", request.getRequestURI());

        HttpSession session = request.getSession();
        Object login_name = session.getAttribute("name");
        if (login_name != null) {
            return true;
        } else {
            response.sendRedirect("/index");
//            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }

    }

    @Override
    //在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），生成视图之前执行，可以修改ModelAndView
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    //在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理; 生成视图时执行，可以用来处理异常，并记录在日志中
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) throws Exception {
//        if(ex != null){
//            httpServletResponse.setStatus(httpServletResponse.SC_SERVICE_UNAVAILABLE);
//        }else{
//            super.afterCompletion(httpServletRequest, httpServletResponse, o, ex);
//        }

    }
}