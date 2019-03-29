package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "PlanApisOrder")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class PlanApisOrder
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "apiOrders")
    private Integer apiOrders;

    @Column(name = "apiId",  nullable = false)
    private Long apiId;

    @Column(name = "planId", nullable = false)
    private Long planId;


}