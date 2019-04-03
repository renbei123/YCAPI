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
    @Column(name = "id",unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;


    @Column(name = "planId")
    private long planId;

    @Column(name = "apiId")
    private long apiId;


    @Column(name = "isok")
    //  0:error  1：ok  是否正缺执行
    private boolean result;


    @Column(name = "StatusCode")
    private int StatusCode;

    @Column(name = "responseSize")
    private int responseSize;


    @Column(name = "startTime")
    //执行开始时间
    private String startTime;


    @Column(name = "elapsedTime")
    //执行接口的经历时间
    private long elapsedTime;


}