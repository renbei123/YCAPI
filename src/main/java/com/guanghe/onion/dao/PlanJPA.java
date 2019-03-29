package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanJPA extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT id, plan_name,plan_time,dingding,creater,comment,status from plan "
            ,  nativeQuery = true)
    List<Object[]> planlist();


}