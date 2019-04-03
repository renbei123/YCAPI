package com.guanghe.onion.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
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

    //@JSONField(format="yyyy-MM-dd HH:mm")
    //private Date createTime;
}

//JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO.
//
//        TABLE：使用一个特定的数据库表格来保存主键。
//        SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
//        IDENTITY：主键由数据库自动生成（主要是自动增长型）
//        AUTO：主键由程序控制(也是默认的,在指定主键时，如果不指定主键生成策略，默认为AUTO)