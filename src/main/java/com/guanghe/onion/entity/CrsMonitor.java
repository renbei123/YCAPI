package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "CrsMonitor")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class CrsMonitor
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "host1")
    private String host1;

    @Column(name = "host2")
    private String host2;

    @Column(name = "planName")
    private String planName;


    @Column(name = "planTime", nullable = false)
    //每5分钟为单位的频率间隔数n，， 间隔时间是 n*5 分钟
    private Integer planTime;


    @Column(name = "comment")
    private String comment;

    @Column(name = "dingding")
    private String dingding;

    @Column(name = "creater")
    private String creater;


    @Column(name = "runstatus")
    private Boolean runstatus=true;   // true: 开始运行监控   false：结束监控
   

}