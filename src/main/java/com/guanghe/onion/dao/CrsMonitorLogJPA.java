package com.guanghe.onion.dao;


import com.guanghe.onion.entity.CrsMonitor;
import com.guanghe.onion.entity.CrsMonitorLog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CrsMonitorLogJPA extends BaseJPA<CrsMonitorLog> {

    @Query(value = "SELECT log.id, api.name, api.path, api.method, log.host1,log.host2, log.differ,log.api_id,log.status\n" +
            "from crs_monitor_log as log, crs_api as api where log.api_id=api.id ",  nativeQuery = true)
    List<Object[]> Loglist();

    @Query(value = "SELECT log.id, api.name, api.path, api.method, log.host1,log.host2, log.differ,log.api_id,log.status\n" +
            "from crs_monitor_log as log, crs_api as api where log.api_id=api.id ",  nativeQuery = true)
    List<Object[]> compareresult();

}