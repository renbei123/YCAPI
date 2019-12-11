package com.guanghe.onion.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Component
public class AuthorityAspect {

    static Logger logger = LoggerFactory.getLogger(AuthorityAspect.class);

    @Pointcut("execution(public * com.guanghe.onion.controller.*.*(..))")
    public void ModifyAuthority() {
    }

    @Before("ModifyAuthority()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容
/*        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));*/

    }

    @AfterReturning(returning = "ret", pointcut = "ModifyAuthority()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
//        logger.info("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("ModifyAuthority()")
    public void throwss(JoinPoint jp) {
//        logger.info("方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("ModifyAuthority()")
    public void after(JoinPoint jp) {
//        logger.info("方法最后执行.....");
    }

    @Around("ModifyAuthority()")
    public Object arround(ProceedingJoinPoint pjp) {
//        logger.info("方法环绕start.....");
        try {
            Object o = pjp.proceed();
//            logger.info("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}


//around > before > around > after > afterReturning

//   例: execution(* com.jiuxian..service.*.*(..))
//           execution 表达式的主体
//        第一个* 代表任意的返回值
//        com.jiuxian aop所横切的包名
//        包后面.. 表示当前包及其子包
//        第二个* 表示类名，代表所有类
//        .*(..) 表示任何方法,括号代表参数 .. 表示任意参数
//
//        例: execution(* com.jiuxian..service.*Service.add*(String))
//        表示： com.jiuxian 包及其子包下的service包下，类名以Service结尾，方法以add开头，参数类型为String的方法的切点。
