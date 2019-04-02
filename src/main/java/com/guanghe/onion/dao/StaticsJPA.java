package com.guanghe.onion.dao;

import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.PlanApisOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface StaticsJPA extends JpaRepository<PlanApisOrder, Long> {

    /*最近30天错误统计*/
    @Query(value = "SELECT count(*) from error_log where exec_time>=?1 "
            ,  nativeQuery = true)
    List errorsOf30days( String startDate);

    /*最近30天500错误*/
    @Query(value = "SELECT count(*) from error_log where exec_time>=?1 and res_code=500"
            ,  nativeQuery = true)
    List sum_500Of30days( String startDate);

    /*最近30天300-500错误*/
    @Query(value = "SELECT count(*) from error_log where exec_time>=?1 and res_code=500"
            ,  nativeQuery = true)
    List code_errorOf30days( String startDate);


    /*最近30天超过ms毫秒的请求*/
    @Query(value = "SELECT count(*) from monitor_log where exec_time>=?1 and exec_time>=?2"
            ,  nativeQuery = true)
    List  longExecTimeOf30days( String startDate,  long ms);


}