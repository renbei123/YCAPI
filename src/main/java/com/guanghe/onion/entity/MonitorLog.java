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
@Table(name = "MonitorLog")
// 对每一个接口的运行结果进行记录
public class MonitorLog extends BaseEntity
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;


    @Column(name = "planId")
    private long planId;

    @Column(name = "apiId")
    private long apiId;


    @Column(name = "result")
    //  0:error  1：ok  是否正缺执行
    private boolean result;


    @Column(name = "StatusCode")
    private byte StatusCode;

    @Column(name = "responseSize")
    private int responseSize;


    @Column(name = "execTime",columnDefinition = "COMMENT '执行时间'")
    //接口从请求到返回经过的时间
    private Date execTime;



}