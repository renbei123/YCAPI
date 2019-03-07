package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "api")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Api extends BaseEntity
{

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name", length =120, nullable = true, unique = true)
    private String name;

    @Column(name = "path",  nullable = false, unique = true)
    private String path;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "headers", length = 500)
    private String headers;

    @Column(name = "parameters", length =1000)
    private String parameters;

}