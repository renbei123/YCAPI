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
@Table(name = "Plan")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Plan
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "host")
    private String host;

    @Column(name = "planName")
    private String planName;

    @Column(name = "apiIds",  nullable = false)
    private String apiIds;

    @Column(name = "planTime", nullable = false)
    //每5分钟为单位的频率间隔数n，， 间隔时间是 n*5 分钟
    private Integer planTime;


    @Column(name = "comment")
    private String comment;

    @Column(name = "dingding")
    private String dingding;

    @Column(name = "creater")
    private String creater;


    @Column(name = "status")
    private Boolean status=true;   // true: 开始运行监控   false：结束监控
   

}