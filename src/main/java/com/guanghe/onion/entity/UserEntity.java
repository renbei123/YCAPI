package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity(name = "t_user")
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


// @Entity注解表示该类是一个实体类，表的名称为注解中name的值，如果不配置name，表名默认为类名。
//        所有的实体类都要有主键，@Id注解表示该属性是一个主键，@GeneratedValue注解表示主键自动生成，strategy表示主键生成策略。
//        默认情况下，生成的表中字段的名称就是实体类中属性的名称，通过@Column注解可以定制生成的字段属性，name表示该属性对应的数据表中字段的名称，nullable表示该字段非空。
//@Transient注解表示在生成数据库中的表时，该属性可以忽略，即不生成对应的字段。
