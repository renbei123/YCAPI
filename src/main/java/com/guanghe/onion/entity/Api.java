package com.guanghe.onion.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "name", length = 1000)
    private String name;

    @Column(name = "path", nullable = false, length = 1000)
    private String path;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "headers", length = 800, columnDefinition = "text")
    private String headers;

    @Column(name = "body", length =10000, columnDefinition = "text")
    private String body;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "label")
    private String label;

    @Column(name = "status")
    private Boolean status = true;   // true: 使用中   false：废弃


    //取邮件@的前缀名字，避免中文重名
    @Column(name = "creater", length = 20)
    private String creater;


    @Column(name = "response_time")
    private Long response_time;

    // assert
    @Column(name = "assert_Code", nullable = true)
    private Integer assert_Code;

    @Column(name = "assert_hasString", nullable = true, length = 800)
    private String assert_hasString;

    @Column(name = "assert_json_path", nullable = true)
    private String assert_json_path;

    @Column(name = "assert_json_value", nullable = true, length = 800)
    private String assert_json_value;

    @Column(name = "PlanVar_name", nullable = true)
    private String PlanVar_name;
    @Column(name = "PlanVar_jsonpath", nullable = true)
    private String PlanVar_jsonpath;

    @Transient
    private String[] assert_hasStringArray = null;
    @Transient
    private String[] assert_json_pathArray = null;
    @Transient
    private String[] assert_json_valueArray = null;
    @Transient
    private String[] PlanVar_nameArray = null;
    @Transient
    private String[] PlanVar_jsonpathArray = null;

}