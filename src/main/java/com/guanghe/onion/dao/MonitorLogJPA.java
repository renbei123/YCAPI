package com.guanghe.onion.dao;

import com.guanghe.onion.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitorLogJPA extends JpaRepository<MonitorLog, Long> {

    @Query(value = "SELECT a.name,log.elapsed_time,log.exec_time,log.response_size,log.isok, log.status_code from monitor_log as log, api as a"
            ,  nativeQuery = true)
    List<Object[]> detailLoglist();

}