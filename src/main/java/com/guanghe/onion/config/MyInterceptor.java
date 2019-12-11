package com.guanghe.onion.config;


import com.guanghe.onion.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyInterceptor extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index", "/statics");

//         registry.addInterceptor(new SecurityInterceptor())
//         .addPathPatterns("/secure/*", "/admin/**", "/profile/**");

        super.addInterceptors(registry);
    }
}


//一个*：只匹配字符，不匹配路径（/）
// 两个**：匹配字符，和路径（/）
//The mapping matches URLs using the following rules:
//
//        ? matches one character
//        * matches zero or more characters
//        ** matches zero or more directories in a path
//        {spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"


//https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html