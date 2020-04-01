package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "SystemVar")
//系统环境变量，全局变量
public class SystemVar
{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value", length = 1000)
    private String value;

    @Column(name = "creater", length = 30)
    private String creater;

}