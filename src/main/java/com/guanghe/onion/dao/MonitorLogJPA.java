package com.guanghe.onion.dao;

import com.guanghe.onion.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonitorLogJPA extends JpaRepository<MonitorLog, Long> {

    @Query(value = "SELECT a.id,a.name,log.status_code , log.elapsed_time,log.response_size,log.isok, log.start_time\n" +
            " from monitor_log as log, api as a where a.id=log.api_id order by  log.id desc limit :length offset :start"
            ,  nativeQuery = true)
    String[][] detailLoglist(@Param("start") Long start, @Param("length") int length);


    @Query(value = "SELECT a.id,a.name,log.status_code , log.elapsed_time,log.response_size,log.isok, log.start_time \n" +
            " from monitor_log as log, api as a where a.id=log.api_id and a.name like '%'||:search||'%' \n" +
            " order by  log.id desc limit :length offset :start"
            , nativeQuery = true)
    String[][] searchLoglist(@Param("start") Long start, @Param("length") int length, @Param("search") String search);

    @Query(value = "SELECT count(*) " +
            "from monitor_log as log, api as a where a.id=log.api_id and a.name like '%'||:search||'%'"
            , nativeQuery = true)
    long searchTotal(@Param("search") String search);


    @Query(value = "SELECT count(*) from monitor_log as log, api as a where a.id=log.api_id", nativeQuery = true)
    long total();

}