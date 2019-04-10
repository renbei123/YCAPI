package com.guanghe.onion.dao;

import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ErrorLogJPA extends JpaRepository<ErrorLog, Long> {
    @Query(value = "SELECT api_name, url, method, assert_result , elapsed_time,res_code,res_size, start_time ,id from error_log " +
            " order by id desc "
            ,  nativeQuery = true)
    List<Object[]> errorLoglist();
}