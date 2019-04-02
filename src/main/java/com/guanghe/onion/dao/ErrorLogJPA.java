package com.guanghe.onion.dao;

import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.MonitorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogJPA extends JpaRepository<ErrorLog, Long> {

}