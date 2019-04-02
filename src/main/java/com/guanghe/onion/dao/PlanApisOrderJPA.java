package com.guanghe.onion.dao;

import com.guanghe.onion.entity.Plan;
import com.guanghe.onion.entity.PlanApisOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

public interface PlanApisOrderJPA extends JpaRepository<PlanApisOrder, Long> {
    @Query(value = "SELECT pao.api_orders ,pao.api_id , a.name ,a.method, a.path, a.label from" +
            " plan_apis_order pao, api a where pao.plan_id=:planId and  pao.api_id=a.id order by api_orders "
            ,  nativeQuery = true)
    List<Object[]> planApiOrder(@Param("planId") Long planId);

    @Query(value = "SELECT api_id  from plan_apis_order  where plan_id=:planId ",  nativeQuery = true)
    List<BigInteger> findApiIdByPlanId(@Param("planId") Long planId);

//@Transactional
    void deleteByPlanId( Long planId);

    PlanApisOrder findByApiOrdersAndPlanId(Integer order, Long PlanId);

    @Query(value = "SELECT pao.api_orders ,pao.api_id , a.name ,a.method, a.path, a.body,  a.assert_code,a.assert_has_string," +
            "       a.assert_json_check,a.headers, a.label,a.status from " +
            "           plan_apis_order pao, api a where pao.plan_id=:planId and  pao.api_id=a.id and a.status=true order by api_orders"
            ,  nativeQuery = true)
    List<Object[]> getexePlanApis(@Param("planId") Long planId);


}