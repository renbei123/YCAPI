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
@Table(name = "errorLog")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class okLog extends BaseEntity
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;


    @Column(name = "planId")
    private Long planId;

    @Column(name = "apiId")
    private Long apiId;


    @Column(name = "status")
    // 是否请求全部成功
    private Boolean status;

    @Column(name = "execTime")
    //执行时间
    private Date execTime;



}