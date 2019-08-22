package com.guanghe.onion.dao;

import com.guanghe.onion.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ErrorLogJPA extends JpaRepository<ErrorLog, Long> {
    @Query(value = "SELECT api_name, url, method, assert_result , elapsed_time,res_code,res_size, start_time ,id from error_log " +
            " order by id desc  limit :length offset :start"
            ,  nativeQuery = true)
    String[][] errorLoglist(@Param("start") Long start, @Param("length") int length);


    @Query(value = "SELECT api_name, url, method, assert_result , elapsed_time,res_code,res_size, start_time ,id from error_log " +
            " where api_name  like '%'||:search||'%'  order by id desc  limit :length offset :start"
            , nativeQuery = true)
    String[][] searcherrorLoglist(@Param("start") Long start, @Param("length") int length, @Param("search") String search);


    @Query(value = "SELECT count(*) " +
            "from error_log where api_name like '%'||:search||'%'"
            , nativeQuery = true)
    long searchTotal(@Param("search") String search);


    @Query(value = "SELECT count(*) from error_log", nativeQuery = true)
    long total();


}