package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Environment")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Environment {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private Long id;


    @Column(name = "EnvironmentName")
    private String environmentName;

    //取邮件@的前缀名字，避免中文重名
    @Column(name = "creater", length = 20)
    private String creater;

}