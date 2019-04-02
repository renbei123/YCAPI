package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "SystemVar")
//系统环境变量，全局变量
public class SystemVar
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "value",length = 1000)
    private String value;



}