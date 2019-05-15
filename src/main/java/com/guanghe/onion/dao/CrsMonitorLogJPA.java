package com.guanghe.onion.dao;


import com.guanghe.onion.entity.CrsMonitor;
import com.guanghe.onion.entity.CrsMonitorLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrsMonitorLogJPA extends BaseJPA<CrsMonitorLog> {

    @Query(value = "SELECT log.id, log.creat_time, api.name, api.path, api.method, log.host1,log.host2, log.differ,log.api_id " +
            "from crs_monitor_log as log, crs_api as api where log.api_id=api.id ",  nativeQuery = true)
    List<String[]> Loglist();

    @Query(value = "SELECT log.creat_time, api.name, api.path, api.method, log.host1,log.host2, api.assert_code, log.differ " +
            "from crs_monitor_log as log, crs_api as api where log.api_id=api.id and log.id=:logId", nativeQuery = true)
    List<String[]> errordetail(@Param("logId") Long logId);

}