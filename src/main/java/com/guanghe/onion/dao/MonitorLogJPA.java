package com.guanghe.onion.dao;

import com.guanghe.onion.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitorLogJPA extends JpaRepository<MonitorLog, Long> {

    @Query(value = "SELECT a.id,a.name,log.status_code , log.elapsed_time,log.response_size,log.isok, log.start_time " +
            " from monitor_log as log, api as a where a.id=log.api_id order by  log.id desc "
            ,  nativeQuery = true)
    List<Object[]> detailLoglist();

}