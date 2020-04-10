package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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

    @Column(name = "apiIds", nullable = false, length = 5000)
    private String apiIds;

    @Column(name = "planTime", nullable = false)
    //每5分钟为单位的频率间隔数n，， 间隔时间是 n*5 分钟
    private Integer planTime;


    @Column(name = "comment")
    private String comment;

    @Column(name = "dingding", length = 5000)
    private String dingding;

    //取邮件@的前缀名字，避免中文重名
    @Column(name = "creater", length = 20)
    private String creater;

    @Column(name = "status")
    private Boolean status = true;   // true: 开始运行监控   false：结束监控

    //环境变量id
    @Column(name = "env_Id")
    private Long env_Id;
}