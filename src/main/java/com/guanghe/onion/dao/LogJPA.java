package com.guanghe.onion.dao;

import com.guanghe.onion.entity.ErrorLog;
import com.guanghe.onion.entity.Plan;
import com.guanghe.onion.entity.okLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogJPA extends JpaRepository<ErrorLog, Long> {

}