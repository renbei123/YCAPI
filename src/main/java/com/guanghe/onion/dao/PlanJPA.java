package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJPA extends JpaRepository<Plan, Long> {

}