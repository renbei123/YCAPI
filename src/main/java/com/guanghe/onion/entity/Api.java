package com.guanghe.onion.entity;


import com.guanghe.onion.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@Table(name = "Api")
//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
public class Api
{

    @Id
    @Column(name = "id",unique = true,nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path",  nullable = false)
    private String path;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "headers", length = 500,columnDefinition = "text")
    private String headers;

    @Column(name = "body", length =10000, columnDefinition = "text")
    private String body;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "label")
    private String label;

    @Column(name = "status")
    private Boolean status=true;   // true: 使用中   false：废弃


    // assert
    @Column(name = "assert_Code",nullable = true)
    private Integer assert_Code;

    @Column(name = "assert_hasString" ,nullable = true )
    private String assert_hasString;

    @Column(name = "assert_jsonCheck",nullable = true)
    private String assert_jsonCheck;

    @Transient
    private String[] assert_hasStringArray;
    @Transient
    private String[] assert_jsonCheckArray;
}