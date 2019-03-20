package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "MonitorPlan")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Plan
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "planName")
    private String planName;

    @Column(name = "apiIds",  nullable = false)
    private String apiIds;

    @Column(name = "planTime", nullable = false)
    //单位是分钟,每多少分钟执行一次
    private Integer planTime;


    @Column(name = "remarks")
    private String remarks;

    @Column(name = "label")
    private String label;

    @Column(name = "creater")
    private String creater;




}