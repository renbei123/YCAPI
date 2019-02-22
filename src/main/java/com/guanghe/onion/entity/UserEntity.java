package com.guanghe.onion.entity;


import javax.persistence.*;
import java.io.Serializable;

import com.guanghe.onion.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "t_user")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class UserEntity extends BaseEntity
{

    @Id
    @Column(name = "t_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_age")
    private int age;

    @Column(name = "t_address")
    private String address;

    @Column(name = "t_pwd")
    private String pwd;

}