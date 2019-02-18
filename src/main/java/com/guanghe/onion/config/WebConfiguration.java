package com.guanghe.onion.config;

import com.guanghe.onion.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

/**
     * 拦截器
 * 其实以前都是继承WebMvcConfigurerAdapter类 不过springBoot2.0以上 WebMvcConfigurerAdapter 方法过时，有两种替代方案：
 * 1、继承WebMvcConfigurationSupport
 * 2、实现WebMvcConfigurer
 * 但是继承WebMvcConfigurationSupport会让Spring-boot对mvc的自动配置失效。根据项目情况选择。现在大多数项目是前后端分离，
 * 并没有对静态资源有自动配置的需求所以继承WebMvcConfigurationSupport也未尝不可。
 * ---------------------
 * */
    @Autowired
    private SessionInterceptor interceptor;

   /**
     * 重写添加拦截器方法并添加配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(interceptor).addPathPatterns("/**");
        registry.addInterceptor(interceptor).addPathPatterns("/aa");
    }


    /**
     * 修改自定义消息转换器
     * @param converters 消息转换器列表
     */
   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //调用父类的配置
        WebMvcConfigurer.super.configureMessageConverters(converters);
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //修改配置返回内容的过滤
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter);

        WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
        WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
        DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
        WriteMapNullValue：是否输出值为null的字段,默认为false。

    }*/
}