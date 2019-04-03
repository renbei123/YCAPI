package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Api;
import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.MonitorLog;
import com.guanghe.onion.entity.PlanApisOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface StaticsJPA extends JpaRepository<MonitorLog, Long> {

    /*最近30天错误统计*/
    @Query(value = "SELECT count(*) from error_log where start_time>=?1 "
            ,  nativeQuery = true)
    List errorsOf30days( String startDate);

    /*最近30天500错误*/
    @Query(value = "SELECT count(*) from error_log where start_time>=?1 and res_code=500"
            ,  nativeQuery = true)
    List sum_500Of30days( String startDate);

    /*最近30天400-500错误*/
    @Query(value = "SELECT count(*) from error_log where start_time>=?1 and res_code>400"
            ,  nativeQuery = true)
    List code_errorOf30days( String startDate);


    /*最近30天超过ms毫秒的请求*/
    @Query(value = "SELECT count(*) from monitor_log where start_time>=:startDate and elapsed_time>=:elapsedTime"
            ,  nativeQuery = true)
    List  longExecTimeOf30days( @Param("startDate") String startDate,  @Param("elapsedTime")Long elapsedTime);


}